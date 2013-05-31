package com.blastedstudios.gdxworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Simple input handler that moves an orthographic camera in the X,Y axis when
 * the specified mouse button is dragged.
 */
public class MouseCameraScroller extends InputAdapter {
	private OrthographicCamera camera;
	private int button;

	private boolean buttonHeld;

        private Vector3 screen0 = null;

        private Vector3 cameraPosition0 = null;

	/**
	 * Sole constructor, specifying the camers to control and the button that
	 * dictates scrolling.
	 *
	 * @param camera the camera to control
	 * @param button the mouse button that causes scrolling; 0 for left, 1 for
	 * right, 2 for middle.
	 */
	public MouseCameraScroller(OrthographicCamera camera, int button) {
		if(camera == null) throw new IllegalArgumentException("Camera cannot be null!");
		if(button < 0) throw new IllegalArgumentException("Button must be >= 0");
		this.buttonHeld = false;
		this.camera = camera;
		this.button = button;
	}

        @Override
	public boolean touchDown(int x, int y, int ptr, int buttonDown) {
		if(buttonDown == button) {
			buttonHeld = true;

			screen0 = new Vector3(x, y, 0);
			
			cameraPosition0 = new Vector3(camera.position);
		}
		return buttonHeld;
	}

        @Override
	public boolean touchUp(int x, int y, int ptr, int buttonDown) {
		if(buttonDown == button) {
			buttonHeld = false;
		}
		return buttonHeld;
	}

        @Override
	public boolean touchDragged(int x, int y, int ptr) {
		if(buttonHeld) {
		    Gdx.app.debug("MouseCameraScroller.touchDragged", "X: " + x + "  Y: " + y);
		    
		    Vector3 world0 = new Vector3(screen0);
		    camera.unproject(world0);

		    Vector3 world = new Vector3(x, y, 0);
		    camera.unproject(world);

		    Vector3 delta = world.sub(world0);

		    camera.position.set(cameraPosition0.cpy().sub(delta));
		}
		return buttonHeld;
	}

        /**
         * Get the status of the camera scroller.
	 * @return true if the scrollon button his held on the mouse, false
	 * otherwise.
         */
	public boolean isScrolling() {
		return buttonHeld;
	}
}
