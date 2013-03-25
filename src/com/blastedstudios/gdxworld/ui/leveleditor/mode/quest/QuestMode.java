package com.blastedstudios.gdxworld.ui.leveleditor.mode.quest;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

public class QuestMode extends AbstractMode {
	private final LevelEditorScreen screen;
	private AbstractWindow questWindow;

	public QuestMode(LevelEditorScreen screen) {
		super(screen.getCamera());
		this.screen = screen;
	}

	@Override public boolean contains(float x, float y) {
		return questWindow.contains(x, y);
	}

	@Override public void clean() {
		if(questWindow != null)
			questWindow.remove();
		questWindow = null;
	}

	public void addQuest(GDXQuest quest) {
		Gdx.app.log("QuestMouseMode.addQuest", quest.toString());
		if(!screen.getLevel().getQuests().contains(quest))
			screen.getLevel().getQuests().add(quest);
	}

	@Override public void loadLevel(GDXLevel level) {
		for(GDXQuest npc : level.getQuests())
			addQuest(npc);
	}
	
	@Override public void start() {
		questWindow = new QuestWindow(screen.getSkin(), screen.getLevel().getQuests(), this, screen);
		screen.getStage().addActor(questWindow);
	}
}
