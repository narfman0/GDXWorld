package com.blastedstudios.gdxworld.world.quest;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
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
	private transient IQuestTriggerInformationProvider provider;
	private transient IQuestManifestationExecutor executor;
	private transient GDXLevel currentLevel;
	private transient Map<String, GDXQuest> currentLevelQuestMap;
	private Map<String, List<QuestStatus>> levelQuestStatusMap = new HashMap<>();

	public void initialize(IQuestTriggerInformationProvider provider,
			IQuestManifestationExecutor executor){
		this.provider = provider;
		this.executor = executor;
	}

	public void setCurrentLevel(GDXLevel currentLevel) {
		this.currentLevel = currentLevel;
		Log.log("GDXQuestManager.setCurrentLevel", "set level: " + currentLevel);
		if(!levelQuestStatusMap.containsKey(currentLevel.getName()) || 
				!Properties.getBool("quest.status.restore", false)){
			List<QuestStatus> statuses = new LinkedList<>();
			for(GDXQuest quest : currentLevel.getQuests())
				statuses.add(new QuestStatus(currentLevel.getName(), quest.getName()));
			Collections.sort(statuses, new QuestStatus.CompletionComparator());
			levelQuestStatusMap.put(currentLevel.getName(), statuses);
		}
		currentLevelQuestMap = new HashMap<>();
		for(GDXQuest quest : currentLevel.getQuests()){
			GDXQuest dupe = (GDXQuest) quest.clone();
			dupe.initialize(provider, executor);
			currentLevelQuestMap.put(quest.getName(), dupe);
		}
	}
	
	public void tick(float dt){
		List<QuestStatus> statuses = levelQuestStatusMap.get(currentLevel.getName());
		if(statuses == null){
			Log.error("GDXQuestManager.tick", "levelQuestStatusMap does not contain level: " + currentLevel);
			return;
		}
		boolean statusChanged = false;	//can't sort while looping through map
		for(QuestStatus status : statuses){
			GDXQuest quest = currentLevelQuestMap.get(status.questName);
			if(status.getCompleted() == CompletionEnum.NOT_STARTED || quest.isRepeatable()){
				if(isActive(quest) && quest.getTrigger().activate()){
					status.setCompleted(quest.getManifestation().execute(dt));
					Log.log("GDXQuestManager.tick", "Quest manifested: " + quest);
					if(quest.isRepeatable())
						quest.getTrigger().reinitialize();
					statusChanged = true;
				}
			}else if(status.getCompleted() == CompletionEnum.EXECUTING){
				CompletionEnum completeStatus = quest.getManifestation().tick(dt);
				if(completeStatus != CompletionEnum.EXECUTING){
					status.setCompleted(completeStatus);
					if(quest.isRepeatable())
						quest.getTrigger().reinitialize();
					statusChanged = true;
				}
			}else
				break;
		}
		if(statusChanged)
			Collections.sort(statuses, new QuestStatus.CompletionComparator());
	}
	
	/**
	 * The following works because of how java .equals works. I give it
	 * a QuestStatus that matches. Also, booleans default to 'false' so if
	 * the dependent quest is not completed, it will match, and return 
	 * false
	 * @return true if the quest's prerequisites have been completed
	 */
	public boolean isActive(GDXQuest quest){
		if(!quest.getPrerequisites().trim().equals(""))
			for(String prereq : quest.getPrerequisites().split(",")){
				List<QuestStatus> statuses = levelQuestStatusMap.get(currentLevel.getName());
				for(QuestStatus status : statuses)
					if(status.levelName.equals(currentLevel.getName()) && prereq.trim().equals(status.questName) &&
							status.getCompleted() != CompletionEnum.COMPLETED)
						return false;
			}
		return true;
	}
	
	public boolean isCompleted(GDXQuest quest){
		QuestStatus representative = new QuestStatus(currentLevel.getName(), quest.getName());
		representative.setCompleted(CompletionEnum.COMPLETED);
		return levelQuestStatusMap.get(currentLevel.getName()).contains(representative);
	}
	
	public void setStatus(String questName, CompletionEnum completed){
		List<QuestStatus> statuses = levelQuestStatusMap.get(currentLevel.getName());
		for(QuestStatus status : statuses)
			if(status.questName.equals(questName))
				status.setCompleted(completed);
		Collections.sort(statuses, new QuestStatus.CompletionComparator());
	}
	
	public List<QuestStatus> getQuestStatuses(){
		return getQuestStatuses(currentLevel);
	}
	
	public List<QuestStatus> getQuestStatuses(GDXLevel level){
		return levelQuestStatusMap.get(level.getName());
	}
}
