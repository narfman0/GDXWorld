package com.blastedstudios.gdxworld.plugin.mode.live;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.physics.CollideCallback;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.util.Log;

@PluginImplementation
public class LiveMode extends AbstractMode {
	private Body lastTouchPolygon;
	private Vector2 lastTouchCoordinates, lastTouchPolygonLocalCoordinates;
	private LiveWindow window;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Log.debug("LiveMode.touchDown", "x="+x+ " y="+y);
		CollideCallback callback = new CollideCallback();
		screen.getWorld().QueryAABB(callback, coordinates.x-.01f, coordinates.y-.01f, coordinates.x+.01f, coordinates.y+.01f);
		lastTouchPolygon = callback.getBody();
		if(lastTouchPolygon != null){
			lastTouchCoordinates = new Vector2(coordinates.x, coordinates.y);
			lastTouchPolygonLocalCoordinates = lastTouchPolygon.getLocalPoint(lastTouchCoordinates);
			Log.log("LiveMode.touchDown", "touched world coords: " + lastTouchCoordinates +
					" touched poly local coords: " + lastTouchPolygonLocalCoordinates);
		}
		return false;
	}

	@Override public boolean touchUp(int x, int y, int arg2, int arg3) {
		Vector3 coordinates = new Vector3(x,y,0);
		camera.unproject(coordinates);
		if(lastTouchPolygon != null){
			Vector2 impulse = new Vector2(coordinates.x, coordinates.y).
					sub(lastTouchCoordinates).scl(lastTouchPolygon.getMass());
			Log.log("LiveMode.touchUp", "applying impulse: " + 
					impulse + " on body: " + lastTouchPolygon.getPosition());
			lastTouchPolygon.applyLinearImpulse(impulse, 
					lastTouchPolygonLocalCoordinates.add(lastTouchPolygon.getPosition()),true);
			lastTouchCoordinates = null;
			lastTouchPolygon = null;
			lastTouchPolygonLocalCoordinates = null;
		}
		return false;
	}

	@Override public boolean contains(float x, float y) {
		return window.contains(x, y);
	}

	@Override public void clean() {
		screen.setLive(false);
		if(window != null)
			window.remove();
		window = null;
	}

	@Override public void start() {
		super.start();
		screen.getStage().addActor(window = new LiveWindow(screen.getSkin()));
		screen.setLive(true);
	}

	@Override public int getLoadPriority() {
		return 120;
	}
}
