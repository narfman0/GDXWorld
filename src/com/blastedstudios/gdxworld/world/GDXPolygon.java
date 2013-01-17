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
	private ArrayList<Vector2> vertices;
	private String name;
	
	public GDXPolygon(){
		vertices = new ArrayList<Vector2>();
	}
	
	public GDXPolygon(String name, ArrayList<Vector2> vertices){
		this.name = name;
		this.vertices = vertices;
	}

	public List<Vector2> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vector2> vertices) {
		this.vertices = vertices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Body createFixture(World world, FixtureDef fd, BodyType type){
		return PhysicsHelper.createFixture(world, fd, type, vertices, PhysicsHelper.POLYGON_SHAPE);
	}

	public Vector2 getClosestVertex(float x, float y) {
		return PolygonUtils.getClosestNode(x, y, vertices);
	}
	
	@Override public String toString(){
		return "[GDXPolygon: " + name + "]";
	}
}
