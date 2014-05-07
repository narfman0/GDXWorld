package com.blastedstudios.gdxworld.plugin.mode.tile;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Tile extends Actor {
	private TextureRegion texture;
	
	public Tile(TextureRegion texture) {
		this.texture = texture;
	}
}
