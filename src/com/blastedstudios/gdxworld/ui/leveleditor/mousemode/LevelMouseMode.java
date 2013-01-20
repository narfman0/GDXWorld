package com.blastedstudios.gdxworld.ui.leveleditor.mousemode;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class LevelMouseMode {
	protected final Camera camera;
	protected Vector2 coordinates;
	
	public LevelMouseMode(Camera camera){
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
	
	public abstract boolean contains(float x, float y);
	public abstract void clean();
}
