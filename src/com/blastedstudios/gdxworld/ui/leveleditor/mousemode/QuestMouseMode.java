package com.blastedstudios.gdxworld.ui.leveleditor.mousemode;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.QuestWindow;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

public class QuestMouseMode extends AbstractMouseMode {
	private final LevelEditorScreen screen;
	private final GDXWindow questWindow;

	public QuestMouseMode(LevelEditorScreen screen) {
		super(screen.getCamera());
		this.screen = screen;
		questWindow = new QuestWindow(screen.getSkin(), screen.getLevel().getQuests(), this);
		screen.getStage().addActor(questWindow);
	}

	@Override public boolean contains(float x, float y) {
		return questWindow.contains(x, y);
	}

	@Override public void clean() {
		if(questWindow != null)
			questWindow.remove();
	}

	public void addQuest(GDXQuest quest) {
		Gdx.app.log("QuestMouseMode.addQuest", quest.toString());
		if(!screen.getLevel().getQuests().contains(quest))
			screen.getLevel().getQuests().add(quest);
	}

	public void removeQuest(GDXQuest quest) {
		Gdx.app.log("QuestMouseMode.removeQuest", quest.toString());
		screen.getLevel().getQuests().remove(quest);
	}
	
	public void clearQuests(){
		Gdx.app.log("QuestMouseMode.clearQuests", "");
		screen.getLevel().getQuests().clear();
	}
}
