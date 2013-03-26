package com.blastedstudios.gdxworld.ui.leveleditor.mode.light;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.light.GDXLight;

public class LightMode extends AbstractMode {
	private LevelEditorScreen screen;
	private LightWindow lightWindow;

	public LightMode(LevelEditorScreen screen){
		super(screen.getCamera());
		this.screen = screen;
	}
	
	public void addLight(GDXLight light){
		Gdx.app.debug("LightMode.addLight", "Adding light");
		if(!screen.getLevel().getLights().contains(light))
			screen.getLevel().getLights().add(light);
	}

	@Override public boolean contains(float x, float y) {
		return lightWindow.contains(x, y);
	}

	@Override public void clean() {
		if(lightWindow != null)
			lightWindow.remove();
		lightWindow = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		for(GDXLight light : level.getLights())
			addLight(light);
		screen.getLevel().setLightAmbient(level.getLightAmbient().cpy());
	}

	@Override public void start() {
		lightWindow = new LightWindow(screen.getSkin(), screen.getLevel().getLights(), 
				screen.getLevel().getLightAmbient(), this, screen);
		screen.getStage().addActor(lightWindow);
	}
}
