package com.blastedstudios.gdxworld.world.light;

import box2dLight.Light;
import box2dLight.RayHandler;

public class DirectionalLight extends GDXLight {
	private static final long serialVersionUID = 1L;
	private float direction = 0;
	
	public float getDirection() {
		return direction;
	}
	
	public void setDirection(float direction) {
		this.direction = direction;
	}
	
	@Override public Object clone(){
		DirectionalLight clone = new DirectionalLight();
		clone.setDirection(direction);
		return super.clone(clone);
	}

	@Override public Light create(RayHandler handler) {
		return new box2dLight.DirectionalLight(handler, rays, GDXLight.convert(color), direction);
	}
}
