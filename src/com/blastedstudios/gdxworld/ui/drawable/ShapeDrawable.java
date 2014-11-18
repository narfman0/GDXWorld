package com.blastedstudios.gdxworld.ui.drawable;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class ShapeDrawable extends Drawable {
	private final GDXShape shape;
	private final Body body;
	
	public ShapeDrawable(GDXShape shape, Body body){
		this.shape = shape;
		this.body = body;
	}

	@Override public void render(float dt, AssetManager assetManager, Batch batch, Camera camera, GDXRenderer renderer) {
		renderer.drawShape(assetManager, camera, shape, body, batch);
	}
}