package com.blastedstudios.gdxworld.plugin.mode.tile;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

public class TileWindow extends AbstractWindow {
	private Tile tile;
	
	public TileWindow(Skin skin, TileMode tileMode, GDXPolygon tilePolygon) {
		super("Tile Editor", skin);
		this.tile = tile;
	}
}
