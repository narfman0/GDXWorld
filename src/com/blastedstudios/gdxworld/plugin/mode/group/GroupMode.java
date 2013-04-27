package com.blastedstudios.gdxworld.plugin.mode.group;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.IMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.group.GDXGroup;

@PluginImplementation
//@Dependency(classes={CircleMode.class,JointMode.class,PolygonMode.class})
public class GroupMode extends AbstractMode implements IMode {
	private GroupWindow window;

	@Override public boolean contains(float x, float y) {
		return window.contains(x, y);
	}

	@Override public void clean() {
		if(window != null)
			window.remove();
		window = null;
	}

	public void add(GDXGroup group) {
		Gdx.app.log("GroupMode.add", group.toString());
		if(!screen.getLevel().getGroups().contains(group))
			screen.getLevel().getGroups().add(group);
	}

	public void remove(GDXGroup group) {
		Gdx.app.log("GroupMode.remove", group.toString());
		if(screen.getLevel().getGroups().contains(group))
			screen.getLevel().getGroups().remove(group);
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXGroup object : level.getGroups())
			add(object);
	}

	@Override public void start() {
		super.start();
		window = new GroupWindow(screen.getSkin(), screen.getLevel().getGroups(), this, screen);
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
}
