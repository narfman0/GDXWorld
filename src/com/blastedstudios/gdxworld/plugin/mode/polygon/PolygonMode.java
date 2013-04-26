package com.blastedstudios.gdxworld.plugin.mode.polygon;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

@PluginImplementation
public class PolygonMode extends AbstractMode {
	private final Map<GDXPolygon, Body> bodies = new HashMap<>();
	private PolygonWindow polygonWindow;
	private Vector2 lastTouchedVertex;
	private GDXPolygon lastTouchedPolygon;

	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("PolygonMouseMode.touchDown", "x="+x+ " y="+y);
		GDXPolygon polygon = screen.getLevel().getClosestPolygon(coordinates.x, coordinates.y);
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || polygon == null || 
				polygon.getDistance(coordinates.x, coordinates.y) > LevelEditorScreen.getNodeRadius())
			polygon = new GDXPolygon();
		if(polygonWindow == null)
			screen.getStage().addActor(polygonWindow = new PolygonWindow(screen.getSkin(), this, polygon));

		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
			lastTouchedPolygon = polygon;
			lastTouchedVertex = polygon.getClosestVertex(coordinates.x, coordinates.y);
		}else if(polygon.getVertices().isEmpty())
			polygonWindow.add(new Vector2(coordinates.x, coordinates.y));
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		shift();
		return false;
	}
	
	private void shift(){
		if(lastTouchedVertex != null && lastTouchedPolygon != null){
			lastTouchedVertex.set(coordinates);
			if(polygonWindow != null){
				polygonWindow.remove(lastTouchedVertex);
				polygonWindow.add(lastTouchedVertex);
			}
			removePolygon(lastTouchedPolygon);
			addPolygon(lastTouchedPolygon);
			lastTouchedVertex = null;
			lastTouchedPolygon = null;
		}
	}

	public void addPolygon(GDXPolygon polygon){
		Gdx.app.log("PolygonMode.addPolygon", polygon.toString());
		if(bodies.containsKey(polygon))
			screen.getWorld().destroyBody(bodies.remove(polygon));
		Body body = polygon.createFixture(screen.getWorld(), !screen.isLive());
		if(body != null){
			if(!screen.getLevel().getShapes().contains(polygon))
				screen.getLevel().getShapes().add(polygon);
			bodies.put(polygon, body);
		}
	}

	public void removePolygon(GDXPolygon polygon) {
		Gdx.app.log("PolygonMode.removePolygon", polygon.toString());
		screen.getWorld().destroyBody(bodies.remove(polygon));
		screen.getLevel().getShapes().remove(polygon);
	}

	@Override public boolean contains(float x, float y) {
		return polygonWindow != null && polygonWindow.contains(x, y);
	}

	@Override public void clean() {
		if(polygonWindow != null)
			polygonWindow.remove();
		polygonWindow = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		bodies.clear();
		for(GDXShape shape : level.getShapes())
			if(shape instanceof GDXPolygon)
				addPolygon((GDXPolygon)shape);
	}
	
	@Override public void render(float delta, Camera camera, ShapeRenderer renderer){
		renderer.setColor(Color.CYAN);
		if(!screen.isLive())
			for(GDXShape shape : screen.getLevel().getShapes())
				if(shape instanceof GDXPolygon)
					for(Vector2 vertex : ((GDXPolygon) shape).getVerticesAbsolute())
						renderer.circle(vertex.x, vertex.y, LevelEditorScreen.getNodeRadius(), 10);
	};
}
