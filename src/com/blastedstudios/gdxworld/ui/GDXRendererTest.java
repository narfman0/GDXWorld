package com.blastedstudios.gdxworld.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

public class GDXRendererTest {
	private Camera camera;
	
	@Before public void setUp(){
		camera = new CameraStub();
	}
	
	/**
	 * Test to ensure the normal (1x) scale works, not performing any parallax
	 */
	@Test public void testMidground() {
		iterate(1f, new Vector2(0,0), new Vector2(0, 0));
		camera.translate(1, 0, 0);
		iterate(1f, new Vector2(0,0), new Vector2(-1, 0));
	}
	
	@Test public void testMidgroundOffset() {
		iterate(1f, new Vector2(1,0), new Vector2(1, 0));
		camera.translate(1, 0, 0);
		iterate(1f, new Vector2(1,0), new Vector2(0, 0));
	}

	
	/**
	 * Test to ensure scale 2 works
	 */
	@Test public void testBackground() {
		iterate(2f, new Vector2(0, 0), new Vector2(0, 0));
		camera.translate(1, 0, 0);
		iterate(2f, new Vector2(0, 0), new Vector2(-.5f, 0));
	}

	@Test public void testBackgroundOffset() {
		iterate(2f, new Vector2(1,0), new Vector2(1, 0));
		camera.translate(1, 0, 0);
		iterate(2f, new Vector2(1,0), new Vector2(.5f, 0));
	}
	
	private void iterate(float scale, Vector2 world, Vector2 expected){
		Vector2 xy = GDXRenderer.toParallax(scale, world, camera);
		assertEquals(expected.x, xy.x, .001f);
		assertEquals(expected.y, xy.y, .001f);
	}
	
	private class CameraStub extends Camera{
		@Override public void update() {}
		@Override public void update(boolean arg0) {}
	}
}
