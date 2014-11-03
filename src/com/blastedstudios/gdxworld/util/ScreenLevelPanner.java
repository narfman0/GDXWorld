package com.blastedstudios.gdxworld.util;

import java.util.Random;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.util.TiledMeshRenderer;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXLevel.CreateLevelReturnStruct;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class ScreenLevelPanner implements Disposable{
	private static final float TIME_TO_TRANSITION = Properties.getFloat("screen.panner.transition.time", 10f);
	private final GDXRenderer gdxRenderer;
	private final GDXWorld gdxWorld;
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private final ITransitionListener listener;
	private AssetManager assetManager;
	private OrthographicCamera camera;
	private TiledMeshRenderer tiledMeshRenderer;
	private RayHandler rayHandler;
	private GDXLevel gdxLevel;
	private CreateLevelReturnStruct struct;
	private World world;
	private Random random;
	private float cameraMoveAmountX, timeTransition;
	private boolean signalled = false;

	public ScreenLevelPanner(GDXWorld gdxWorld, GDXRenderer gdxRenderer, ITransitionListener listener){
		this.gdxWorld = gdxWorld;
		this.gdxRenderer = gdxRenderer;
		this.listener = listener;
		random = new Random();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = Properties.getFloat("gameplay.camera.zoom", .02f);
		transitionLevel();
	}

	public void transitionLevel(){
		GDXLevel nextLevel;
		do{
			nextLevel = gdxWorld.getLevels().get(random.nextInt(gdxWorld.getLevels().size()));
		}while(nextLevel == gdxLevel && gdxWorld.getLevels().size() != 1);
		initializeLevel(nextLevel);
	}

	public void initializeLevel(GDXLevel level){
		this.gdxLevel = level;
		world = new World(new Vector2(0, -10), true);
		struct = level.createLevel(world);
		rayHandler = struct.lights.rayHandler;
		tiledMeshRenderer = new TiledMeshRenderer(gdxRenderer, level.getPolygons());
		cameraMoveAmountX = random.nextFloat()*.1f - .05f;
		assetManager = gdxLevel.createAssetManager(false);
	}
	
	public boolean update(){
		boolean complete = assetManager.update();
		if(complete)
			timeTransition = TIME_TO_TRANSITION;
		return complete;
	}

	public void render(float dt){
		camera.position.add(cameraMoveAmountX, 0, 0);
		camera.update();
		rayHandler.setCombinedMatrix(camera.combined);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		gdxRenderer.render(assetManager, spriteBatch, gdxLevel, camera, struct.bodies.entrySet());
		spriteBatch.end();
		tiledMeshRenderer.render(camera);
		world.step(1f/30f, 10, 10);
		if(Properties.getBool("lighting.draw", false))
			rayHandler.updateAndRender();
		timeTransition -= dt;
		if(!signalled && timeTransition <= 0f){
			listener.transition();
			signalled = true;
		}
	}
	
	@Override public void dispose(){
		assetManager.dispose();
	}
	
	public interface ITransitionListener{
		void transition();
	}
}
