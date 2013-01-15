package com.blastedstudios.gdxworld.ui.leveleditor;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXPolygon;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelEditorScreen extends AbstractScreen<GDXWorldEditor> {
	private static final float NODE_RADIUS = 1;
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private final World world = new World(new Vector2(), true);
	private final Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	private final HashMap<GDXPolygon, Body> bodies = new HashMap<GDXPolygon, Body>();
	private LevelWindow levelWindow;
	private PolygonWindow polygonWindow;
	private GDXLevel gdxLevel;
	
	public LevelEditorScreen(final GDXWorldEditor game, final GDXWorld gdxWorld, final GDXLevel gdxLevel){
		super(game);
		this.gdxLevel = gdxLevel;
		stage.addActor(levelWindow = new LevelWindow(game, skin, gdxWorld, gdxLevel));
		camera.zoom += 3;
	}
	
	@Override public void render(float delta) {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		camera.update();
		camera.apply(Gdx.gl10);
		renderer.render(world, camera.combined);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		if(Gdx.input.isKeyPressed(Keys.UP))
			camera.position.y++;
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			camera.position.y--;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			camera.position.x++;
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			camera.position.x--;
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		Gdx.app.debug("LevelEditorScreen.render", "isTouched: x="+x+ " y="+y);
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		if(!levelWindow.contains(x,y) && (polygonWindow == null || !polygonWindow.contains(x, y))){
			if(levelWindow.isPolygonMode()){
				GDXPolygon polygon = gdxLevel.getClosestPolygon(coordinates.x, coordinates.y);
				if(polygon == null || polygon.getClosestVertex(coordinates.x, coordinates.y).
						dst(coordinates.x, coordinates.y) > NODE_RADIUS)
					polygon = new GDXPolygon();
				if(polygonWindow == null){
					Gdx.app.log("LevelEditorScreen.touchDown", "TODO Create polygon");
					stage.addActor(polygonWindow = new PolygonWindow(skin, this, polygon));
				}
				Vector2 vertex = new Vector2(coordinates.x, coordinates.y);
				polygonWindow.add(vertex);
			}
		}
		return false;
	}
	
	public void addPolygon(GDXPolygon polygon){
		Gdx.app.log("WorldEditorScreen.addPolygon", polygon.toString());
		bodies.put(polygon, polygon.createFixture(world, new FixtureDef(), BodyType.StaticBody));
	}

	public void removePolygon(GDXPolygon polygon) {
		Gdx.app.log("WorldEditorScreen.removePolygon", polygon.toString());
		world.destroyBody(bodies.remove(polygon));
	}
	
	public void removePolygonWindow(){
		polygonWindow.remove();
		polygonWindow = null;
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(1, camera.zoom + amount + (amount/amount)*camera.zoom/8);
		Gdx.app.log("WorldEditorScreen.scrolled", "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}
}