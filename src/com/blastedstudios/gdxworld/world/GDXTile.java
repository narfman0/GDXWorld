package com.blastedstudios.gdxworld.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class GDXTile {
	private Vector2 position;
	private Sprite sprite;
	
	public GDXTile(final float x, final float y, final Sprite sprite) {
		this(new Vector2(x, y), sprite);
	}
	
	public GDXTile(final Vector2 position, final Sprite sprite) {
		this.position = position;
		this.sprite = sprite;
	}
	
	public Sprite updateAndGetSprite() {
		sprite.setPosition(position.x, position.y);
		return sprite;
	}
	
	@Override 
	public GDXTile clone() {
		GDXTile tile = new GDXTile(new Vector2(position), new Sprite(sprite));
		return tile;
	}
}
