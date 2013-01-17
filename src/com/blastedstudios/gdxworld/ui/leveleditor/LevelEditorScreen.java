package com.blastedstudios.gdxworld.ui.leveleditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXNPC;
import com.blastedstudios.gdxworld.world.GDXPath;
import com.blastedstudios.gdxworld.world.GDXPolygon;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelEditorScreen extends AbstractScreen<GDXWorldEditor> {
	private static final float NODE_RADIUS = 1;
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private final World world = new World(new Vector2(), true);
	private final Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	private final HashMap<Object, List<Body>> bodies = new HashMap<Object, List<Body>>();
	private LevelWindow levelWindow;
	private PolygonWindow polygonWindow;
	private NPCWindow npcWindow;
	private PathWindow pathWindow;
	private GDXLevel gdxLevel;
	
	public LevelEditorScreen(final GDXWorldEditor game, final GDXWorld gdxWorld, final GDXLevel gdxLevel){
		super(game);
		this.gdxLevel = gdxLevel;
		stage.addActor(levelWindow = new LevelWindow(game, skin, gdxWorld, gdxLevel));
		camera.zoom += 3;
		for(GDXPolygon polygon : gdxLevel.getPolygons())
			addPolygon(polygon);
		for(GDXNPC npc : gdxLevel.getNpcs())
			addNPC(npc);
		for(GDXPath path : gdxLevel.getPaths())
			addPath(path);
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
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		Gdx.app.debug("LevelEditorScreen.render", "isTouched: x="+x+ " y="+y);
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		if(!levelWindow.contains(x,y) && 
				(polygonWindow == null || !polygonWindow.contains(x, y)) &&
				(npcWindow == null || !npcWindow.contains(x, y)) &&
				(pathWindow == null || !pathWindow.contains(x, y))){
			if(levelWindow.isPolygonMode()){
				GDXPolygon polygon = gdxLevel.getClosestPolygon(coordinates.x, coordinates.y);
				if(polygon == null || polygon.getClosestVertex(coordinates.x, coordinates.y).
						dst(coordinates.x, coordinates.y) > NODE_RADIUS)
					polygon = new GDXPolygon();
				if(polygonWindow == null)
					stage.addActor(polygonWindow = new PolygonWindow(skin, this, polygon));
				Vector2 vertex = new Vector2(coordinates.x, coordinates.y);
				if(polygon.getVertices().isEmpty())
					polygonWindow.add(vertex);
			}else if(levelWindow.isNPCMode()){
				GDXNPC npc = gdxLevel.getClosestNPC(coordinates.x, coordinates.y);
				if(npc == null || npc.getCoordinates().dst(coordinates.x, coordinates.y) > NODE_RADIUS)
					npc = new GDXNPC();
				if(npcWindow == null)
					stage.addActor(npcWindow = new NPCWindow(skin, this, npc));
				npcWindow.setCoordinates(new Vector2(coordinates.x, coordinates.y));
			}else if(levelWindow.isPathMode()){
				GDXPath path = gdxLevel.getClosestPath(coordinates.x, coordinates.y);
				if(path == null || path.getClosestNode(coordinates.x, coordinates.y).
						dst(coordinates.x, coordinates.y) > NODE_RADIUS)
					path = new GDXPath();
				if(pathWindow == null)
					stage.addActor(pathWindow = new PathWindow(skin, this, path));
				Vector2 vertex = new Vector2(coordinates.x, coordinates.y);
				if(path.getNodes().isEmpty())
					pathWindow.add(vertex);
			}
		}
		return false;
	}
	
	public void addPolygon(GDXPolygon polygon){
		Gdx.app.log("WorldEditorScreen.addPolygon", polygon.toString());
		if(bodies.containsKey(polygon))
			for(Body body : bodies.remove(polygon))
				world.destroyBody(body);
		Body body = polygon.createFixture(world, true);
		if(body != null){
			if(!gdxLevel.getPolygons().contains(polygon))
				gdxLevel.getPolygons().add(polygon);
			List<Body> newBodies = new ArrayList<Body>();
			newBodies.add(body);
			for(Vector2 vertex : polygon.getVertices())
				newBodies.add(PhysicsHelper.createCircle(world, NODE_RADIUS, vertex));
			bodies.put(polygon, newBodies);
		}
	}

	public void removePolygon(GDXPolygon polygon) {
		Gdx.app.log("WorldEditorScreen.removePolygon", polygon.toString());
		for(Body body : bodies.remove(polygon))
			world.destroyBody(body);
		gdxLevel.getPolygons().remove(polygon);
	}
	
	public void addNPC(GDXNPC npc){
		Gdx.app.log("WorldEditorScreen.addNPC", npc.toString());
		if(bodies.containsKey(npc))
			for(Body body : bodies.remove(npc))
				world.destroyBody(body);
		if(!gdxLevel.getNpcs().contains(npc))
			gdxLevel.getNpcs().add(npc);
		bodies.put(npc, Arrays.asList(PhysicsHelper.createCircle(world, NODE_RADIUS, npc.getCoordinates())));
	}

	public void removeNPC(GDXNPC npc) {
		Gdx.app.log("WorldEditorScreen.removeNPC", npc.toString());
		for(Body body : bodies.remove(npc))
			world.destroyBody(body);
		gdxLevel.getNpcs().remove(npc);
	}

	public void addPath(GDXPath path) {
		Gdx.app.log("WorldEditorScreen.addPath", path.toString());
		if(bodies.containsKey(path))
			for(Body body : bodies.remove(path))
				world.destroyBody(body);
		Body body = path.createFixture(world, new FixtureDef(), BodyType.StaticBody);
		if(body != null){
			if(!gdxLevel.getPaths().contains(path))
				gdxLevel.getPaths().add(path);
			List<Body> newBodies = new ArrayList<Body>();
			newBodies.add(body);
			for(Vector2 vertex : path.getNodes())
				newBodies.add(PhysicsHelper.createCircle(world, NODE_RADIUS, vertex));
			bodies.put(path, newBodies);
		}
	}

	public void removePath(GDXPath path) {
		Gdx.app.log("WorldEditorScreen.removePath", path.toString());
		for(Body body : bodies.remove(path))
			world.destroyBody(body);
		gdxLevel.getPaths().remove(path);
	}
	
	public void removePolygonWindow(){
		polygonWindow.remove();
		polygonWindow = null;
	}

	public void removeNPCWindow() {
		npcWindow.remove();
		npcWindow = null;
	}

	public void removePathWindow() {
		pathWindow.remove();
		pathWindow = null;
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(1, camera.zoom + amount + amount*camera.zoom/8);
		Gdx.app.log("WorldEditorScreen.scrolled", "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}
}