package com.blastedstudios.gdxworld.plugin.mode.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class GDXTile {
	private Vector2 position;
	private Sprite sprite;
	
	public GDXTile(final float x, final float y, final Sprite sprite) {
		this(new Vector2(x, y), sprite);
	}
	
	public GDXTile(final Vector2 center, final Sprite sprite) {
		this.position = center;
		this.sprite = sprite;
	}
	
	public Sprite updateAndGetSprite() {
		sprite.setPosition(position.x, position.y);
		return sprite;
	}
}
