package com.blastedstudios.gdxworld.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class PolygonUtilsTest {
	private static final float SQUARE_X = 1, SQUARE_Y = 1;
	private static final Vector2[] SQUARE_VERTS = new Vector2[]{
		new Vector2(-SQUARE_X,-SQUARE_Y), new Vector2(-SQUARE_X,SQUARE_Y), 
		new Vector2(SQUARE_X,SQUARE_Y), new Vector2(SQUARE_X,-SQUARE_Y)};
	
	@Test public void testAABB() {
		assertEquals(2,PolygonUtils.getAABB(new Vector2[]{new Vector2(0,0),new Vector2(1,0)}).length);
		assertEquals(2,PolygonUtils.getAABB(SQUARE_VERTS).length);
		assertEquals(-SQUARE_X,PolygonUtils.getAABB(SQUARE_VERTS)[0].x, .001f);
		assertEquals(SQUARE_X,PolygonUtils.getAABB(SQUARE_VERTS)[1].x, .001f);
		assertEquals(SQUARE_Y,PolygonUtils.getAABB(SQUARE_VERTS)[0].y, .001f);
		assertEquals(-SQUARE_Y,PolygonUtils.getAABB(SQUARE_VERTS)[1].y, .001f);
	}

	@Test public void testDimensions() {
		assertEquals(SQUARE_X*2,PolygonUtils.getDimensions(SQUARE_VERTS).x,.001f);
		assertEquals(SQUARE_Y*2,PolygonUtils.getDimensions(SQUARE_VERTS).y,.001f);
	}
}
