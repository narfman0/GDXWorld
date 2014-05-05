package com.blastedstudios.gdxworld.plugin.mode.background;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
	private BackgroundChooserWindow backgroundChooserWindow;
	
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
		if(backgroundWindow == null && backgroundChooserWindow == null){
			List<GDXBackground> backgrounds = getOverlappingBackgrounds(coordinates.x, coordinates.y);
			if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || backgrounds.isEmpty())
				touchBegin(new GDXBackground());
			else if(backgrounds.size() == 1)
				touchBegin(backgrounds.get(0));
			else
				screen.getStage().addActor(backgroundChooserWindow = new BackgroundChooserWindow(
						screen.getSkin(), this, backgrounds));
		}
		if(backgroundWindow != null)
			backgroundWindow.clicked(coordinates);
		return false;
	}
	
	void touchBegin(GDXBackground background){
		if(backgroundWindow == null)
			screen.getStage().addActor(backgroundWindow = new BackgroundWindow(screen.getSkin(), this, background));
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		shift();
		if(backgroundWindow != null)
			backgroundWindow.clicked(coordinates);
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		shift();
		if(backgroundWindow != null)
			backgroundWindow.clicked(coordinates);
		return false;
	}
	
	private void shift(){
		if(backgroundWindow != null){
			GDXBackground background = backgroundWindow.getGDXBackground();
			Gdx.app.debug("BackgroundMode.touchUp", background.toString() + " to " + coordinates);
			Vector2 world = GDXRenderer.fromParallax(backgroundWindow.getDepth(), coordinates, camera);
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
				background.getCoordinates().set(world);
				if(backgroundWindow != null)
					backgroundWindow.setCenter(new Vector2(world.x, world.y));
			}
		}
	}
	
	private List<GDXBackground> getOverlappingBackgrounds(float x, float y){
		List<GDXBackground> backgrounds = new LinkedList<>();
		for(GDXBackground background : screen.getLevel().getBackgrounds()){
			Texture tex = screen.getGDXRenderer().getTexture(background.getTexture());
			Vector2 world = GDXRenderer.toParallax(background.getDepth(), background.getCoordinates(), camera);
			if(tex != null && PolygonUtils.aabbCollide(x,y, world.x, world.y,
				tex.getWidth()*background.getScale(), tex.getHeight()*background.getScale()))
				backgrounds.add(background);
		}
		return backgrounds;
	}
	
	@Override public boolean contains(float x, float y) {
		return backgroundWindow != null && backgroundWindow.contains(x, y);
	}

	@Override public void clean() {
		if(backgroundWindow != null)
			backgroundWindow.remove();
		if(backgroundChooserWindow != null)
			backgroundChooserWindow.remove();
		backgroundWindow = null;
		backgroundChooserWindow = null;
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
