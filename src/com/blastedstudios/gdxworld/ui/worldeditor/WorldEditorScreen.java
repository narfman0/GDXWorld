package com.blastedstudios.gdxworld.ui.worldeditor;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class WorldEditorScreen extends AbstractScreen {
	private final ShapeRenderer renderer = new ShapeRenderer();
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private LevelInformationWindow levelInfo;
	private AbstractWindow worldWindow;
	private final GDXWorld gdxWorld;
	private File lastSavedFile;
	
	public WorldEditorScreen(final Game game, final GDXWorld gdxWorld, File lastSavedFile){
		super(game);
		this.lastSavedFile = lastSavedFile;
		this.gdxWorld = gdxWorld == null ? new GDXWorld() : gdxWorld;
		stage.addActor(worldWindow = new WorldWindow(game, skin, this.gdxWorld, lastSavedFile));
		camera.zoom += 3;
	}

	@Override public void render(float delta) {
		super.render(delta);
		camera.update();
		if(!Gdx.graphics.isGL20Available())
			camera.apply(Gdx.gl10);
		renderer.setColor(Color.WHITE);
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Line);
		for(GDXLevel level : gdxWorld.getLevels())
			renderer.circle(level.getCoordinates().x, level.getCoordinates().y, getLevelRadius());
		if(levelInfo != null){
			renderer.setColor(Color.GRAY);
			renderer.circle(levelInfo.getCoordinates().x, levelInfo.getCoordinates().y, getLevelRadius());
		}
		renderer.end();
		if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
			camera.position.y+=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))
			camera.position.y-=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))
			camera.position.x+=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
			camera.position.x-=camera.zoom;
		stage.draw();
	}
	
	void removeLevelInformationWindow(){
		levelInfo.remove();
		levelInfo = null;
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(1, camera.zoom + amount + amount*camera.zoom/8);
		Gdx.app.log("WorldEditorScreen.scrolled", "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}

	@Override public boolean touchDown(int x, int y, int ptr, int button) {
		super.touchDown(x, y, ptr, button);
		touched(x,y);
		return false;
	}

	@Override public boolean touchDragged(int x, int y, int ptr) {
		super.touchDragged(x, y, ptr);
		touched(x,y);
		return false;
	}

	@Override public boolean touchUp(int x, int y, int ptr, int button) {
		super.touchUp(x, y, ptr, button);
		touched(x,y);
		return false;
	}
	
	private void touched(int x, int y){
		if(!worldWindow.contains(x,y) && (levelInfo == null || !levelInfo.contains(x, y))){
			Vector2 coordinates = toWorldCoordinates(x,y);
			if(levelInfo == null){
				GDXLevel level = gdxWorld.getClosestLevel(coordinates.x,coordinates.y);
				if(level == null || level.getCoordinates().dst(coordinates.x, coordinates.y) > getLevelRadius()){
					level = new GDXLevel();
					Gdx.app.log("WorldEditorScreen.render", "Spawned new level");
				}else
					Gdx.app.log("WorldEditorScreen.render", "Level selected " + level);
				levelInfo = new LevelInformationWindow(game, this, skin, gdxWorld, level, lastSavedFile);
				stage.addActor(levelInfo);
			}else if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				levelInfo.setCoordinates(coordinates.x, coordinates.y);
		}
	}
	
	private Vector2 toWorldCoordinates(int x, int y){
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		return new Vector2(coordinates.x,coordinates.y);
	}
	
	private static float getLevelRadius(){
		return Properties.getFloat("world.editor.node.radius", 1f);
	}
}
