package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.TiledMeshRenderer;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

@PluginImplementation
public class TileMode extends AbstractMode {
	private PaletteWindow palette;
	private Set<TextureRegion> tilePalette = new HashSet<>();
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
	
	/**
	 * Loads a png file containing a spritesheet and creates a set of tiles that can
	 * be added to the world
	 */
	public boolean loadPalette(final String paletteFilePath, final int margin, final int spacing, final int tilesize) {
		Gdx.app.log("TileMode.loadPalette", "Loading palette file " + paletteFilePath);
		Texture texture = new Texture(Gdx.files.absolute(paletteFilePath));
		tilePalette.addAll(split(texture, margin, spacing, tilesize));
		return false;
	}
	
	private List<TextureRegion> split(Texture texture, int margin, int spacing, int tilesize) {
		List<TextureRegion> tiles = new ArrayList<>();
		int stopWidth = texture.getWidth() - tilesize;
		int stopHeight = texture.getHeight() - tilesize;
		
		for(int y = margin; y <= stopHeight; y += tilesize + spacing) {
			for(int x = margin; x <= stopWidth; x += tilesize + spacing) {
				tiles.add(new TextureRegion(texture, x, y, tilesize, tilesize));
			}
		}
		return tiles;
	}
	
	@Override
	public int getLoadPriority() {
		return 10;
	}
	
	@Override
	public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x, y, x1, y1);
		Gdx.app.log("TileMode.touchDown", "x="+x+ " y="+y);
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
