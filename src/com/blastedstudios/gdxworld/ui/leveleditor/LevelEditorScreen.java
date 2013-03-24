package com.blastedstudios.gdxworld.ui.leveleditor;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.JointMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.AbstractMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.NPCMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.PathMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.PolygonMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.QuestMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.LevelWindow;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXNPC;
import com.blastedstudios.gdxworld.world.GDXPath;
import com.blastedstudios.gdxworld.world.GDXWorld;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class LevelEditorScreen extends AbstractScreen<GDXWorldEditor> {
	public static final float NODE_RADIUS = 1;
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private World world = new World(new Vector2(), true);
	private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final GDXRenderer gdxRenderer = new GDXRenderer(true);
	private final Map<Object, List<Body>> bodies = new HashMap<Object, List<Body>>();
	private final Map<String, Joint> joints = new HashMap<String, Joint>();
	private LevelWindow levelWindow;
	private GDXLevel gdxLevel;
	private boolean live;
	private AbstractMouseMode mouseMode;
	
	public LevelEditorScreen(final GDXWorldEditor game, final GDXWorld gdxWorld, 
			final GDXLevel gdxLevel, final File lastSavedFile){
		super(game);
		this.gdxLevel = gdxLevel;
		stage.addActor(levelWindow = new LevelWindow(game, skin, gdxWorld, gdxLevel, this, lastSavedFile));
		mouseMode = new PolygonMouseMode(this);
		camera.zoom = 4;
		resetLevel();
	}
	
	private void resetLevel(){
		for(GDXShape shape : gdxLevel.getShapes())
			new PolygonMouseMode(this).addPolygon(shape);
		for(GDXNPC npc : gdxLevel.getNpcs())
			new NPCMouseMode(this).addNPC(npc);
		for(GDXPath path : gdxLevel.getPaths())
			new PathMouseMode(this).addPath(path);
		JointMouseMode jointMode = new JointMouseMode(this);
		for(GDXJoint joint : gdxLevel.getJoints())
			jointMode.addJoint(joint);
		jointMode.clean();
		QuestMouseMode questMode = new QuestMouseMode(this);
		for(GDXQuest quest : gdxLevel.getQuests())
			questMode.addQuest(quest);
		questMode.clean();
	}
	
	@Override public void render(float delta) {
		super.render(delta);
		camera.update();
		camera.apply(Gdx.gl10);
		if(live)
			world.step(delta, 4, 4);
		gdxRenderer.render(gdxLevel, camera);
		debugRenderer.render(world, camera.combined);
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
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		Gdx.app.debug("LevelEditorScreen.touchDown", "x="+x+ " y="+y);
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		if(!levelWindow.contains(x,y) && !mouseMode.contains(x,y))
			mouseMode.touchDown(x, y, x1, y1);
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int x1, int y1){
		mouseMode.touchUp(x, y, x1, y1);
		return false;
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
	
	public boolean isLive(){
		return live;
	}
	
	public World getWorld(){
		return world;
	}
	
	public Map<Object, List<Body>> getBodies(){
		return bodies;
	}
	
	public Map<String,Joint> getJoints(){
		return joints;
	}
	
	public GDXLevel getLevel(){
		return gdxLevel;
	}

	public void setMouseMode(AbstractMouseMode mouseMode) {
		this.mouseMode.clean();
		this.mouseMode = mouseMode;
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public GDXRenderer getGDXRenderer(){
		return gdxRenderer;
	}

	/**
	 * Clear all saved information about bodies, joints, and other world info
	 */
	public void clear() {
		bodies.clear();
		joints.clear();
		world = new World(new Vector2(), true);
	}
}
