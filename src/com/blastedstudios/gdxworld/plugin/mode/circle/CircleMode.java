package com.blastedstudios.gdxworld.plugin.mode.circle;

import java.util.Arrays;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

@PluginImplementation
public class CircleMode extends AbstractMode {
	private CircleWindow circleWindow;
	private GDXCircle lastTouched;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("CircleMouseMode.touchDown", "x="+x+ " y="+y);
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
	
	public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		shift();
		return false;
	}
	
	private void shift(){
		if(lastTouched != null){
			Gdx.app.debug("CircleMode.shift", lastTouched.toString() + " to " + coordinates);
			lastTouched.getCenter().set(coordinates);
			removeCircle(lastTouched);
			addCircle(lastTouched);
			if(circleWindow != null)
				circleWindow.setCenter(new Vector2(coordinates.x, coordinates.y));
		}
	}

	public void addCircle(GDXCircle circle) {
		Gdx.app.log("CircleMouseMode.addCircle", circle.toString());
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

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXShape shape : level.getShapes())
			if(shape instanceof GDXCircle)
				addCircle((GDXCircle)shape);
	}
}
