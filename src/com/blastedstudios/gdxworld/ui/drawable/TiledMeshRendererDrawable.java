package com.blastedstudios.gdxworld.ui.drawable;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.TiledMeshRenderer;

public class TiledMeshRendererDrawable extends Drawable {
	private final TiledMeshRenderer tiledMeshRenderer;
	
	public TiledMeshRendererDrawable(TiledMeshRenderer tiledMeshRenderer){
		this.tiledMeshRenderer = tiledMeshRenderer;
	}

	@Override public void render(float dt, AssetManager assetManager, Batch batch, Camera camera, GDXRenderer renderer) {
		tiledMeshRenderer.render(camera);
	}
}