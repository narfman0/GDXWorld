package com.blastedstudios.gdxworld.plugin.mode.light;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.light.GDXLight;

@PluginImplementation
public class LightMode extends AbstractMode {
	private LightWindow lightWindow;
	
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
		super.loadLevel(level);
		for(GDXLight light : level.getLights())
			addLight(light);
		screen.getLevel().setLightAmbient(level.getLightAmbient());
	}

	@Override public void start() {
		super.start();
		lightWindow = new LightWindow(screen.getSkin(), screen.getLevel().getLights(), 
				GDXLight.convert(screen.getLevel().getLightAmbient()), this, screen);
		screen.getStage().addActor(lightWindow);
	}
}
