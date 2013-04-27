package com.blastedstudios.gdxworld.plugin.mode.quest;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

@PluginImplementation
public class QuestMode extends AbstractMode {
	private AbstractWindow questWindow;

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
		super.loadLevel(level);
		for(GDXQuest npc : level.getQuests())
			addQuest(npc);
	}

	@Override public void start() {
		super.start();
		questWindow = new QuestWindow(screen.getSkin(), screen.getLevel().getQuests(), this, screen);
		screen.getStage().addActor(questWindow);
	}
}
