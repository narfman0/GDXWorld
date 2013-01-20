package com.blastedstudios.gdxworld.world.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.quest.manifestation.IQuestManifestationExecutor;
import com.blastedstudios.gdxworld.world.quest.trigger.IQuestTriggerInformationProvider;

/**
 * Manager for quests for each client/player. This will determine what quests
 * are available, if they are to be triggered, and will trigger the quest.
 * 
 * Intention is to go in higher level manager and tick every frame so that
 * progress may occur seamlessly. Should be the main visible thing to
 * quest programmer.
 */
public class GDXQuestManager implements Serializable{
	private static final long serialVersionUID = 1L;
	private transient final IQuestTriggerInformationProvider provider;
	private transient final IQuestManifestationExecutor executor;
	private transient GDXLevel currentLevel;
	private transient Map<String,GDXQuest> currentLevelQuestMap = new HashMap<String, GDXQuest>();
	private Map<String, List<QuestStatus>> levelQuestStatusMap = new HashMap<String, List<QuestStatus>>();

	public GDXQuestManager(IQuestTriggerInformationProvider provider,
			IQuestManifestationExecutor executor){
		this.provider = provider;
		this.executor = executor;
	}

	public void setCurrentLevel(GDXLevel currentLevel) {
		this.currentLevel = currentLevel;
		Gdx.app.log("GDXQuestManager.setCurrentLevel", "set level: " + currentLevel.getName());
		if(!levelQuestStatusMap.containsKey(currentLevel.getName())){
			List<QuestStatus> statuses = new ArrayList<QuestStatus>();
			for(GDXQuest quest : currentLevel.getQuests())
				statuses.add(new QuestStatus(currentLevel.getName(), quest.getName()));
			Collections.sort(statuses, new QuestStatus.CompletionComparator());
			levelQuestStatusMap.put(currentLevel.getName(), statuses);
		}
		currentLevelQuestMap.clear();
		for(GDXQuest quest : currentLevel.getQuests()){
			GDXQuest dupe = (GDXQuest) quest.clone();
			dupe.initialize(provider, executor);
			currentLevelQuestMap.put(quest.getName(), dupe);
		}
	}
	
	public void tick(float delta){
		if(!levelQuestStatusMap.containsKey(currentLevel.getName())){
			Gdx.app.error("GDXQuestManager.tick", "levelQuestStatusMap does not contain level: " + currentLevel.getName());
			return;
		}
		boolean statusChanged = false;	//can't sort while looping through map
		for(QuestStatus status : levelQuestStatusMap.get(currentLevel.getName()))
			if(!status.isCompleted()){
				GDXQuest quest = currentLevelQuestMap.get(status.questName);
				if(isActive(quest) && quest.getTrigger().activate()){
					status.setCompleted(statusChanged = true);
					quest.getManifestation().execute();
					Gdx.app.log("GDXQuestManager.tick", "Quest manifested: " + quest.getName());
				}
			}else
				break;
		if(statusChanged)
			Collections.sort(levelQuestStatusMap.get(currentLevel.getName()), 
					new QuestStatus.CompletionComparator());
	}
	
	/**
	 * The following works because of how java .equals works. I give it
	 * a QuestStatus that matches. Also, booleans default to 'false' so if
	 * the dependent quest is not completed, it will match, and return 
	 * false
	 * @return true if the quest's prerequisites have been completed
	 */
	public boolean isActive(GDXQuest quest){
		for(String prereq : quest.getPrerequisites().split(",")){
			List<QuestStatus> statuses = levelQuestStatusMap.get(currentLevel.getName());
			if(statuses.contains(new QuestStatus(currentLevel.getName(), prereq)))
				return false;
		}
		return true;
	}
}
