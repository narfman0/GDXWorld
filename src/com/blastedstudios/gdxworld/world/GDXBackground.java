package com.blastedstudios.gdxworld.world;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents an item that is rendered in the background. THis will not be
 * loaded in to the physics engine, and may be at different depths to emulate
 * parallax scrolling.
 */
public class GDXBackground implements Cloneable,Serializable,Comparable<GDXBackground> {
	private static final long serialVersionUID = 1L;
	private Vector2 coordinates = new Vector2();
	private String texture = "";
	/**
	 * The distance as a ratio from the camera. 0 is on the camera, 0-1 is 
	 * foreground, 1 is the mid-ground (normal distance where physics objects
	 * are located), 1+ is the background (and subject to parallax scrolling)
	 */
	private float depth = 1;
	
	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public Vector2 getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Vector2 coordinates) {
		this.coordinates = coordinates;
	}
	
	@Override public Object clone(){
		GDXBackground background = new GDXBackground();
		background.setCoordinates(coordinates.cpy());
		background.setDepth(depth);
		background.setTexture(texture);
		return background;
	}
	@Override public String toString(){
		return "[GDXBackground texture:" + texture + " depth:" + depth + 
				" coords:" + coordinates.toString() + "]";
	}

	@Override public int compareTo(GDXBackground o) {
		return -((Float)depth).compareTo(o.depth);
	}
}
