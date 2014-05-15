package com.blastedstudios.gdxworld.plugin.mode.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class PaletteTile extends ImageButton {
	private String resource;
	private int resX, resY, tilesize;
	
	public PaletteTile(final Sprite sprite, final String resource, final int resX, final int resY, final int tilesize) {
		super(new SpriteDrawable(sprite));
		this.resource = resource;
		this.resX = resX;
		this.resY = resY;
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

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getResY() {
		return resY;
	}

	public void setResY(int resY) {
		this.resY = resY;
	}

	public int getResX() {
		return resX;
	}

	public void setResX(int resX) {
		this.resX = resX;
	}

	public int getTileSize() {
		return tilesize;
	}

	public void setTileSize(int tilesize) {
		this.tilesize = tilesize;
	}
}
