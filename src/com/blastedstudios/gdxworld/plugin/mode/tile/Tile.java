package com.blastedstudios.gdxworld.plugin.mode.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor {
	private Sprite sprite;
	
	public Tile(TextureRegion texture) {
		this.sprite = new Sprite(texture);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		sprite.draw(batch);
	}
}
