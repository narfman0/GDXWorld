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
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.physics.CollideCallback;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.joints.JointWindow;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXNPC;
import com.blastedstudios.gdxworld.world.GDXPath;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.GearJoint;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class LevelEditorScreen extends AbstractScreen<GDXWorldEditor> {
	private static final float NODE_RADIUS = 1;
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private World world = new World(new Vector2(), true);
	private final Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	private final HashMap<Object, List<Body>> bodies = new HashMap<Object, List<Body>>();
	private final HashMap<String, Joint> joints = new HashMap<String, Joint>();
	private Body lastTouchPolygon;
	private Vector2 lastTouchCoordinates, lastTouchPolygonLocalCoordinates;
	private LevelWindow levelWindow;
	private PolygonWindow polygonWindow;
	private CircleWindow circleWindow;
	private NPCWindow npcWindow;
	private PathWindow pathWindow;
	private GDXLevel gdxLevel;
	private final JointWindow jointWindow;
	private boolean live;
	
	public LevelEditorScreen(final GDXWorldEditor game, final GDXWorld gdxWorld, final GDXLevel gdxLevel){
		super(game);
		this.gdxLevel = gdxLevel;
		jointWindow = new JointWindow(skin, this);
		stage.addActor(levelWindow = new LevelWindow(game, skin, gdxWorld, gdxLevel, this));
		camera.zoom += 3;
		resetLevel();
	}
	
	private void resetLevel(){
		for(GDXShape shape : gdxLevel.getShapes())
			addPolygon(shape);
		for(GDXNPC npc : gdxLevel.getNpcs())
			addNPC(npc);
		for(GDXPath path : gdxLevel.getPaths())
			addPath(path);
		for(GDXJoint joint : gdxLevel.getJoints())
			addJoint(joint);
	}
	
	@Override public void render(float delta) {
		super.render(delta);
		camera.update();
		camera.apply(Gdx.gl10);
		if(live)
			world.step(delta, 4, 4);
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
		Gdx.app.debug("LevelEditorScreen.touchDown", "x="+x+ " y="+y);
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		if(!levelWindow.contains(x,y) && 
				(polygonWindow == null || !polygonWindow.contains(x, y)) &&
				(npcWindow == null || !npcWindow.contains(x, y)) &&
				(pathWindow == null || !pathWindow.contains(x, y)) &&
				(jointWindow == null || !jointWindow.contains(x, y)) &&
				(circleWindow == null || !circleWindow.contains(x, y))){
			if(levelWindow.isPolygonMode()){
				GDXPolygon polygon = gdxLevel.getClosestPolygon(coordinates.x, coordinates.y);
				if(polygon == null || polygon.getDistance(coordinates.x, coordinates.y) > NODE_RADIUS)
					polygon = new GDXPolygon();
				if(polygonWindow == null)
					stage.addActor(polygonWindow = new PolygonWindow(skin, this, polygon));
				Vector2 vertex = new Vector2(coordinates.x, coordinates.y);
				if(polygon.getVertices().isEmpty())
					polygonWindow.add(vertex);
			}else if(levelWindow.isCircleMode()){
				GDXCircle circle = gdxLevel.getClosestCircle(coordinates.x, coordinates.y);
				if(circle == null || circle.getDistance(coordinates.x, coordinates.y) > NODE_RADIUS)
					circle = new GDXCircle();
				if(circleWindow == null)
					stage.addActor(circleWindow = new CircleWindow(skin, this, circle));
				circleWindow.setCenter(new Vector2(coordinates.x, coordinates.y));
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
			}else if(levelWindow.isJointMode()){
				GDXJoint joint = gdxLevel.getClosestJoint(coordinates.x, coordinates.y, world);
				if(joint != null && joint.getCenter().dst(coordinates.x, coordinates.y) < NODE_RADIUS)
					jointWindow.setSelected(joint);
				jointWindow.clicked(new Vector2(coordinates.x, coordinates.y));
			}else if(levelWindow.isLiveMode()){
				CollideCallback callback = new CollideCallback();
				world.QueryAABB(callback, coordinates.x-.01f, coordinates.y-.01f, coordinates.x+.01f, coordinates.y+.01f);
				lastTouchPolygon = callback.getBody();
				if(lastTouchPolygon != null){
					lastTouchCoordinates = new Vector2(coordinates.x, coordinates.y);
					lastTouchPolygonLocalCoordinates = lastTouchPolygon.getLocalPoint(lastTouchCoordinates);
					Gdx.app.log("LevelEditorScreen.touchDown", "touched world coords: " + lastTouchCoordinates +
							" touched poly local coords: " + lastTouchPolygonLocalCoordinates);
				}
			}
		}
		return false;
	}

	@Override public boolean touchUp(int x, int y, int arg2, int arg3) {
		if(levelWindow.isLiveMode()){
			Vector3 coordinates = new Vector3(x,y,0);
			camera.unproject(coordinates);
			if(lastTouchPolygon != null){
				Vector2 impulse = new Vector2(coordinates.x, coordinates.y).
						sub(lastTouchCoordinates).mul(lastTouchPolygon.getMass());
				Gdx.app.log("LevelEditorScreen.touchUp", "applying impulse: " + 
						impulse + " on body: " + lastTouchPolygon.getPosition());
				lastTouchPolygon.applyLinearImpulse(impulse, 
						lastTouchPolygonLocalCoordinates.add(lastTouchPolygon.getPosition()));
				lastTouchCoordinates = null;
				lastTouchPolygon = null;
				lastTouchPolygonLocalCoordinates = null;
			}
		}
		return false;
	}
	
	public void addPolygon(GDXShape shape){
		Gdx.app.log("WorldEditorScreen.addPolygon", shape.toString());
		if(bodies.containsKey(shape))
			for(Body body : bodies.remove(shape))
				world.destroyBody(body);
		Body body = shape.createFixture(world, !live);
		if(body != null){
			if(!gdxLevel.getShapes().contains(shape))
				gdxLevel.getShapes().add(shape);
			List<Body> newBodies = new ArrayList<Body>();
			newBodies.add(body);
			if(!live)
				if(shape instanceof GDXPolygon){
					for(Vector2 vertex : ((GDXPolygon)shape).getVertices()){
						Vector2 position = vertex.cpy().add(shape.getCenter());
						newBodies.add(PhysicsHelper.createCircle(world, NODE_RADIUS, position, BodyType.StaticBody));
					}
				}else if(shape instanceof GDXCircle)
					newBodies.add(PhysicsHelper.createCircle(world, ((GDXCircle)shape).getRadius(), 
							shape.getCenter(), BodyType.StaticBody));
			bodies.put(shape, newBodies);
		}
	}

	public void removePolygon(GDXPolygon polygon) {
		Gdx.app.log("WorldEditorScreen.removePolygon", polygon.toString());
		for(Body body : bodies.remove(polygon))
			world.destroyBody(body);
		gdxLevel.getShapes().remove(polygon);
	}
	
	public void addNPC(GDXNPC npc){
		Gdx.app.log("WorldEditorScreen.addNPC", npc.toString());
		if(bodies.containsKey(npc))
			for(Body body : bodies.remove(npc))
				world.destroyBody(body);
		if(!gdxLevel.getNpcs().contains(npc))
			gdxLevel.getNpcs().add(npc);
		bodies.put(npc, Arrays.asList(PhysicsHelper.createCircle(world, NODE_RADIUS, npc.getCoordinates(), BodyType.StaticBody)));
	}

	public void removeNPC(GDXNPC npc) {
		Gdx.app.log("WorldEditorScreen.removeNPC", npc.toString());
		for(Body body : bodies.remove(npc))
			world.destroyBody(body);
		gdxLevel.getNpcs().remove(npc);
	}

	public void addPath(GDXPath path) {
		Gdx.app.log("WorldEditorScreen.addPath", path.toString());
		if(live){
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
					newBodies.add(PhysicsHelper.createCircle(world, NODE_RADIUS, vertex, BodyType.StaticBody));
				bodies.put(path, newBodies);
			}
		}
	}

	public void removePath(GDXPath path) {
		Gdx.app.log("WorldEditorScreen.removePath", path.toString());
		for(Body body : bodies.remove(path))
			world.destroyBody(body);
		gdxLevel.getPaths().remove(path);
	}

	public void addJoint(GDXJoint gdxJoint) {
		removeJoint(gdxJoint);
		if(gdxJoint instanceof GearJoint){
			GearJoint gear = (GearJoint)gdxJoint;
			gear.initialize(joints.get(gear.getJoint1()), joints.get(gear.getJoint2()));
		}
		joints.put(gdxJoint.getName(), gdxJoint.attach(world));
		if(!gdxLevel.getJoints().contains(gdxJoint))
			gdxLevel.getJoints().add(gdxJoint);
		if(!live){
			Body body = PhysicsHelper.createCircle(world, NODE_RADIUS, gdxJoint.getCenter(), BodyType.DynamicBody);
			bodies.put(gdxJoint.getName(), Arrays.asList(body));
		}
		Gdx.app.log("LevelEditorScreen.addJoint", "Added joint " + gdxJoint.toString());
	}

	public void removeJoint(GDXJoint joint) {
		Gdx.app.log("LevelEditorScreen.removeJoint", "Removing joint " + joint.toString());
		if(bodies.containsKey(joint.getName()))
			for(Body body : bodies.remove(joint.getName()))
					world.destroyBody(body);
		if(joints.containsKey(joint.getName()))
			world.destroyJoint(joints.remove(joint.getName()));
		if(gdxLevel.getJoints().contains(joint))
			gdxLevel.getJoints().remove(joint);
	}

	public void addCircle(GDXCircle circle) {
		Gdx.app.log("WorldEditorScreen.addCircle", circle.toString());
		if(bodies.containsKey(circle))
			for(Body body : bodies.remove(circle))
				world.destroyBody(body);
		Body body = circle.createFixture(world, !live);
		if(body != null){
			bodies.put(circle, Arrays.asList(body));
			if(!gdxLevel.getShapes().contains(circle))
				gdxLevel.getShapes().add(circle);
		}
	}

	public void removeCircle(GDXCircle circle) {
		gdxLevel.getShapes().remove(circle);
		if(bodies.containsKey(circle))
			for(Body body : bodies.get(circle))
				world.destroyBody(body);
	}
	
	public void removePolygonWindow(){
		if(polygonWindow != null)
			polygonWindow.remove();
		polygonWindow = null;
	}
	
	public void removeJointWindow(){
		jointWindow.remove();
	}

	public void removeNPCWindow() {
		if(npcWindow != null)
			npcWindow.remove();
		npcWindow = null;
	}

	public void removePathWindow() {
		if(pathWindow != null)
			pathWindow.remove();
		pathWindow = null;
	}

	public void removeCircleWindow() {
		if(circleWindow != null)
			circleWindow.remove();
		circleWindow = null;
	}
	
	public void addJointWindow(){
		stage.addActor(jointWindow);
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(1, camera.zoom + amount + amount*camera.zoom/8);
		Gdx.app.log("LevelEditorScreen.scrolled", "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}

	/**
	 * Start simulating the world in a tester type mode
	 * @param live simulate or not
	 */
	public void setLive(boolean live) {
		this.live = live;
		bodies.clear();
		joints.clear();
		world.dispose();
		world = new World(live ? new Vector2(0,-10) : new Vector2(), true);
		resetLevel();
	}
}