package com.blastedstudios.gdxworld.plugin.mode.circle;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

@PluginImplementation
public class CircleMode extends AbstractMode {
	private final Map<GDXCircle, Body> bodies = new HashMap<>();
	private CircleWindow circleWindow;
	private GDXCircle lastTouched;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("CircleMode.touchDown", "x="+x+ " y="+y);
		GDXCircle circle = screen.getLevel().getClosestCircle(coordinates.x, coordinates.y);
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || circle == null || 
				circle.getDistance(coordinates.x, coordinates.y) > LevelEditorScreen.getNodeRadius())
			circle = new GDXCircle();
		if(circleWindow == null)
			screen.getStage().addActor(circleWindow = new CircleWindow(screen.getSkin(), this, circle));
		circleWindow.setCenter(new Vector2(coordinates.x, coordinates.y));
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			lastTouched = circle;
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		shift();
		return false;
	}
	
	public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		shift();
		lastTouched = null;
		return false;
	}
	
	private void shift(){
		if(lastTouched != null){
			Gdx.app.debug("CircleMode.shift", lastTouched.toString() + " to " + coordinates);
			lastTouched.getCenter().set(coordinates);
			bodies.get(lastTouched).setTransform(coordinates, 0);
			if(circleWindow != null)
				circleWindow.setCenter(new Vector2(coordinates.x, coordinates.y));
		}
	}

	public void addCircle(GDXCircle circle) {
		Gdx.app.log("CircleMode.addCircle", circle.toString());
		if(bodies.containsKey(circle))
			screen.getWorld().destroyBody(bodies.remove(circle));
		Body body = circle.createFixture(screen.getWorld(), !screen.isLive());
		if(body != null){
			bodies.put(circle, body);
			if(!screen.getLevel().getShapes().contains(circle))
				screen.getLevel().getShapes().add(circle);
		}
	}

	public void removeCircle(GDXCircle circle) {
		screen.getLevel().getShapes().remove(circle);
		if(bodies.containsKey(circle))
			screen.getWorld().destroyBody(bodies.remove(circle));
	}

	@Override public boolean contains(float x, float y) {
		return circleWindow != null && circleWindow.contains(x, y);
	}

	@Override public void clean() {
		if(circleWindow != null)
			circleWindow.remove();
		circleWindow = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		bodies.clear();
		for(GDXShape shape : level.getShapes())
			if(shape instanceof GDXCircle)
				addCircle((GDXCircle)shape);
	}
}
