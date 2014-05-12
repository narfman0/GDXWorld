package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.TiledMeshRenderer;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

@PluginImplementation
public class TileMode extends AbstractMode {
	private final SpriteBatch spriteBatch = new SpriteBatch();
	private final Map<GDXPolygon, Body> bodies = new HashMap<>();
	private PaletteWindow paletteWindow;
	private Set<PaletteTile> tilePalette = new HashSet<>();
	private TileWindow tileWindow;
	private TiledMeshRenderer tiledMeshRenderer;
	private PaletteTile activeTile;
	
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
		tiledMeshRenderer = null;
	}
	
	@Override
	public boolean touchDown(final int x, final int y, final int x1, final int y1) {
		super.touchDown(x, y, x1, y1);
		Gdx.app.log("TileMode.touchDown", "x="+x+ " y="+y);
		GDXPolygon tilePolygon = new GDXPolygon();
		List<Vector2> vertices = new ArrayList<Vector2>();
		vertices.add(new Vector2(x, y));
		vertices.add(new Vector2(x + paletteWindow.getTilesize(), y));
		vertices.add(new Vector2(x, y + paletteWindow.getTilesize()));
		vertices.add(new Vector2(x + paletteWindow.getTilesize(), y + paletteWindow.getTilesize()));
		tilePolygon.setVertices(vertices);
		
//		if(tileWindow == null) {
//			screen.getStage().addActor(tileWindow = new TileWindow(screen.getSkin(), this, tilePolygon));
//		}
		return false;
	}
	
	@Override
	public void render(final float delta, final OrthographicCamera camera, final GDXRenderer gdxRenderer, final ShapeRenderer renderer) {
		if(tiledMeshRenderer == null)
			tiledMeshRenderer = new TiledMeshRenderer(gdxRenderer, screen.getLevel().getPolygons());
		tiledMeshRenderer.render(camera);
		if(!screen.isLive()){
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeType.Line);
			
			//Draw set polygons
			renderer.setColor(Color.GREEN);
			for(GDXPolygon shape : screen.getLevel().getPolygons())
				for(Vector2 vertex : shape.getVerticesAbsolute())
					renderer.circle(vertex.x, vertex.y, LevelEditorScreen.getNodeRadius(), 10);
			//Draw currently selected polygon/nodes
			renderer.setColor(new Color(0, .8f, 0, 1));
			renderer.end();
		} else {
			spriteBatch.setProjectionMatrix(camera.combined);
			spriteBatch.begin();
			for(Entry<GDXPolygon, Body> entry : bodies.entrySet())
				gdxRenderer.drawShape(camera, entry.getKey(), entry.getValue(), spriteBatch);
			spriteBatch.end();
		}
	};
	
	@Override public int getLoadPriority() {
		return 10;
	}
}
