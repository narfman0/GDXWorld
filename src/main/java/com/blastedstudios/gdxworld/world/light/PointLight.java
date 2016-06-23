package com.blastedstudios.gdxworld.world.light;

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
		return new box2dLight.PointLight(handler, rays, GDXLight.convert(color),
				distance, coordinates.x, coordinates.y);
	}

	@Override public int compareTo(GDXLight o) {
		if(getClass() != o.getClass())
			return getClass().getName().compareTo(o.getClass().getName());
		int coordXCompare = ((Float)coordinates.x).compareTo(((PointLight)o).coordinates.x); 
		if(coordXCompare != 0)
			return coordXCompare;
		return ((Float)coordinates.y).compareTo(((PointLight)o).coordinates.y);
	}
}
