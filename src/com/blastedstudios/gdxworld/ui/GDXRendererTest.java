package com.blastedstudios.gdxworld.ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GDXRendererTest {
	private Camera camera;
	
	@Before public void setUp(){
		camera = new CameraStub();
	}
	
	/**
	 * Test to ensure the normal (1x) scale works, not performing any parallax
	 */
	@Test public void testMidground() {
		iterate(1f, new Vector2(0,0), new Vector3(1, 0, 0));
	}
	
	@Test public void testMidgroundOffset() {
		iterate(1f, new Vector2(1,0), new Vector3(1, 0, 0));
	}

	
	/**
	 * Test to ensure scale 2 works
	 */
	@Test public void testBackground() {
		iterate(2f, new Vector2(0,0), new Vector3(1, 0, 0));
	}

	@Test public void testBackgroundOffset() {
		iterate(2f, new Vector2(1,0), new Vector3(1, 0, 0));
	}
	
	private void iterate(float scale, Vector2 world, Vector3 translate){
		Vector2 xy = GDXRenderer.toParallax(scale, world, camera);
		assertTrue(xy.x - camera.position.x == world.x / scale);
		assertTrue(xy.y - camera.position.y == world.y / scale);
		camera.translate(translate.x, translate.y, translate.z);
		Vector2 xy2 = GDXRenderer.toParallax(scale, world, camera);
		assertTrue(xy2.x + translate.x/scale == world.x / scale);
		assertTrue(xy2.y + translate.y/scale == world.y / scale);
	}
	
	private class CameraStub extends Camera{
		@Override public void update() {}
		@Override public void update(boolean arg0) {}
	}
}
