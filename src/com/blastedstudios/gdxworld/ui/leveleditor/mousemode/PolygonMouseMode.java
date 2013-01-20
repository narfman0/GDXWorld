package com.blastedstudios.gdxworld.ui.leveleditor.mousemode;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.PolygonWindow;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class PolygonMouseMode extends LevelMouseMode {
	private PolygonWindow polygonWindow;
	private LevelEditorScreen screen;
	
	public PolygonMouseMode(Camera camera, LevelEditorScreen screen){
		super(camera);
		this.screen = screen;
	}

	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("PolygonMouseMode.touchDown", "x="+x+ " y="+y);
		GDXPolygon polygon = screen.getLevel().getClosestPolygon(coordinates.x, coordinates.y);
		if(polygon == null || polygon.getDistance(coordinates.x, coordinates.y) > LevelEditorScreen.NODE_RADIUS)
			polygon = new GDXPolygon();
		if(polygonWindow == null)
			screen.getStage().addActor(polygonWindow = new PolygonWindow(screen.getSkin(), this, polygon));
		Vector2 vertex = new Vector2(coordinates.x, coordinates.y);
		if(polygon.getVertices().isEmpty())
			polygonWindow.add(vertex);
		return false;
	}

	public void addPolygon(GDXShape shape){
		Gdx.app.log("WorldEditorScreen.addPolygon", shape.toString());
		if(screen.getBodies().containsKey(shape))
			for(Body body : screen.getBodies().remove(shape))
				screen.getWorld().destroyBody(body);
		Body body = shape.createFixture(screen.getWorld(), !screen.isLive());
		if(body != null){
			if(!screen.getLevel().getShapes().contains(shape))
				screen.getLevel().getShapes().add(shape);
			List<Body> newBodies = new ArrayList<Body>();
			newBodies.add(body);
			if(!screen.isLive())
				if(shape instanceof GDXPolygon){
					for(Vector2 vertex : ((GDXPolygon)shape).getVertices()){
						Vector2 position = vertex.cpy().add(shape.getCenter());
						newBodies.add(PhysicsHelper.createCircle(screen.getWorld(), 
								LevelEditorScreen.NODE_RADIUS, position, BodyType.StaticBody));
					}
				}else if(shape instanceof GDXCircle)
					newBodies.add(PhysicsHelper.createCircle(screen.getWorld(), ((GDXCircle)shape).getRadius(), 
							shape.getCenter(), BodyType.StaticBody));
			screen.getBodies().put(shape, newBodies);
		}
	}

	public void removePolygon(GDXPolygon polygon) {
		Gdx.app.log("WorldEditorScreen.removePolygon", polygon.toString());
		for(Body body : screen.getBodies().remove(polygon))
			screen.getWorld().destroyBody(body);
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
}
