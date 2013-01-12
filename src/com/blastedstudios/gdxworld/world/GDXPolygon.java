package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GDXPolygon implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final PolygonShape polygonShape = new PolygonShape();
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
		BodyDef bd = new BodyDef();
		bd.type = type;
		polygonShape.set(vertices.toArray(new Vector2[vertices.size()]));
		fd.shape = polygonShape;
		Body body = world.createBody(bd); 
		body.createFixture(fd);
		return body;
	}

	public Vector2 getClosestVertex(float x, float y) {
		Vector2 closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(Vector2 vertex : vertices){
			float distance = vertex.dst2(x, y);
			if(closest == null || closestDistance > distance){
				closest = vertex;
				closestDistance = distance;
			}
		}
		return closest;
	}
}
