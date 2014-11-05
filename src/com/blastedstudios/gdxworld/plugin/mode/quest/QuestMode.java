package com.blastedstudios.gdxworld.plugin.mode.quest;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

@PluginImplementation
public class QuestMode extends AbstractMode {
	private QuestWindow questWindow;

	@Override public boolean contains(float x, float y) {
		return questWindow.contains(x, y);
	}

	@Override public void clean() {
		if(questWindow != null)
			questWindow.remove();
		questWindow = null;
	}

	public void addQuest(GDXQuest quest) {
		Log.log("QuestMouseMode.addQuest", quest.toString());
		if(!screen.getLevel().getQuests().contains(quest))
			screen.getLevel().getQuests().add(quest);
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXQuest npc : level.getQuests())
			addQuest(npc);
	}

	@Override public void start() {
		super.start();
		questWindow = new QuestWindow(screen.getSkin(), screen.getLevel().getQuests(), this, screen);
		screen.getStage().addActor(questWindow);
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1){
		super.touchDown(x, y, x1, y1);
		questWindow.touched(coordinates);
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		questWindow.touched(coordinates);
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		questWindow.touched(coordinates);
		return false;
	}

	@Override public int getLoadPriority() {
		return 80;
	}
}
