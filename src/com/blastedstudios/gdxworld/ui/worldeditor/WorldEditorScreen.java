package com.blastedstudios.gdxworld.ui.worldeditor;

import java.io.File;

import com.blastedstudios.gdxworld.util.GDXGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.TempWorldScreen;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class WorldEditorScreen extends AbstractScreen {
	private final ShapeRenderer renderer = new ShapeRenderer();
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private LevelInformationWindow levelInfo;
	private WorldWindow worldWindow;
	private PropertiesWindow propertiesWindow;
	private final GDXWorld gdxWorld;
	
	public WorldEditorScreen(final GDXGame game, final GDXWorld gdxWorld, File lastSavedFile){
		super(game, "data/ui/uiskin.json");
		this.gdxWorld = gdxWorld == null ? new GDXWorld() : gdxWorld;
		stage.addActor(worldWindow = new WorldWindow(game, skin, gdxWorld, lastSavedFile));
		stage.addActor(propertiesWindow = new PropertiesWindow(game, skin, gdxWorld.getWorldProperties()));
		camera.zoom += 3;
		TempWorldScreen.start(this.gdxWorld);
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
		if(Gdx.input.isKeyPressed(Keys.UP))
			camera.position.y+=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			camera.position.y-=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			camera.position.x+=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			camera.position.x-=camera.zoom;
		stage.draw();
	}
	
	void removeLevelInformationWindow(){
		levelInfo.remove();
		levelInfo = null;
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(.01f, camera.zoom + amount*camera.zoom/4f);
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
		if(!worldWindow.contains(x,y) && !propertiesWindow.contains(x,y) && 
				(levelInfo == null || !levelInfo.contains(x, y))){
			Vector2 coordinates = GDXRenderer.toWorldCoordinates(camera, new Vector2(x,y));
			if(levelInfo == null){
				GDXLevel level = gdxWorld.getClosestLevel(coordinates.x,coordinates.y);
				if(level == null || level.getCoordinates().dst(coordinates.x, coordinates.y) > getLevelRadius()){
					level = new GDXLevel();
					Gdx.app.log("WorldEditorScreen.render", "Spawned new level");
				}else
					Gdx.app.log("WorldEditorScreen.render", "Level selected " + level);
				levelInfo = new LevelInformationWindow(game, this, skin, gdxWorld, 
						worldWindow.getSavedFile(), level);
				stage.addActor(levelInfo);
			}else if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				levelInfo.setCoordinates(coordinates.x, coordinates.y);
		}
	}
	
	@Override public boolean keyDown(int key) {
		switch(key){
		case Keys.ESCAPE:
			game.popScreen();
			break;
		}
		return super.keyDown(key);
	}
	
	private static float getLevelRadius(){
		return Properties.getFloat("world.editor.node.radius", 1f);
	}
}
