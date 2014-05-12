package com.blastedstudios.gdxworld.plugin.mode.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class PaletteTile extends ImageButton {
	private int tilesize;

	public PaletteTile(final TextureRegion texture, final int tilesize) {
		super(new SpriteDrawable(new Sprite(texture)));
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
	public void draw(final SpriteBatch batch, final float parentAlpha) {
		this.setPosition(getX(), getY());
		super.draw(batch, parentAlpha);
	}
}
