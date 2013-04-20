package com.blastedstudios.gdxworld.math;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class PolygonUtils {
	public static float getPolygonSignedArea(Vector2[] points) {
		if (points.length < 3)
			return 0;

		float sum = 0;
		for (int i = 0; i < points.length; i++) {
			Vector2 p1 = points[i];
			Vector2 p2 = i != points.length-1 ? points[i+1] : points[0];
			sum += (p1.x * p2.y) - (p1.y * p2.x);
		}
		return 0.5f * sum;
	}

	public static float getPolygonArea(Vector2[] points) {
		return Math.abs(getPolygonSignedArea(points));
	}

	public static boolean isPolygonCCW(Vector2[] points) {
		return getPolygonSignedArea(points) > 0;
	}
	
	/**
	 * @return the average coordinates of the given @param vertices
	 */
	public static Vector2 getCenter(List<Vector2> vertices){
		Vector2 center = new Vector2();
		for(Vector2 vertex : vertices)
			center.add(vertex);
		center.div(vertices.size());
		return center;
	}
	
	/**
	 * @return list of skewed vertices to be centered around given origin
	 */
	public static List<Vector2> getCenterVertices(List<Vector2> vertices, Vector2 vertex){
		List<Vector2> newVertices = new ArrayList<Vector2>();
		for(Vector2 vertexNode : vertices)
			newVertices.add(vertexNode.cpy().sub(vertex));
		return newVertices;
	}
	
	/**
	 * @return list of skewed vertices to be centered around given origin
	 */
	public static List<Vector2> getCenterVerticesReverse(List<Vector2> vertices, Vector2 vertex){
		return getCenterVertices(vertices, vertex.cpy().scl(-1));
	}
	
	public static Vector2 getClosestNode(float x, float y, List<Vector2> nodes) {
		Vector2 closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(Vector2 vertex : nodes){
			float distance = vertex.dst2(x, y);
			if(closest == null || closestDistance > distance){
				closest = vertex;
				closestDistance = distance;
			}
		}
		return closest;
	}
	
	public static boolean aabbCollide(float x, float y, float centerX,
			float centerY, float width, float height){
		return centerX - width/2 < x &&	centerX + width/2 > x &&
				centerY - height/2 < y && centerY + height/2 > y;
	}
	
	/**
	 * @return array representing {upper left,lower right} aabb verts
	 */
	public static Vector2[] getAABB(Vector2[] verts){
		Vector2[] aabb = {new Vector2(Float.MAX_VALUE, Float.MIN_VALUE), 
				new Vector2(Float.MIN_VALUE, Float.MAX_VALUE)};
		for(Vector2 vert : verts){
			aabb[0].x = Math.min(aabb[0].x, vert.x);
			aabb[0].y = Math.max(aabb[0].y, vert.y);
			aabb[1].x = Math.max(aabb[1].x, vert.x);
			aabb[1].y = Math.min(aabb[1].y, vert.y);
		}
		return aabb;
	}
	
	public static Vector2 getDimensions(Vector2[] verts){
		Vector2[] aabb = getAABB(verts); 
		return new Vector2(aabb[1].x - aabb[0].x, aabb[0].y - aabb[1].y);
	}
}
