package com.blastedstudios.gdxworld.world;

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

public class GDXPolygon implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * Coordinates for vertices relative to center
	 */
	private List<Vector2> vertices = new ArrayList<Vector2>();
	private String name = "";
	private float density = 1f, friction = .5f, restitution = .3f;
	private BodyType bodyType = BodyType.StaticBody;
	private Vector2 center;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public BodyType getBodyType() {
		return bodyType;
	}

	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
	}

	public float getRestitution() {
		return restitution;
	}

	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	public Vector2 getCenter() {
		return center;
	}

	public void setCenter(Vector2 center) {
		this.center = center;
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
		Body body = PhysicsHelper.createFixture(world, fd, type, vertices, PhysicsHelper.POLYGON_SHAPE);
		body.setTransform(center, 0);
		body.setUserData(name);
		return body;
	}

	public Vector2 getClosestVertex(float x, float y) {
		return PolygonUtils.getClosestNode(x, y, getVerticesAbsolute());
	}
	
	@Override public String toString(){
		return "[GDXPolygon: " + name + "]";
	}
}
