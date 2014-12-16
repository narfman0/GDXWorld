package com.blastedstudios.gdxworld.util.panner;

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
import com.blastedstudios.gdxworld.world.metadata.CameraShot;

public class ScreenLevelPanner implements Disposable{
	private static final float TIME_TO_TRANSITION = Properties.getFloat("screen.panner.transition.time", 10f),
			VELOCITY_SCALAR = Properties.getFloat("screen.panner.velocity.scalar", 1f);
	private final GDXRenderer gdxRenderer;
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
	private float timeTransition;
	private boolean signalled = false;
	private CameraShot shot;

	public ScreenLevelPanner(GDXLevel gdxLevel, GDXRenderer gdxRenderer, ITransitionListener listener){
		this.gdxLevel = gdxLevel;
		this.gdxRenderer = gdxRenderer;
		this.listener = listener;
		random = new Random();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = Properties.getFloat("gameplay.camera.zoom", .02f);
		world = new World(new Vector2(0, -10), true);
		struct = gdxLevel.createLevel(world);
		rayHandler = struct.lights.rayHandler;
		tiledMeshRenderer = new TiledMeshRenderer(gdxRenderer, gdxLevel.getPolygons());
		assetManager = gdxLevel.createAssetManager(false);
		try{
			int shotIndex = random.nextInt(gdxLevel.getMetadata().getCameraShots().size());
			shot = gdxLevel.getMetadata().getCameraShots().get(shotIndex);
			camera.position.set(shot.getPosition(), 0f);
		}catch(Exception e){
			//probably doesn't have shots
			shot = new CameraShot();
		}
	}
	
	public boolean update(){
		boolean complete = assetManager.update();
		if(complete)
			timeTransition = TIME_TO_TRANSITION;
		return complete;
	}

	public void render(float dt){
		if(shot != null)
			camera.position.add(shot.getVelocity().x*VELOCITY_SCALAR*dt, shot.getVelocity().y*VELOCITY_SCALAR*dt, 0);
		camera.update();
		rayHandler.setCombinedMatrix(camera.combined);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		gdxRenderer.render(assetManager, spriteBatch, gdxLevel, camera, struct.bodies.entrySet());
		spriteBatch.end();
		tiledMeshRenderer.render(camera);
		world.step(dt, 10, 10);
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
