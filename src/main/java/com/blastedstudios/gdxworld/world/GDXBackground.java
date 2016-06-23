package com.blastedstudios.gdxworld.world;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.Properties;

/**
 * Represents an item that is rendered in the background. This will not be
 * loaded in to the physics engine, and may be at different depths to emulate
 * parallax scrolling.
 */
public class GDXBackground implements Cloneable,Serializable,Comparable<GDXBackground> {
	private static final long serialVersionUID = 1L;
	private Vector2 coordinates = new Vector2();
	private String texture = "";
	private boolean scissor = false;
	private Vector2 scissorUpperRight = new Vector2(), scissorLowerLeft = new Vector2();
	/**
	 * The distance as a ratio from the camera. 0 is on the camera, 0-1 is 
	 * foreground, 1 is the mid-ground (normal distance where physics objects
	 * are located), 1+ is the background (and subject to parallax scrolling)
	 */
	private float depth = Properties.getFloat("background.depth.default", 1), 
			scale = Properties.getFloat("background.scale.default", 1);
	
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

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	@Override public Object clone(){
		GDXBackground background = new GDXBackground();
		background.setCoordinates(coordinates.cpy());
		background.setDepth(depth);
		background.setTexture(texture);
		background.setScale(scale);
		background.setScissor(scissor);
		background.setScissorLowerLeft(scissorLowerLeft.cpy());
		background.setScissorUpperRight(scissorUpperRight.cpy());
		return background;
	}
	@Override public String toString(){
		return "[GDXBackground texture:" + texture + " depth:" + depth + 
				" coords:" + coordinates.toString() + " scale:" + scale + "]";
	}

	@Override public int compareTo(GDXBackground o) {
		return -((Float)depth).compareTo(o.depth);
	}

	public boolean isScissor() {
		return scissor;
	}

	public void setScissor(boolean scissor) {
		this.scissor = scissor;
	}

	public Vector2 getScissorUpperRight() {
		if(scissorUpperRight == null)
			return scissorUpperRight = new Vector2();
		return scissorUpperRight;
	}

	public void setScissorUpperRight(Vector2 scissorUpperRight) {
		this.scissorUpperRight = scissorUpperRight;
	}

	public Vector2 getScissorLowerLeft() {
		if(scissorLowerLeft == null)
			return scissorLowerLeft = new Vector2();
		return scissorLowerLeft;
	}

	public void setScissorLowerLeft(Vector2 scissorLowerLeft) {
		this.scissorLowerLeft = scissorLowerLeft;
	}
}
