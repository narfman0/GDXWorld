package com.blastedstudios.gdxworld.plugin.mode.polygon;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.math.PolygonUtils;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.TiledMeshRenderer;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

@PluginImplementation
public class PolygonMode extends AbstractMode {
	private final SpriteBatch spriteBatch;
	private final Map<GDXPolygon, Body> bodies = new HashMap<>();
	private TiledMeshRenderer tiledMeshRenderer;
	private PolygonWindow polygonWindow;
	private Vector2 lastTouchedVertex;
	private GDXPolygon lastTouchedPolygon;
	
	public PolygonMode(){
		spriteBatch = new SpriteBatch();
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("PolygonMouseMode.touchDown", "x="+x+ " y="+y);
		GDXPolygon polygon = screen.getLevel().getClosestPolygon(coordinates.x, coordinates.y);
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || polygon == null || 
				polygon.getDistance(coordinates.x, coordinates.y) > LevelEditorScreen.getNodeRadius())
			polygon = new GDXPolygon();
		//If window isn't up, we can either move a poly (holding shift) or make
		//a new poly. If window is up, either move a vertex (holding shift) or
		//make a new vertex.
		if(polygonWindow == null){
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				lastTouchedPolygon = polygon;
			else{
				if(polygon.getVertices().isEmpty())
					polygon.getVertices().add(new Vector2(coordinates.x, coordinates.y));
				screen.getStage().addActor(polygonWindow = new PolygonWindow(screen.getSkin(), this, polygon));
			}
		}else if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && PolygonUtils.getClosestNode(coordinates.x, 
				coordinates.y, polygonWindow.getVertices()).dst(coordinates) < LevelEditorScreen.getNodeRadius()){
			lastTouchedVertex = PolygonUtils.getClosestNode(coordinates.x, coordinates.y, polygonWindow.getVertices());
		}else if((Gdx.input.isKeyPressed(Keys.DEL) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) && PolygonUtils.getClosestNode(coordinates.x, 
				coordinates.y, polygonWindow.getVertices()).dst(coordinates) < LevelEditorScreen.getNodeRadius()){
			lastTouchedVertex = PolygonUtils.getClosestNode(coordinates.x, coordinates.y, polygonWindow.getVertices());
			polygonWindow.remove(lastTouchedVertex);
		}else
			polygonWindow.add(new Vector2(coordinates.x, coordinates.y));
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		shift();
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		shift();
		if(lastTouchedPolygon != null)
			lastTouchedPolygon = null;
		else if(lastTouchedVertex != null)
			lastTouchedVertex = null;
		return false;
	}
	
	private void shift(){
		if(lastTouchedPolygon != null){
			lastTouchedPolygon.getCenter().set(coordinates);
			lastTouchedPolygon.setCenter(coordinates);
			if(bodies.containsKey(lastTouchedPolygon))
				bodies.get(lastTouchedPolygon).setTransform(coordinates, 0);
		}else if(lastTouchedVertex != null){
			lastTouchedVertex.set(coordinates);
			polygonWindow.repopulate();
		}
		tiledMeshRenderer = null;
	}

	public boolean addPolygon(GDXPolygon polygon){
		Gdx.app.log("PolygonMode.addPolygon", polygon.toString());
		if(bodies.containsKey(polygon))
			screen.getWorld().destroyBody(bodies.remove(polygon));
		Body body = polygon.createFixture(screen.getWorld(), !screen.isLive());
		if(body != null){
			if(!screen.getLevel().getPolygons().contains(polygon))
				screen.getLevel().getPolygons().add(polygon);
			bodies.put(polygon, body);
		}
		tiledMeshRenderer = null;
		return body != null;
	}

	public void removePolygon(GDXPolygon polygon) {
		Gdx.app.log("PolygonMode.removePolygon", polygon.toString());
		screen.getWorld().destroyBody(bodies.remove(polygon));
		screen.getLevel().getPolygons().remove(polygon);
		tiledMeshRenderer = null;
	}

	@Override public boolean contains(float x, float y) {
		return polygonWindow != null && polygonWindow.contains(x, y);
	}

	@Override public void clean() {
		if(polygonWindow != null)
			polygonWindow.remove();
		polygonWindow = null;
		tiledMeshRenderer = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		bodies.clear();
		for(GDXPolygon shape : level.getPolygons())
			addPolygon(shape);
	}
	
	@Override public void render(float delta, OrthographicCamera camera, GDXRenderer gdxRenderer, ShapeRenderer renderer){
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
			if(polygonWindow != null)
				for(int i=0; i<polygonWindow.getVertices().size(); i++){
					Vector2 vertex = polygonWindow.getVertices().get(i);
					renderer.circle(vertex.x, vertex.y, LevelEditorScreen.getNodeRadius());
					if(i>0){
						Vector2 previous = polygonWindow.getVertices().get(i-1);
						renderer.line(vertex.x, vertex.y, previous.x, previous.y);
					}else{
						Vector2 last = polygonWindow.getVertices().get(polygonWindow.getVertices().size()-1);
						renderer.line(vertex.x, vertex.y, last.x, last.y);
					}
				}
			renderer.end();
			spriteBatch.setProjectionMatrix(camera.combined);
			spriteBatch.begin();
			for(Entry<GDXPolygon,Body> entry : bodies.entrySet())
				gdxRenderer.drawShape(camera, entry.getKey(), entry.getValue(), spriteBatch, .5f);
			spriteBatch.end();
		}else{
			spriteBatch.setProjectionMatrix(camera.combined);
			spriteBatch.begin();
			for(Entry<GDXPolygon,Body> entry : bodies.entrySet())
				gdxRenderer.drawShape(camera, entry.getKey(), entry.getValue(), spriteBatch);
			spriteBatch.end();
		}
	};

	@Override public int getLoadPriority() {
		return 10;
	}
}
