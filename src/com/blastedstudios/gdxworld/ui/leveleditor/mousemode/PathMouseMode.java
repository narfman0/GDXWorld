package com.blastedstudios.gdxworld.ui.leveleditor.mousemode;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.PathWindow;
import com.blastedstudios.gdxworld.world.GDXPath;

public class PathMouseMode extends AbstractMouseMode {
	private PathWindow pathWindow;
	private LevelEditorScreen screen;
	
	public PathMouseMode(LevelEditorScreen screen){
		super(screen.getCamera());
		this.screen = screen;
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("PolygonMouseMode.touchDown", "x="+x+ " y="+y);
		GDXPath path = screen.getLevel().getClosestPath(coordinates.x, coordinates.y);
		if(path == null || path.getClosestNode(coordinates.x, coordinates.y).
				dst(coordinates.x, coordinates.y) > LevelEditorScreen.NODE_RADIUS)
			path = new GDXPath();
		if(pathWindow == null)
			screen.getStage().addActor(pathWindow = new PathWindow(screen.getSkin(), this, path));
		Vector2 vertex = new Vector2(coordinates.x, coordinates.y);
		if(path.getNodes().isEmpty())
			pathWindow.add(vertex);
		return false;
	}

	public void addPath(GDXPath path) {
		Gdx.app.log("WorldEditorScreen.addPath", path.toString());
		if(screen.getBodies().containsKey(path))
			for(Body body : screen.getBodies().remove(path))
				screen.getWorld().destroyBody(body);
		Body body = path.createFixture(screen.getWorld(), new FixtureDef(), BodyType.StaticBody);
		if(body != null){
			if(!screen.getLevel().getPaths().contains(path))
				screen.getLevel().getPaths().add(path);
			List<Body> newBodies = new ArrayList<Body>();
			newBodies.add(body);
			for(Vector2 vertex : path.getNodes())
				newBodies.add(PhysicsHelper.createCircle(screen.getWorld(), 
						LevelEditorScreen.NODE_RADIUS, vertex, BodyType.StaticBody));
			screen.getBodies().put(path, newBodies);
		}
	}

	public void removePath(GDXPath path) {
		Gdx.app.log("WorldEditorScreen.removePath", path.toString());
		for(Body body : screen.getBodies().remove(path))
			screen.getWorld().destroyBody(body);
		screen.getLevel().getPaths().remove(path);
	}

	@Override public boolean contains(float x, float y) {
		return pathWindow != null && pathWindow.contains(x, y);
	}

	@Override public void clean() {
		if(pathWindow != null)
			pathWindow.remove();
		pathWindow = null;
	}
}
