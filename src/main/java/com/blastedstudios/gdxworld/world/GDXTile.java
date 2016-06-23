package com.blastedstudios.gdxworld.world;

import com.badlogic.gdx.math.Vector2;

public class GDXTile {
	private Vector2 position;
	private String resource;
	private int x, y, tilesize;
	
	public GDXTile(final float x, final float y, final String resource, final int resourcePositionX, final int resourcePositionY, final int tilesize) {
		this(new Vector2(x, y), resource, resourcePositionX, resourcePositionY, tilesize);
	}
	
	public GDXTile(final Vector2 position, final String resource, final int resourcePositionX, final int resourcePositionY, final int tilesize) {
		this.position = position;
		this.resource = resource;
		this.x = resourcePositionX;
		this.y = resourcePositionY;
		this.tilesize = tilesize;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	/** get resource position x */
	public int getX() {
		return x;
	}
	
	/** set resource position x */
	public void setX(int x) {
		this.x = x;
	}
	
	/** get resource position y */
	public int getY() {
		return y;
	}

	/** set resource position y */
	public void setY(int y) {
		this.y = y;
	}
	
	public int getTilesize() {
		return tilesize;
	}

	public void setTilesize(int tilesize) {
		this.tilesize = tilesize;
	}
	
	@Override
	public GDXTile clone() {
		return new GDXTile(new Vector2(position), new String(resource), x, y, tilesize);
	}
}
