package com.blastedstudios.gdxworld.ui.drawable;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.world.GDXBackground;

public class BackgroundDrawable extends Drawable {
	public final GDXBackground background;

	public BackgroundDrawable(GDXBackground background){
		this.background = background;
	}
	
	@Override public void render(float dt, AssetManager assetManager, Batch batch, Camera camera, GDXRenderer renderer) {
		renderer.drawBackground(assetManager, camera, background, batch);
	}
}