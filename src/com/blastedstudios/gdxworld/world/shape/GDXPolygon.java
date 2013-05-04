package com.blastedstudios.gdxworld.world.shape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.math.PolygonUtils;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;

public class GDXPolygon extends GDXShape implements Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	/**
	 * Coordinates for vertices relative to center. To convert to world 
	 * coordinates, use the absolute version, e.g. getVerticesAbsolute
	 */
	private List<Vector2> vertices = new ArrayList<Vector2>();
	/**
	 * Dimensions of poly after getting aabb's max/min x/y. Cached for performance
	 */
	private transient Vector2 dimensions;
	
	public GDXPolygon(){
		name = "Polygon-" + count++;
	}

	public List<Vector2> getVertices() {
		return vertices;
	}

	public List<Vector2> getVerticesAbsolute() {
		if(center == null || vertices.isEmpty())
			return vertices;
		return PolygonUtils.getCenterVerticesReverse(vertices, center);
	}

	public void setVertices(List<Vector2> vertices) {
		this.vertices = vertices;
	}

	public void setVerticesAbsolute(List<Vector2> vertices) {
		this.vertices = PolygonUtils.getCenterVertices(vertices, center);
	}
	
	/**
	 * @param overrideStatic use BodyType.StaticBody no matter what bodyType is set to
	 */
	public Body createFixture(World world, boolean overrideStatic){
		FixtureDef fd = new FixtureDef();
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		BodyType type = overrideStatic ? BodyType.StaticBody : bodyType;
		Body body = PhysicsHelper.createFixture(world, fd, type, vertices, 
				PhysicsHelper.POLYGON_SHAPE);
		if(body != null){
			body.setTransform(center, 0);
			body.setUserData(name);
		}
		return body;
	}

	public Vector2 getClosestVertex(float x, float y) {
		return PolygonUtils.getClosestNode(x, y, getVerticesAbsolute());
	}
	
	@Override public String toString(){
		return "[GDXPolygon: " + name + " center " + center + "]";
	}

	@Override public float getDistance(float x, float y) {
		float closestDistance = Float.MAX_VALUE;
		for(Vector2 vertex : vertices)
			closestDistance = Math.min(closestDistance, vertex.cpy().add(center).dst2(x, y));
		return (float) Math.sqrt(closestDistance);//just doing one sqrt, assuming its faster..?
	}

	@Override public Object clone() {
		GDXPolygon clone = new GDXPolygon();
		for(Vector2 vertex : vertices)
			clone.getVertices().add(vertex.cpy());
		return super.clone(clone);
	}

	@Override public Vector2 getDimensions() {
		if(dimensions == null)
			dimensions = PolygonUtils.getDimensions((Vector2[]) vertices.toArray());
		return dimensions;
	}
	
	public void scl(float scalar){
		for(Vector2 vertex : vertices)
			vertex.scl(scalar);
	}
}
