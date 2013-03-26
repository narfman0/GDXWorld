package com.blastedstudios.gdxworld.world.light;

import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.math.Vector2;

public class ConeLight extends GDXLight {
	private static final long serialVersionUID = 1L;
	private Vector2 coordinates = new Vector2();
	private float distance = 10f, directionDegree = 0f, coneDegree = 15f;
	
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

	public float getDirectionDegree() {
		return directionDegree;
	}

	public void setDirectionDegree(float directionDegree) {
		this.directionDegree = directionDegree;
	}

	public float getConeDegree() {
		return coneDegree;
	}

	public void setConeDegree(float coneDegree) {
		this.coneDegree = coneDegree;
	}

	@Override public Object clone() {
		ConeLight clone = new ConeLight();
		clone.setConeDegree(coneDegree);
		clone.setCoordinates(coordinates.cpy());
		clone.setDirectionDegree(directionDegree);
		clone.setDistance(distance);
		return super.clone(clone);
	}

	@Override public Light create(RayHandler handler) {
		return new box2dLight.ConeLight(handler, rays, color, distance, 
				coordinates.x, coordinates.y, directionDegree, coneDegree);
	}
}
