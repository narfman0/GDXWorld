package com.blastedstudios.gdxworld.plugin.mode.background;

import java.util.Collections;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.math.PolygonUtils;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXBackground;
import com.blastedstudios.gdxworld.world.GDXLevel;

@PluginImplementation
public class BackgroundMode extends AbstractMode {
	private BackgroundWindow backgroundWindow;
	private GDXBackground lastTouched;
	
	public void addBackground(GDXBackground background) {
		Gdx.app.log("BackgroundMode.addBackground", background.toString());
		if(!screen.getLevel().getBackgrounds().contains(background)){
			screen.getLevel().getBackgrounds().add(background);
			Collections.sort(screen.getLevel().getBackgrounds());
		}
	}

	public void removeBackground(GDXBackground background) {
		Gdx.app.log("BackgroundMode.removeBackground", background.toString());
		screen.getLevel().getBackgrounds().remove(background);
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("BackgroundMode.touchDown", "x="+x+ " y="+y);
		GDXBackground background = getClosest(coordinates.x, coordinates.y);
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || background == null)
			background = new GDXBackground();
		if(backgroundWindow == null)
			screen.getStage().addActor(backgroundWindow = new BackgroundWindow(screen.getSkin(), this, background));
		backgroundWindow.setCenter(new Vector2(coordinates.x, coordinates.y));
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			lastTouched = background;
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		shift();
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		shift();
		lastTouched = null;
		return false;
	}
	
	private void shift(){
		if(lastTouched != null){
			Gdx.app.debug("BackgroundMode.touchUp", lastTouched.toString() + " to " + coordinates);
			Vector2 world = GDXRenderer.fromParallax(lastTouched.getDepth(), coordinates, camera);
			lastTouched.getCoordinates().set(world);
			if(backgroundWindow != null)
				backgroundWindow.setCenter(new Vector2(world.x, world.y));
		}
	}
	
	private GDXBackground getClosest(float x, float y){
		for(GDXBackground background : screen.getLevel().getBackgrounds()){
			Texture tex = screen.getGDXRenderer().getTexture(background.getTexture());
			Vector2 world = GDXRenderer.toParallax(background.getDepth(), background.getCoordinates(), camera);
			if(tex != null && PolygonUtils.aabbCollide(x,y, world.x, world.y,
				tex.getWidth()*background.getScale(), tex.getHeight()*background.getScale()))
				return background;
		}
		return null;
	}
	
	@Override public boolean contains(float x, float y) {
		return backgroundWindow != null && backgroundWindow.contains(x, y);
	}

	@Override public void clean() {
		if(backgroundWindow != null)
			backgroundWindow.remove();
		backgroundWindow = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXBackground background : level.getBackgrounds())
			addBackground(background);
	}

	@Override public int getLoadPriority() {
		return 20;
	}
}
