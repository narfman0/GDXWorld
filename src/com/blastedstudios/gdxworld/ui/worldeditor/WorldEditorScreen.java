package com.blastedstudios.gdxworld.ui.worldeditor;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class WorldEditorScreen extends AbstractScreen<GDXWorldEditor> {
	private static final float LEVEL_RADIUS = 1;
	private OrthographicCamera camera = new OrthographicCamera(28, 20);
	private World world = new World(new Vector2(), true);
	private Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	private LevelInformationWindow levelInfo;
	private GDXWindow worldWindow;
	private final GDXWorld gdxWorld;
	private HashMap<GDXLevel, Body> bodies = new HashMap<GDXLevel, Body>();
	
	public WorldEditorScreen(final GDXWorldEditor game, final GDXWorld gdxWorld){
		super(game);
		this.gdxWorld = gdxWorld;
		stage.addActor(worldWindow = new WorldWindow(game, skin, gdxWorld));
		for(GDXLevel level : gdxWorld.getLevels())
			add(level);
		camera.zoom += 3;
	}

	@Override public void render(float delta) {
		super.render(delta);
		camera.update();
		camera.apply(Gdx.gl10);
		renderer.render(world, camera.combined);
		if(Gdx.input.isKeyPressed(Keys.UP))
			camera.position.y+=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			camera.position.y-=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			camera.position.x+=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			camera.position.x-=camera.zoom;
		
		if(Gdx.input.isTouched()){
			int x = Gdx.input.getX(), y = Gdx.input.getY();
			Gdx.app.debug("WodlEditorScren.render", "isTouched: x="+x+ " y="+y);
			if(!worldWindow.contains(x,y) && (levelInfo == null || !levelInfo.contains(x, y))){
				Vector3 coordinates = new Vector3(x,y,0);
				camera.unproject(coordinates);
				if(levelInfo == null){
					GDXLevel level = gdxWorld.getClosestLevel(coordinates.x,coordinates.y);
					if(level == null || level.getCoordinates().dst(coordinates.x, coordinates.y) > LEVEL_RADIUS){
						level = new GDXLevel();
						Gdx.app.log("WorldEditorScreen.render", "Spawned new level");
					}else
						Gdx.app.log("WorldEditorScreen.render", "Level selected " + level);
					levelInfo = new LevelInformationWindow(game, this, skin, gdxWorld, level);
					stage.addActor(levelInfo);
				}else
					levelInfo.setCoordinates(coordinates.x, coordinates.y);
			}
		}
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
		bodies.put(gdxLevel, PhysicsHelper.createCircle(world, LEVEL_RADIUS, gdxLevel.getCoordinates()));
	}

	public void update(GDXLevel gdxLevel) {
		bodies.get(gdxLevel).setTransform(gdxLevel.getCoordinates(), 0);
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(1, camera.zoom + amount + amount*camera.zoom/8);
		Gdx.app.log("WorldEditorScreen.scrolled", "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}
}
