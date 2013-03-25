package com.blastedstudios.gdxworld.ui.leveleditor.mode;

import org.reflections.Reflections;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.blastedstudios.gdxworld.world.GDXLevel;

public abstract class AbstractMode {
	private static Iterable<Class<? extends AbstractMode>> children;
	protected final Camera camera;
	protected Vector2 coordinates;
	
	public AbstractMode(Camera camera){
		this.camera = camera;
	}
	
	public boolean touchDown(int x, int y, int x1, int y1){
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		this.coordinates = new Vector2(coordinates.x,coordinates.y);
		return false;
	}
	
	public boolean touchUp(int x, int y, int arg2, int arg3){
		return false;
	}
	
	public static Iterable<Class<? extends AbstractMode>> getChildClasses(){
		if(children == null){
			Reflections reflections = new Reflections("com.blastedstudios.gdxworld.ui.leveleditor.mode");
			children = reflections.getSubTypesOf(AbstractMode.class);
		}
		return children;
	}
	
	public abstract boolean contains(float x, float y);
	public abstract void clean();
	public abstract void loadLevel(GDXLevel level);
	public abstract void start();
}
