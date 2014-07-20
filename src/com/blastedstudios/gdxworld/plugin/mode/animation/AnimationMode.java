package com.blastedstudios.gdxworld.plugin.mode.animation;

import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.IMode;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class AnimationMode extends AbstractMode implements IMode {
	private AbstractWindow window;

	@Override public void start() {
		super.start();
		window = new AnimationsWindow(screen.getSkin(), screen.getLevel().getAnimations(), this, screen);
		screen.getStage().addActor(window);
	}
	
	@Override public int getLoadPriority() {
		return 150;
	}
	
	@Override public boolean contains(float x, float y) {
		return window.contains(x, y);
	}

	@Override public void clean() {
		if(window != null)
			window.remove();
		window = null;
	}
}
