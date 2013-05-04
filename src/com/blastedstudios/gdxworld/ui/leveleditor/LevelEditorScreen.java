package com.blastedstudios.gdxworld.ui.leveleditor;

import java.io.File;
import java.util.Collection;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.IMode;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelEditorScreen extends AbstractScreen {
	private final OrthographicCamera camera = new OrthographicCamera(28, 20);
	private World world;
	private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private final GDXRenderer gdxRenderer = new GDXRenderer(true, false);
	private final ShapeRenderer renderer = new ShapeRenderer();
	private LevelWindow levelWindow;
	private GDXLevel gdxLevel;
	private boolean live;
	private final Collection<IMode> modes = PluginUtil.getPluginsSorted(IMode.class);
	private IMode mode;
	
	public LevelEditorScreen(final Game game, final GDXWorld gdxWorld, 
			final GDXLevel gdxLevel, final File lastSavedFile){
		super(game);
		this.gdxLevel = gdxLevel;
		for(IMode child : modes)
			child.init(this);
		stage.addActor(levelWindow = new LevelWindow(game, skin, gdxWorld, gdxLevel, this, lastSavedFile));
		camera.zoom = 4;
		loadLevel();
	}
	
	public void loadLevel(){
		if(world != null)
			world.dispose();
		world = new World(new Vector2(0,live ? -10 : 0), true);
		for(IMode child : modes)
			child.loadLevel(gdxLevel);
	}
	
	@Override public void render(float delta) {
		super.render(delta);
		camera.update();
		if(!Gdx.graphics.isGL20Available())
			camera.apply(Gdx.gl10);
		if(live)
			world.step(delta, 4, 4);
		gdxRenderer.render(gdxLevel, camera, null);
		debugRenderer.render(world, camera.combined);
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Line);
		for(IMode child : modes)
			child.render(delta, camera, renderer);
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
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		Gdx.app.debug("LevelEditorScreen.touchDown", "x="+x+ " y="+y);
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		if(!levelWindow.contains(x,y) && !mode.contains(x,y))
			mode.touchDown(x, y, x1, y1);
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int x1, int y1){
		if(!levelWindow.contains(x,y) && !mode.contains(x,y))
			mode.touchUp(x, y, x1, y1);
		return false;
	}

	@Override public boolean touchDragged(int x, int y, int ptr) {
		if(!levelWindow.contains(x,y) && !mode.contains(x,y))
			mode.touchDragged(x, y, ptr);
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
}
