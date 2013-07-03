package com.blastedstudios.gdxworld.plugin.mode.chain;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;

@PluginImplementation
public class ChainMode extends AbstractMode {
	private ChainWindow window;

	public void start() {
		screen.getStage().addActor(window = new ChainWindow(screen.getSkin(), screen.getLevel()));
	}

	@Override public void clean() {
		if(window != null)
			window.remove();
		window = null;
	}
	
	public boolean touchDown(int x, int y, int x1, int y1){
		super.touchDown(x, y, x1, y1);
		if(!window.contains(x, y))
			window.touched(coordinates.x, coordinates.y);
		return false;
	}
	
	public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		if(!window.contains(x, y))
			window.touched(coordinates.x, coordinates.y);
		return false;
	}
	
	public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		if(!window.contains(x, y))
			window.touched(coordinates.x, coordinates.y);
		return false;
	}

	@Override public void render(float delta, OrthographicCamera camera, GDXRenderer gdxRenderer, ShapeRenderer renderer){
		if(window != null)
			window.render(delta, camera);
	}
}
