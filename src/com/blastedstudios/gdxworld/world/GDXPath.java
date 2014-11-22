package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.math.PolygonUtils;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;

/**
 * Represents a route defined as a set of points. 
 * 
 * This may be used for the default path NPCs take when not in combat, certain
 * predefined paths for cutscenes, etc. 
 */
public class GDXPath implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Path-"+count++;
	private List<Vector2> nodes = new ArrayList<Vector2>();
	private CompletionEnum completionCriteria = CompletionEnum.REPEAT;

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
		return PhysicsHelper.createFixture(world, fixtureDef, type, nodes, new PolygonShape(), 
				(short)-1, (short)1, (short)0);
	}
	
	@Override public Object clone(){
		GDXPath clone = new GDXPath();
		clone.setName(name);
		clone.setCompletionCriteria(completionCriteria);
		for(Vector2 node : nodes)
			clone.getNodes().add(node.cpy());
		return clone;
	}
	
	public CompletionEnum getCompletionCriteria() {
		if(completionCriteria == null)
			completionCriteria = CompletionEnum.REPEAT;
		return completionCriteria;
	}

	public void setCompletionCriteria(CompletionEnum completionCriteria) {
		this.completionCriteria = completionCriteria;
	}

	public enum CompletionEnum{
		REPEAT, REVERSE, END
	}

	public GDXPath reverse() {
		Collections.reverse(nodes);
		return this;
	}
}
