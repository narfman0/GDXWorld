package com.blastedstudios.gdxworld.ui.drawable;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.blastedstudios.gdxworld.ui.GDXRenderer;

public abstract class Drawable {
	public abstract void render(float dt, AssetManager assetManager, Batch batch, Camera camera, GDXRenderer renderer);
}