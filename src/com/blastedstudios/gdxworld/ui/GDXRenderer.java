package com.blastedstudios.gdxworld.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.blastedstudios.gdxworld.util.BlurUtil;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXBackground;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXTile;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class GDXRenderer {
	private static final Format PREFERRED_FORMAT = Format.valueOf(Properties.get("texture.format", "RGBA8888"));
	private static final boolean USE_MIP_MAPS = Properties.getBool("texture.useMipMaps", true);
	private static final boolean USE_DEPTH_BLUR = Properties.getBool("texture.useDepthBlur", true);
	private static final TextureWrap TEXTURE_WRAP = TextureWrap.valueOf(Properties.get("texture.wrap", "Repeat"));
	private static final float SHAPE_SCALE = Properties.getFloat("renderer.shape.scale", .02f);
	private boolean drawBackground, drawShapes;
	private Map<String, Texture> textureMap = new HashMap<>();
	private Map<String, Map<Float,Texture>> textureBlurMap = new HashMap<>();
	
	public GDXRenderer(boolean drawBackground, boolean drawShapes){
		this.drawBackground = drawBackground;
		this.drawShapes = drawShapes;
	}
	
	public void render(Batch batch, GDXLevel level, OrthographicCamera camera, 
			Iterable<Entry<GDXShape,Body>> bodies){
		if(drawBackground)
			for(GDXBackground background : level.getBackgrounds())
				drawBackground(camera, background, batch);
		if(drawShapes)
			for(Entry<GDXShape,Body> entry : bodies)
				drawShape(camera, entry.getKey(), entry.getValue(), batch);
	}
	
	public void drawTile(OrthographicCamera camera, GDXTile tile, Batch batch) {
		Texture texture = getTexture(tile.getResource());
		if(texture != null) {
			Sprite sprite = new Sprite(new TextureRegion(texture, tile.getX(), tile.getY(), tile.getTilesize(), tile.getTilesize()));
			sprite.setPosition(tile.getPosition().x, tile.getPosition().y);
			sprite.draw(batch);
		}
	}
	
	public void drawShape(OrthographicCamera camera, GDXShape shape, Body body, Batch batch){
		drawShape(camera, shape, body, batch, 1f);
	}
	
	public void drawShape(OrthographicCamera camera, GDXShape shape, Body body, Batch batch, float alpha){
		Texture texture = getTexture(shape.getResource());
		if(!shape.isRepeatable() && texture != null && !shape.getResource().equals("") && body != null){
			Sprite sprite = new Sprite(texture);
			sprite.setScale(SHAPE_SCALE);
			sprite.setRotation((float)Math.toDegrees(body.getAngle()));
			sprite.setPosition(body.getWorldCenter().x - texture.getWidth()/2f, body.getWorldCenter().y - texture.getHeight()/2f);
			if(alpha != 1f)
				sprite.setAlpha(alpha);
			sprite.draw(batch);
		}
	}
	
	public void drawBackground(Camera camera, GDXBackground background, Batch batch){
		Texture texture = background.getDepth() == 1f || !USE_DEPTH_BLUR? 
				getTexture(background.getTexture()) :
				getBlurTexture(background.getTexture(), background.getDepth());
		if(texture != null){
			float depth = Math.max(background.getDepth(), .001f);
			Vector2 offset = new Vector2(texture.getWidth(),texture.getHeight()).scl(.5f * background.getScale());
			Vector2 xy = toParallax(depth, background.getCoordinates(), camera).sub(offset);
			//Need the following boolean in case of failed pushScissors. This may
			//happen if we zoom far out to the point where the scissord area is 0
			boolean scissorCheck = background.isScissor();
			if(scissorCheck){
				Rectangle scissors = new Rectangle();
				Vector2 dimensions = new Vector2(background.getScissorUpperRight().x - background.getScissorLowerLeft().x,
						background.getScissorUpperRight().y - background.getScissorLowerLeft().y);
				Rectangle clipBounds = new Rectangle(background.getScissorLowerLeft().x, background.getScissorLowerLeft().y,
						dimensions.x, dimensions.y);
				Matrix4 batchTransform = new Matrix4();
				batchTransform.translate(camera.position.x/background.getDepth(), camera.position.y/background.getDepth(), 0);
				ScissorStack.calculateScissors(camera, batchTransform, clipBounds, scissors);
				scissorCheck = ScissorStack.pushScissors(scissors);
			}
			batch.draw(texture, xy.x, xy.y, texture.getWidth()*background.getScale(), 
					texture.getHeight()*background.getScale());
			if(scissorCheck){
				batch.flush();
				ScissorStack.popScissors();
			}
		}
	}
	
	/**
	 * Convert from world coordinates to parallax screen coordinates
	 */
	public static Vector2 toParallax(float depth, Vector2 world, Camera camera){
		return toParallax(depth, world, camera.position.x, camera.position.y);
	}
	
	public static Vector2 toParallax(float depth, Vector2 world, float camx, float camy){
		return world.cpy().add(new Vector2(camx, camy).scl(1f-(1f/depth)));
	}
	
	public static Vector2 fromParallax(float depth, Vector2 parallax, Camera camera){
		return fromParallax(depth, parallax, camera.position.x, camera.position.y);
	}
	
	public static Vector2 fromParallax(float depth, Vector2 parallax, float camx, float camy){
		return new Vector2(-camx, -camy).scl(1f-(1f/depth)).add(parallax);
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
	
	public Texture getBlurTexture(String name, float depth){
		Map<Float,Texture> texMap = textureBlurMap.get(name);
		if(texMap == null)
			textureBlurMap.put(name, texMap = new HashMap<>());
		Texture texture = texMap.get(depth);
		if(texture == null){
			int blurRadius = (int) Math.ceil(Math.sqrt(depth));
			texMap.put(depth, texture = new Texture(BlurUtil.blur(new Pixmap(getTextureFile(name)), blurRadius, 2, true)));
		}
		return texture;
	}
	
	public Texture getTexture(String name){
		if(!textureMap.containsKey(name)){
			final Texture EMPTY = new Texture(1,1,Format.RGBA4444);
			FileHandle file = getTextureFile(name);
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
	
	private static FileHandle getTextureFile(String name){
		return FileUtil.find(name);
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
