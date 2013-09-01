package com.blastedstudios.gdxworld.plugin.mode.sound;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXSound;

@PluginImplementation
public class SoundMode extends AbstractMode {
	private SoundWindow soundWindow;

	@Override public void start() {
		super.start();
		soundWindow = new SoundWindow(screen.getSkin(), screen.getLevel().getSounds(), this, screen);
		screen.getStage().addActor(soundWindow);
	}
	
	@Override public boolean contains(float x, float y) {
		return soundWindow.contains(x, y);
	}

	@Override public void clean() {
		if(soundWindow != null)
			soundWindow.remove();
		soundWindow = null;
	}

	public void addSound(GDXSound sound) {
		Gdx.app.log("SoundMode.addSound", sound.toString());
		if(!screen.getLevel().getSounds().contains(sound))
			screen.getLevel().getSounds().add(sound);
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXSound npc : level.getSounds())
			addSound(npc);
	}
}
