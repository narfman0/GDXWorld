package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.util.ArrayList;
import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXTile;

@PluginImplementation
public class TileMode extends AbstractMode {
	private static final int DEFAULT_TILESIZE = 21;
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private PaletteWindow paletteWindow;
	private List<GDXTile> worldTiles = new ArrayList<>();
	private TileWindow tileWindow;
	private PaletteTile activeTile;
	private boolean drawGrid = true;
	private static int tilesize = DEFAULT_TILESIZE;
	private ShapeRenderer sr = new ShapeRenderer();
	
	public void start() {
		screen.getStage().addActor(paletteWindow = new PaletteWindow(screen.getSkin(), this));
	}
	
	public void setActiveTile(final PaletteTile tile) {
		activeTile = tile;
	}
	
	@Override
	public void clean() {
		if(tileWindow != null)
			tileWindow.remove();
		tileWindow = null;
		if(paletteWindow != null)
			paletteWindow.remove();
		paletteWindow = null;
	}
	
	@Override
	public boolean touchDown(final int x, final int y, final int x1, final int y1) {
		super.touchDown(x, y, x1, y1);
		Gdx.app.log("TileMode.touchDown", "x="+coordinates.x+ " y="+coordinates.y);
		if(activeTile != null) {
			GDXTile tile = new GDXTile(getOffset(coordinates.x), getOffset(coordinates.y), activeTile.getSprite());
			worldTiles.add(tile);
			screen.getLevel().addTile(tile);
		}
		return false;
	}
	
	@Override
	public void render(final float delta, final OrthographicCamera camera, final GDXRenderer gdxRenderer, final ShapeRenderer renderer) {
		spriteBatch.setProjectionMatrix(camera.combined);
		
		// render cursor
		if(paletteWindow != null && !contains(Gdx.input.getX(),Gdx.input.getX())){
			renderer.begin(ShapeType.Filled);
			renderer.setColor(0.0f, 0.35f, 0.6f, 0.0f);
			Vector3 coordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(coordinates);
			renderer.rect(getOffset(coordinates.x), getOffset(coordinates.y), tilesize, tilesize);
			renderer.end();
		}
				
		// render tiles
		spriteBatch.begin();
		for(GDXTile tile : worldTiles)
			gdxRenderer.drawTile(camera, tile, spriteBatch);
		spriteBatch.end();

		// render grid
//		if(drawGrid && paletteWindow != null) {
//			sr.begin(ShapeType.Point);
//			sr.setColor(Color.WHITE);
//			sr.setProjectionMatrix(camera.combined);
//			for(int x = -1000 * tilesize; x < 1000 * tilesize; x += tilesize)
//				for(int y = -1000 * tilesize; y < 1000 * tilesize; y += tilesize)
//					sr.point(x, y, 0);
//			sr.end();
//		}
	};
	
	/** Translates given position to nearest valid tile position */
	private static float getOffset(float position) {
		return position - position > 0 ? (position % tilesize) : tilesize + (position % tilesize);
	}
	
	@Override public int getLoadPriority() {
		return 10;
	}
	
	@Override public boolean contains(float x, float y){
		return super.contains(x, y) || (paletteWindow != null && paletteWindow.contains(x, y));
	}
}
