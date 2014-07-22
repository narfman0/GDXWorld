package com.blastedstudios.gdxworld.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;

import net.xeoh.plugins.base.Plugin;

public interface IMode extends Plugin {
	 boolean touchDown(int x, int y, int x1, int y1);
	 boolean touchDragged(int x, int y, int ptr);
	 boolean touchUp(int x, int y, int arg2, int arg3);
	 boolean contains(float x, float y);
	 void clean();
	 void loadLevel(GDXLevel level);
	 int getLoadPriority();
	 void init(LevelEditorScreen screen);
	 void start();
	 void render(float delta, OrthographicCamera camera, GDXRenderer gdxRenderer, ShapeRenderer renderer);
	 Body getPhysicsBody(String name);
	 Joint getPhysicsJoint(String name);
}
