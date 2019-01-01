package com.blastedstudios.gdxworld.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

public class GDXRendererTest {
    private Camera camera;

    @Before
    public void setUp() {
        camera = new CameraStub();
    }

    @Test
    public void testFromParallax() {
        camera.translate(1, 1, 0);
        float scale = 2f;
        Vector2 world = new Vector2(1, 1);
        Vector2 to = GDXRenderer.toParallax(scale, world, camera);
        assertEquals(to.x, 1.5, .01);
        assertEquals(to.y, 1.5, .01);
        Vector2 from = GDXRenderer.fromParallax(scale, to, camera);
        assertEquals(world.x, from.x, .01);
        assertEquals(world.y, from.y, .01);
    }
}
