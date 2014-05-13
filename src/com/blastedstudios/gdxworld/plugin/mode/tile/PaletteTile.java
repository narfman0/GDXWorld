package com.blastedstudios.gdxworld.plugin.mode.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class PaletteTile extends ImageButton {
	private int tilesize;
	private Sprite sprite;
	
	public PaletteTile(final Sprite sprite, final int tilesize) {
		super(new SpriteDrawable(sprite));
		this.sprite = sprite;
		this.tilesize = tilesize;
	}
	
	public void setSprite(final Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
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
	public void draw(final SpriteBatch batch, final float parentAlpha) {
		this.setPosition(getX(), getY());
		super.draw(batch, parentAlpha);
	}
}
