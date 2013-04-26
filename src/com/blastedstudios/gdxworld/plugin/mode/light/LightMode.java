package com.blastedstudios.gdxworld.plugin.mode.light;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.light.GDXLight;

@PluginImplementation
public class LightMode extends AbstractMode {
	private LightWindow lightWindow;
	private RayHandler rayHandler;
	
	public void addLight(GDXLight light){
		Gdx.app.debug("LightMode.addLight", "Adding light");
		if(!screen.getLevel().getLights().contains(light))
			screen.getLevel().getLights().add(light);
	}

	@Override public boolean contains(float x, float y) {
		return lightWindow.contains(x, y);
	}

	@Override public boolean touchDown(int x, int y, int x1, int y1){
		super.touchDown(x,y,x1,y1);
		drag();
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		drag();
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		drag();
		return false;
	}
	
	private void drag(){
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && lightWindow.getSelected() != null)
			lightWindow.getSelected().setCoordinates(coordinates.x, coordinates.y);
	}

	@Override public void clean() {
		if(lightWindow != null)
			lightWindow.remove();
		lightWindow = null;
		if(rayHandler != null)
			rayHandler.dispose();
		rayHandler = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXLight light : level.getLights())
			addLight(light);
		if(rayHandler != null)
			rayHandler.dispose();
		rayHandler = null;
		if(screen.isLive())
			rayHandler = level.createLights(screen.getWorld()).rayHandler;
	}
	
	@Override public void render(float delta, Camera camera, ShapeRenderer renderer){
		super.render(delta, camera, renderer);
		if(rayHandler != null){
			rayHandler.setCombinedMatrix(camera.combined);
			rayHandler.updateAndRender();
		}
	}

	@Override public void start() {
		super.start();
		lightWindow = new LightWindow(screen.getSkin(), screen.getLevel().getLights(), 
				GDXLight.convert(screen.getLevel().getLightAmbient()), this, screen);
		screen.getStage().addActor(lightWindow);
	}
}
