package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.util.Iterator;
import java.util.Map.Entry;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXTile;

@PluginImplementation
public class TileMode extends AbstractMode {
	private static final int DEFAULT_TILESIZE = 21;
	private static int tilesize = DEFAULT_TILESIZE;
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private PaletteWindow paletteWindow;
	private TileWindow tileWindow;
	private PaletteTile activeTile;
	private boolean renderGrid = Properties.getBool("tilemode.renderGrid", false);
	private boolean renderOrigin = Properties.getBool("tilemode.renderOrigin", false);
	private ShapeRenderer sr = new ShapeRenderer();
	
	public void start() {
		screen.getStage().addActor(paletteWindow = new PaletteWindow(screen.getSkin(), this));
	}
	
	public void setActiveTile(final PaletteTile tile) {
		activeTile = tile;
	}
	
	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		
		Iterator<Entry<Vector2, GDXTile>> it = level.getTiles().entrySet().iterator();
		if(it.hasNext()) {
			Entry<Vector2, GDXTile> first = it.next();
			tilesize = first.getValue().getTilesize();
			screen.getLevel().getTiles().put(first.getKey(), first.getValue());
			 while(it.hasNext()) {
				Entry<Vector2, GDXTile> entry = it.next();
				screen.getLevel().getTiles().put(entry.getKey(), entry.getValue());
			}
		}
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
	
	@Override public boolean touchDown(final int x, final int y, final int x1, final int y1) {
		super.touchDown(x, y, x1, y1);
		paint();
		return false;
	}

	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		paint();
		return false;
	}
	
	private void paint(){
		Gdx.app.log("TileMode.paint", "x=" + coordinates.x + " y=" + coordinates.y);
		if(activeTile != null) {
			GDXTile tile = new GDXTile(getOffset(coordinates.x), getOffset(coordinates.y), activeTile.getResource(), activeTile.getResX(), activeTile.getResY(), activeTile.getTileSize());
			screen.getLevel().getTiles().put(new Vector2(getOffset(coordinates.x), getOffset(coordinates.y)), tile);
		}
	}
	
	@Override
	public void render(final float delta, final OrthographicCamera camera, final GDXRenderer gdxRenderer, final ShapeRenderer renderer) {
		spriteBatch.setProjectionMatrix(camera.combined);
		
		// render origin lines
		if(renderOrigin && paletteWindow != null) {
			Vector3 start = new Vector3(0, 0, 0);
			Vector3 end = new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0f);
			camera.unproject(start);
			camera.unproject(end);
			sr.begin(ShapeType.Line);
			sr.setColor(Color.WHITE);
			sr.setProjectionMatrix(camera.combined);
			sr.line(getOffset(start.x), 0, getOffset(end.x) + tilesize, 0);
			sr.line(0, getOffset(start.y) + tilesize, 0, getOffset(end.y));
			sr.end();
		}

		// render grid
		if(renderGrid && paletteWindow != null) {
			sr.begin(ShapeType.Point);
			sr.setColor(Color.WHITE);
			sr.setProjectionMatrix(camera.combined);
			Vector3 start = new Vector3(0, 0, 0);
			Vector3 end = new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0f);
			camera.unproject(start);
			camera.unproject(end);
			for(int x = (int) getOffset(start.x); x < getOffset(end.x) + tilesize; x += tilesize) {
				for(int y = (int) getOffset(start.y); y > getOffset(end.y); y -= tilesize) {
					sr.point(x, y, 0);
				}
			}
			sr.end();
		}
		
		// render cursor
		if(paletteWindow != null && !contains(Gdx.input.getX(),Gdx.input.getX())){
			renderer.begin(ShapeType.Filled);
			renderer.setColor(0.0f, 0.35f, 0.6f, 0.0f);
			Vector3 coordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(coordinates);
			renderer.rect(getOffset(coordinates.x), getOffset(coordinates.y), tilesize, tilesize);
			renderer.end();
		}
		
		// render tile sprites
		spriteBatch.begin();
		for(GDXTile tile : screen.getLevel().getTiles().values())
			gdxRenderer.drawTile(camera, tile, spriteBatch);
		spriteBatch.end();

		
	};
	
	public void setTileSize(int newTilesize) {
		tilesize = newTilesize;
	}
	
	/** Aligns position to nearest tile position */
	private static float getOffset(float position) {
		return position - (position > 0 ? position % tilesize : tilesize + (position % tilesize));
	}
	
	@Override
	public int getLoadPriority() {
		return 10;
	}
	
	@Override
	public boolean contains(float x, float y){
		return super.contains(x, y) || (paletteWindow != null && paletteWindow.contains(x, y));
	}
}
