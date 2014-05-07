package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.TiledMeshRenderer;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

@PluginImplementation
public class TileMode extends AbstractMode {
	private PaletteWindow palette;
	private Set<Tile> tiles = new HashSet<Tile>();
	private TileWindow tileWindow;
	private TiledMeshRenderer tiledMeshRenderer;
	private float tileSize = 10;
	
	public void start() {
		screen.getStage().addActor(palette = new PaletteWindow(screen.getSkin(), this));
	}
	
	@Override public void clean() {
		if(tileWindow != null)
			tileWindow.remove();
		tileWindow = null;
		tiledMeshRenderer = null;
	}
	
	@Override
	public int getLoadPriority() {
		return 10;
	}
	
	@Override
	public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x, y, x1, y1);
		Gdx.app.log("TileMouseMode.touchDown", "x="+x+ " y="+y);
		GDXPolygon tilePolygon = new GDXPolygon();
		List<Vector2> vertices = new ArrayList<Vector2>();
		vertices.add(new Vector2(x, y));
		vertices.add(new Vector2(x + tileSize, y));
		vertices.add(new Vector2(x, y + tileSize));
		vertices.add(new Vector2(x + tileSize, y + tileSize));
		tilePolygon.setVertices(vertices);
//		if(tileWindow == null) {
//			screen.getStage().addActor(tileWindow = new TileWindow(screen.getSkin(), this, tilePolygon));
//		}
		return false;
	}
}
