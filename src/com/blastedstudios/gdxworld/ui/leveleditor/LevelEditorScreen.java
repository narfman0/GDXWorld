package com.blastedstudios.gdxworld.ui.leveleditor;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.blastedstudios.gdxworld.util.GDXGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.MouseCameraScroller;
import com.blastedstudios.gdxworld.util.AssetManagerWrapper;
import com.blastedstudios.gdxworld.util.IMode;
import com.blastedstudios.gdxworld.util.LoadPriorityComparator;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelEditorScreen extends AbstractScreen {
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private World world;
	private final GDXWorld gdxWorld;
	private final FileHandle selectedFile;
	private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final GDXRenderer gdxRenderer = new GDXRenderer(true, false);
	private LevelWindow levelWindow;
	private GDXLevel gdxLevel;
	private boolean live;
	private final Collection<IMode> modes = PluginUtil.getPluginsSorted(IMode.class);
	private IMode mode;
	private MouseCameraScroller scroller = new MouseCameraScroller(camera, 2);
	private final ShapeRenderer renderer = new ShapeRenderer();
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private final AssetManagerWrapper assetManager;
        
	public LevelEditorScreen(final GDXGame game, final GDXWorld gdxWorld, 
			final FileHandle selectedFile, final GDXLevel gdxLevel, AssetManagerWrapper assetManager){
		super(game, "data/ui/uiskin.json");
		this.gdxLevel = gdxLevel;
		this.gdxWorld = gdxWorld;
		this.selectedFile = selectedFile;
		this.assetManager = assetManager;
		inputMultiplexer.addProcessor(scroller);
		for(IMode child : modes)
			child.init(this);
		stage.addActor(levelWindow = new LevelWindow(game, skin, gdxWorld, gdxLevel, this));
		camera.zoom = 4;
		loadLevel();
	}
	
	public void loadLevel(){
		if(world != null)
			world.dispose();
		world = new World(new Vector2(0,live ? -10 : 0), true);
		LinkedList<IMode> loadSorted = new LinkedList<>(modes);
		Collections.sort(loadSorted, new LoadPriorityComparator());
		for(IMode child : loadSorted)
			child.loadLevel(gdxLevel);
	}
	
	@Override public void render(float delta) {
		super.render(delta);
		camera.update();
		if(live)
			world.step(delta * Properties.getFloat("level.world.step.scalar", 1f), 4, 4);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		gdxRenderer.render(assetManager, spriteBatch, gdxLevel, camera, null);
		spriteBatch.end();
		for(IMode child : modes)
			child.render(delta, camera, gdxRenderer, renderer);
		debugRenderer.render(world, camera.combined);
		if(Gdx.input.isKeyPressed(Keys.UP))
			camera.position.y+=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.DOWN))
			camera.position.y-=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
			camera.position.x+=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			camera.position.x-=camera.zoom;
		if(Gdx.input.isKeyPressed(Keys.S) && Gdx.input.isKeyPressed(Keys.CONTROL_LEFT))
			gdxWorld.save(selectedFile);
		stage.draw();
	}
	
	@Override public boolean keyDown(int key) {
		switch(key){
		case Keys.ESCAPE:
			game.popScreen();
			break;
		}
		return super.keyDown(key);
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		Gdx.app.debug("LevelEditorScreen.touchDown", "x="+x+ " y="+y);
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		if(!levelWindow.contains(x,y) && !mode.contains(x,y) && y1 != 2)
		    mode.touchDown(x, y, x1, y1);
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int x1, int y1){
	    if(!levelWindow.contains(x,y) && !mode.contains(x,y) && y1 != 2)
		mode.touchUp(x, y, x1, y1);
	    return false;
	}

	@Override public boolean touchDragged(int x, int y, int ptr) {
	    if(!levelWindow.contains(x,y) && !mode.contains(x,y))
		mode.touchDragged(x, y, ptr);
	    return false;
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(.01f, camera.zoom + amount*camera.zoom/4f);
		Gdx.app.log("LevelEditorScreen.scrolled", "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}

	/**
	 * Start simulating the world in a tester type mode
	 * @param live simulate or not
	 */
	public void setLive(boolean live) {
		this.live = live;
		loadLevel();
	}
	
	public boolean isLive(){
		return live;
	}
	
	public World getWorld(){
		return world;
	}
	
	public GDXLevel getLevel(){
		return gdxLevel;
	}

	public void setMode(IMode mode) {
		if(this.mode != null)
			this.mode.clean();
		this.mode = mode;
		mode.start();
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public GDXRenderer getGDXRenderer(){
		return gdxRenderer;
	}

	public Collection<IMode> getModes() {
		return modes;
	}
	
	public static float getNodeRadius(){
		return Properties.getFloat("screen.level.node.radius", .3f);
	}
	
	@Override public void dispose() {
		assetManager.dispose();
	}

	public AssetManagerWrapper getAssetManager() {
		return assetManager;
	}
}
