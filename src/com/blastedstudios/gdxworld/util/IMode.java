package com.blastedstudios.gdxworld.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;

import net.xeoh.plugins.base.Plugin;

public interface IMode extends Plugin {
	public boolean touchDown(int x, int y, int x1, int y1);
	public boolean touchDragged(int x, int y, int ptr);
	public boolean touchUp(int x, int y, int arg2, int arg3);
	public boolean contains(float x, float y);
	public void clean();
	public void loadLevel(GDXLevel level);
	public void init(LevelEditorScreen screen);
	public void start();
	public void render(float delta, Camera camera, ShapeRenderer renderer);
}
