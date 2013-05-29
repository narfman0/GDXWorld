package com.blastedstudios.gdxworld.ui;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Simple input handler that moves an orthographic camera in the X,Y axis when
 * the specified mouse button is dragged.
 */
public class MouseCameraScroller extends InputAdapter {
    private OrthographicCamera camera;
    private int button;

    private boolean buttonHeld;

    private int x0;
    private int y0;
    private float cx0;
    private float cy0;

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
	x0 = -1;
	y0 = -1;
    }

    public boolean touchDown(int x, int y, int ptr, int buttonDown) {
	if(buttonDown == button) {
	    buttonHeld = true;
	    x0 = x;
	    y0 = y;
	    cx0 = camera.position.x;
	    cy0 = camera.position.y;
	}
	return buttonHeld;
    }

    public boolean touchUp(int x, int y, int ptr, int buttonDown) {
	if(buttonDown == button) {
	    buttonHeld = false;
	    x0 = -1;
	    y0 = -1;
	}
	return buttonHeld;
    }

    public boolean touchDragged(int x, int y, int ptr) {
	if(buttonHeld) {
	    if(x0 != -1 && y0 != -1) {
		System.err.println("X: " + x + "  Y: " + y);
		int dx = (x - x0);
		int dy = (y - y0);
		camera.position.y = cy0 + dy;
		camera.position.x = cx0 - dx;
	    }
	}
	return buttonHeld;
    }

    public boolean isScrolling() {
	return buttonHeld;
    }
}
