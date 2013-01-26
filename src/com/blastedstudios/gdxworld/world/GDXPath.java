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

public class GDXPath implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private String name = "";
	private List<Vector2> nodes = new ArrayList<Vector2>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Vector2> getNodes() {
		return nodes;
	}

	public void setNodes(List<Vector2> nodes) {
		this.nodes = nodes;
	}

	public Vector2 getClosestNode(float x, float y) {
		return PolygonUtils.getClosestNode(x, y, nodes);
	}
	
	@Override public String toString(){
		return "[GDXPath name:" + getName() + "]";
	}

	public Body createFixture(World world, FixtureDef fixtureDef, BodyType type) {
		return PhysicsHelper.createFixture(world, fixtureDef, type, nodes, PhysicsHelper.POLYGON_SHAPE);
	}
	
	@Override public Object clone(){
		GDXPath clone = new GDXPath();
		clone.setName(name);
		for(Vector2 node : nodes)
			clone.getNodes().add(node.cpy());
		return clone;
	}
}
