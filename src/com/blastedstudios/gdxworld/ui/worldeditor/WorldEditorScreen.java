package com.blastedstudios.gdxworld.ui.worldeditor;

import java.io.File;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class WorldEditorScreen extends AbstractScreen {
	private OrthographicCamera camera = new OrthographicCamera(28, 20);
	private World world = new World(new Vector2(), true);
	private Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	private LevelInformationWindow levelInfo;
	private AbstractWindow worldWindow;
	private final GDXWorld gdxWorld;
	private HashMap<GDXLevel, Body> bodies = new HashMap<GDXLevel, Body>();
	private File lastSavedFile;
	
	public WorldEditorScreen(final Game game, final GDXWorld gdxWorld, File lastSavedFile){
		super(game);
		this.lastSavedFile = lastSavedFile;
		this.gdxWorld = gdxWorld == null ? new GDXWorld() : gdxWorld;
		stage.addActor(worldWindow = new WorldWindow(game, skin, this.gdxWorld, lastSavedFile));
		for(GDXLevel level : this.gdxWorld.getLevels())
			add(level);
		camera.zoom += 3;
	}

	@Override public void render(float delta) {
		super.render(delta);
		camera.update();
		if(!Gdx.graphics.isGL20Available())
			camera.apply(Gdx.gl10);
		renderer.render(world, camera.combined);
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

	public void remove(GDXLevel gdxLevel) {
		Gdx.app.log("WorldEditorScreen.remove", "Removing level " + gdxLevel + " | contained:" + bodies.containsKey(gdxLevel));
		if(bodies.containsKey(gdxLevel))
			world.destroyBody(bodies.remove(gdxLevel));
	}

	public void add(GDXLevel gdxLevel) {
		Gdx.app.log("WorldEditorScreen.add", "Added level " + gdxLevel);
		bodies.put(gdxLevel, PhysicsHelper.createCircle(world, getLevelRadius(), gdxLevel.getCoordinates(), BodyType.StaticBody));
	}

	public void update(GDXLevel gdxLevel) {
		bodies.get(gdxLevel).setTransform(gdxLevel.getCoordinates(), 0);
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
			}else{
				if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
					levelInfo.setCoordinates(coordinates.x, coordinates.y);
					if(bodies.containsKey(levelInfo.getLevel()))
						bodies.get(levelInfo.getLevel()).setTransform(coordinates, 0);
				}
			}
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
