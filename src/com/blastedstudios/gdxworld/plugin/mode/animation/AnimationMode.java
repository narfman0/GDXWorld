package com.blastedstudios.gdxworld.plugin.mode.animation;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.animation.live.AnimationLiveOptionTable;
import com.blastedstudios.gdxworld.plugin.mode.live.ILiveOptionProvider;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.IMode;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class AnimationMode extends AbstractMode implements IMode, ILiveOptionProvider {
	private AnimationLiveOptionTable table;
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
		table = null;//if we go out of live mode we should get a new table in case there are new animations
	}

	@Override public Table getTable(Skin skin, AbstractWindow window) {
		if(table == null)
			table = new AnimationLiveOptionTable(skin, this, window);
		return table;
	}

	@Override public void render(float delta, OrthographicCamera camera, GDXRenderer gdxRenderer, ShapeRenderer renderer){
		if(table != null)
			table.render(delta);
	}
}
