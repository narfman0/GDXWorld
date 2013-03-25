package com.blastedstudios.gdxworld.world.folder;

import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.math.Vector2;

public class PointLight extends GDXLight {
	private static final long serialVersionUID = 1L;
	private Vector2 coordinates = new Vector2();
	private float distance = 10f;

	public Vector2 getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Vector2 coordinates) {
		this.coordinates = coordinates;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	@Override public Object clone() {
		PointLight clone = new PointLight();
		clone.setCoordinates(coordinates.cpy());
		clone.setDistance(distance);
		return super.clone(clone);
	}

	@Override public Light create(RayHandler handler) {
		return new box2dLight.PointLight(handler, rays, color, distance, 
				coordinates.x, coordinates.y);
	}
}
