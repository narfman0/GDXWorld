package com.blastedstudios.gdxworld.ui.leveleditor.mousemode;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.CircleWindow;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;

public class CircleMouseMode extends LevelMouseMode {
	private CircleWindow circleWindow;
	private LevelEditorScreen screen;
	
	public CircleMouseMode(Camera camera, LevelEditorScreen screen){
		super(camera);
		this.screen = screen;
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("PolygonMouseMode.touchDown", "x="+x+ " y="+y);
		GDXCircle circle = screen.getLevel().getClosestCircle(coordinates.x, coordinates.y);
		if(circle == null || circle.getDistance(coordinates.x, coordinates.y) > LevelEditorScreen.NODE_RADIUS)
			circle = new GDXCircle();
		if(circleWindow == null)
			screen.getStage().addActor(circleWindow = new CircleWindow(screen.getSkin(), this, circle));
		circleWindow.setCenter(new Vector2(coordinates.x, coordinates.y));
		return false;
	}

	public void addCircle(GDXCircle circle) {
		Gdx.app.log("WorldEditorScreen.addCircle", circle.toString());
		if(screen.getBodies().containsKey(circle))
			for(Body body : screen.getBodies().remove(circle))
				screen.getWorld().destroyBody(body);
		Body body = circle.createFixture(screen.getWorld(), !screen.isLive());
		if(body != null){
			screen.getBodies().put(circle, Arrays.asList(body));
			if(!screen.getLevel().getShapes().contains(circle))
				screen.getLevel().getShapes().add(circle);
		}
	}

	public void removeCircle(GDXCircle circle) {
		screen.getLevel().getShapes().remove(circle);
		if(screen.getBodies().containsKey(circle))
			for(Body body : screen.getBodies().get(circle))
				screen.getWorld().destroyBody(body);
	}

	@Override public boolean contains(float x, float y) {
		return circleWindow != null && circleWindow.contains(x, y);
	}

	@Override public void clean() {
		if(circleWindow != null)
			circleWindow.remove();
		circleWindow = null;
	}
}
