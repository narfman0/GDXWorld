package com.blastedstudios.gdxworld.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXBackground;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class GDXRenderer {
	private static final Format PREFERRED_FORMAT = Format.valueOf(Properties.get("texture.format", "RGBA8888"));
	private static final boolean USE_MIP_MAPS = Properties.getBool("texture.useMipMaps", true);
	private static final TextureWrap TEXTURE_WRAP = TextureWrap.valueOf(Properties.get("texture.wrap", "Repeat"));
	private static final float SHAPE_SCALE = Properties.getFloat("renderer.shape.scale", .02f);
	private boolean drawBackground, drawShapes;
	private Map<String, Texture> textureMap;
	private SpriteBatch batch;
	
	public GDXRenderer(boolean drawBackground, boolean drawShapes){
		this.drawBackground = drawBackground;
		this.drawShapes = drawShapes;
		textureMap = new HashMap<String, Texture>();
		batch = new SpriteBatch();
		if(!Properties.getBool("renderer.blend.enabled", true))
			batch.disableBlending();
	}
	
	public void render(GDXLevel level, OrthographicCamera camera, Iterable<Entry<GDXShape,Body>> bodies){
		batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
		if(drawBackground)
			for(GDXBackground background : level.getBackgrounds())
				drawBackground(camera, background, batch);
		if(drawShapes)
			for(Entry<GDXShape,Body> entry : bodies)
				drawShape(camera, entry.getKey(), entry.getValue(), batch);
		batch.end();
	}
	
	public void drawShape(OrthographicCamera camera, GDXShape shape, Body body, SpriteBatch batch){
		Texture texture = getTexture(shape.getResource());
		if(!shape.isRepeatable() && texture != null && !shape.getResource().equals("") && body != null){
			Sprite sprite = new Sprite(texture);
			sprite.setScale(SHAPE_SCALE);
			sprite.setRotation((float)Math.toDegrees(body.getAngle()));
			sprite.setPosition(body.getPosition().x - texture.getWidth()/2f, body.getPosition().y - texture.getHeight()/2f);
			sprite.draw(batch);
		}
	}
	
	public void drawBackground(Camera camera, GDXBackground background, SpriteBatch batch){
		Texture texture = getTexture(background.getTexture());
		if(texture != null){
			float depth = Math.max(background.getDepth(), .001f);
			Vector2 offset = new Vector2(texture.getWidth(),texture.getHeight()).scl(.5f * background.getScale());
			Vector2 xy = toParallax(depth, background.getCoordinates(), camera).sub(offset);
			batch.draw(texture, xy.x, xy.y, texture.getWidth()*background.getScale(), 
					texture.getHeight()*background.getScale());
		}
	}
	
	/**
	 * Convert from world coordinates to parallax screen coordinates
	 */
	public static Vector2 toParallax(float depth, Vector2 world, Camera camera){
		return world.cpy().add(new Vector2(camera.position.x, camera.position.y).scl(1f-(1f/depth)));
	}
	
	public static Vector2 fromParallax(float depth, Vector2 parallax, Camera camera){
		return new Vector2(-camera.position.x, -camera.position.y).scl(1f-(1f/depth)).add(parallax);
	}

	public boolean isDrawBackground() {
		return drawBackground;
	}

	public void setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
	}

	public boolean isDrawShapes() {
		return drawShapes;
	}

	public void setDrawShapes(boolean drawShapes) {
		this.drawShapes = drawShapes;
	}
	
	public Texture getTexture(String name){
		if(!textureMap.containsKey(name)){
			final Texture EMPTY = new Texture(1,1,Format.RGBA4444);
			FileHandle file = FileUtil.find(Gdx.files.internal("data"), name);
			if(file != null){
				try{
					Texture texture = new Texture(file, PREFERRED_FORMAT, USE_MIP_MAPS);
					texture.setWrap(TEXTURE_WRAP, TEXTURE_WRAP);
					textureMap.put(name, texture);
					Gdx.app.log("GDXRenderer.render", "Added texture " + name);
				}catch(Exception e){
					Gdx.app.error("GDXRenderer.render", "Texture found but error loading " + 
							name + ", using empty. Exception: " + e.getMessage());
					e.printStackTrace();
					textureMap.put(name, EMPTY);
				}
			}else{
				Gdx.app.error("GDXRenderer.render", "Texture " + name + " not found, using empty");
				textureMap.put(name, EMPTY);
			}
		}
		return textureMap.get(name);
	}
	
	public static Vector2 toWorldCoordinates(Camera cam, Vector2 screen){
		Vector3 coords = new Vector3(screen.x, screen.y, 0);
		cam.unproject(coords);
		return new Vector2(coords.x, coords.y);
	}

	public static Vector2 toScreenCoordinates(Camera cam, Vector2 world){
		Vector3 coords = new Vector3(world.x, world.y, 0);
		cam.project(coords);
		return new Vector2(coords.x, coords.y);
	}
}
