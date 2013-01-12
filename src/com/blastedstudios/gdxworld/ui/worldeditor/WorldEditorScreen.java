package com.blastedstudios.gdxworld.ui.worldeditor;

import java.util.HashMap;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.ui.AbstractScreen;
import com.blastedstudios.gdxworld.ui.MainScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class WorldEditorScreen extends AbstractScreen<GDXWorldEditor> {
	private static final float LEVEL_RADIUS = 1;
	private OrthographicCamera camera = new OrthographicCamera(28, 20);
	private World world = new World(new Vector2(), true);
	private Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	private LevelInformationWindow levelInfo;
	private Window worldWindow;
	private final GDXWorld gdxWorld;
	private HashMap<GDXLevel, Body> bodies = new HashMap<GDXLevel, Body>();
	
	public WorldEditorScreen(final GDXWorldEditor game, final GDXWorld gdxWorld){
		super(game);
		this.gdxWorld = gdxWorld;
		//initialize ui
		final Button clearButton = new TextButton("Clear", skin);
		final Button saveButton = new TextButton("Save", skin);
		final Button backButton = new TextButton("Back", skin);
		final Button exitButton = new TextButton("Exit", skin);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxWorld.clear();
			}
		});
		saveButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				final JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(null);
				gdxWorld.save(fc.getSelectedFile());
			}
		});
		backButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainScreen(game));
			}
		});
		exitButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		worldWindow = new Window("World Editor", skin);
		worldWindow.add(clearButton);
		worldWindow.row();
		worldWindow.add(saveButton);
		worldWindow.row();
		worldWindow.add(backButton);
		worldWindow.row();
		worldWindow.add(exitButton);
		worldWindow.pack();
		worldWindow.setY(Gdx.graphics.getHeight() - worldWindow.getHeight());
		worldWindow.setMovable(false);
		stage.addActor(worldWindow);
		
		//initialize world
		for(GDXLevel level : gdxWorld.getLevels())
			add(level);
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
		
		if(Gdx.input.isTouched()){
			int x = Gdx.input.getX(), y = Gdx.input.getY();
			Gdx.app.debug("WodlEditorScren.render", "isTouched: x="+x+ " y="+y);
			if(!contains(x,y) && (levelInfo == null || !levelInfo.contains(x, y))){
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
		world.step(delta, 4, 4);
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
		Body body = world.createBody(new BodyDef());
		CircleShape shape = new CircleShape();
		shape.setRadius(LEVEL_RADIUS);
		body.createFixture(shape, 1);
		body.setTransform(gdxLevel.getCoordinates(), 0);
		body.setActive(false);
		bodies.put(gdxLevel, body);
	}

	@Override public boolean scrolled(int amount) {
		camera.zoom = Math.max(1, camera.zoom + amount);
		Gdx.app.log("WorldEditorScreen.scrolled", "Scroll amount: " + amount + " camera.zoom: " + camera.zoom);
		return false;
	}

	/**
	 * @param x screen coordinates
	 * @param y screen coordinates
	 * @return if the world window contains teh given point (in screen coordinates)
	 */
	public boolean contains(float x, float y){
		return worldWindow.getX()+worldWindow.getWidth() > x && worldWindow.getX() < x && //not within world editor window
				worldWindow.getY()+worldWindow.getHeight() > Gdx.graphics.getHeight()-y && worldWindow.getY() < Gdx.graphics.getHeight()-y;
	}
}
