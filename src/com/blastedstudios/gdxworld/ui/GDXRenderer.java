package com.blastedstudios.gdxworld.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.world.GDXBackground;
import com.blastedstudios.gdxworld.world.GDXLevel;

public class GDXRenderer {
	private boolean drawBackground;
	private Map<String, Texture> textureMap;
	private SpriteBatch batch;
	
	public GDXRenderer(boolean drawBackground){
		this.drawBackground = drawBackground;
		textureMap = new HashMap<String, Texture>();
		batch = new SpriteBatch();
	}
	
	public void render(GDXLevel level, Camera camera){
		batch.setProjectionMatrix(camera.combined);
        batch.disableBlending();
        batch.begin();
        
		if(drawBackground)
			for(GDXBackground background : level.getBackgrounds()){
				Texture texture = getTexture(background.getTexture());
				if(texture != null){
					float depth = Math.max(background.getDepth(), .001f);
					Vector2 offset = new Vector2(texture.getWidth(),texture.getHeight()).mul(background.getScale()/2f);
					Vector2 xy = toParallax(depth, background.getCoordinates(), camera).sub(offset);
					batch.draw(texture, xy.x, xy.y, texture.getWidth()*background.getScale(), 
							texture.getHeight()*background.getScale());
				}
			}
		batch.end();
	}
	
	/**
	 * Convert from world coordinates to parallax screen coordinates
	 */
	public static Vector2 toParallax(float depth, Vector2 world, Camera camera){
		Vector2 camOffset = new Vector2(camera.position.x,camera.position.y).div(depth);
		return world.cpy().sub(camOffset);
	}

	public boolean isDrawBackground() {
		return drawBackground;
	}

	public void setDrawBackground(boolean drawBackground) {
		this.drawBackground = drawBackground;
	}
	
	public Texture getTexture(String name){
		if(!textureMap.containsKey(name)){
			FileHandle file = FileUtil.find(Gdx.files.internal("data"), name);
			if(file == null)
				Gdx.app.error("GDXRenderer.render", "Texture " + name + " not found!");
			else{
				try{
					textureMap.put(name, new Texture(file));
					Gdx.app.log("GDXRenderer.render", "Added texture " + name);
				}catch(Exception e){
					Gdx.app.error("GDXRenderer.render", "Exception loading texture " + name);
					e.printStackTrace();
				}
			}
		}
		return textureMap.get(name);
	}
}
