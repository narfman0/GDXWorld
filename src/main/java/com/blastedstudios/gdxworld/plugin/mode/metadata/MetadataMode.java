package com.blastedstudios.gdxworld.plugin.mode.metadata;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.IMode;

@PluginImplementation
public class MetadataMode extends AbstractMode implements IMode {
	private MetadataWindow window;

	@Override public boolean contains(float x, float y) {
		return window.contains(x, y);
	}

	@Override public void clean() {
		if(window != null)
			window.remove();
		window = null;
	}

	@Override public void start() {
		super.start();
		window = new MetadataWindow(screen.getSkin(), screen.getLevel().getMetadata(), this, screen);
		screen.getStage().addActor(window);
	}

	@Override public boolean touchDown(int x, int y, int x1, int y1){
		super.touchDown(x, y, x1, y1);
		window.touched(coordinates.x, coordinates.y);
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		window.touched(coordinates.x, coordinates.y);
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		window.touched(coordinates.x, coordinates.y);
		return false;
	}

	@Override public int getLoadPriority() {
		return 130;
	}
}
