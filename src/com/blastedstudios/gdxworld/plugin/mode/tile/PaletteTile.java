package com.blastedstudios.gdxworld.plugin.mode.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PaletteTile extends Table {
	
	private Sprite sprite;
	
	/** in pixels */
	private int tilesize;

	public PaletteTile(TextureRegion texture, int tilesize) {
		this.sprite = new Sprite(texture);
		this.tilesize = tilesize;
	}
	
	@Override
	public float getPrefWidth() {
		return tilesize;
	};
	
	@Override
	public float getPrefHeight() {
		return tilesize;
	};
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		sprite.setPosition(getX(), getY());
		sprite.draw(batch);
	}
}
