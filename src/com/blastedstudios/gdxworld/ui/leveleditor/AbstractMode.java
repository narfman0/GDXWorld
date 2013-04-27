package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.blastedstudios.gdxworld.util.IMode;
import com.blastedstudios.gdxworld.world.GDXLevel;

public abstract class AbstractMode implements IMode{
	protected Camera camera;
	protected Vector2 coordinates;
	protected LevelEditorScreen screen;
	
	public boolean touchDown(int x, int y, int x1, int y1){
		updateTouched(x,y);
		return false;
	}
	
	public boolean touchDragged(int x, int y, int ptr){
		updateTouched(x,y);
		return false;
	}
	
	public boolean touchUp(int x, int y, int arg2, int arg3){
		updateTouched(x,y);
		return false;
	}
	
	private void updateTouched(int x, int y){
		if(!contains(x,y)){
			Vector3 coordinates = new Vector3(x,y,0);
			camera.unproject(coordinates);
			this.coordinates = new Vector2(coordinates.x,coordinates.y);
		}
	}

	public void loadLevel(GDXLevel level) {
		this.camera = screen.getCamera();
	}
	
	public void init(LevelEditorScreen screen){
		this.screen = screen;
	}
	
	public void start(){};
	public void render(float delta, Camera camera, ShapeRenderer renderer){};
}
