package com.blastedstudios.gdxworld.world.joint;

import java.io.Serializable;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GDXJoint implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name = "", bodyA = "", bodyB = "";
	private JointType jointType = JointType.RevoluteJoint;
	private boolean collideConnected;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public JointType getJointType() {
		return jointType;
	}

	public void setJointType(JointType jointType) {
		this.jointType = jointType;
	}

	public boolean isCollideConnected() {
		return collideConnected;
	}

	public void setCollideConnected(boolean collideConnected) {
		this.collideConnected = collideConnected;
	}

	public String getBodyA() {
		return bodyA;
	}

	public void setBodyA(String bodyA) {
		this.bodyA = bodyA;
	}

	public String getBodyB() {
		return bodyB;
	}

	public void setBodyB(String bodyB) {
		this.bodyB = bodyB;
	}
	
	public abstract Joint attach(World world);
	
	/**
	 * @return finished attached joint using GDXJoint data
	 */
	protected Joint attach(World world, JointDef def){
		Body[] bodies = getBodyAB(world);
		if(bodies[0] == null || bodies[1] == null){
			Gdx.app.error("GDXJoint.attach", "Body null! bodyA:" + this.bodyA + " bodyB:" + this.bodyB);
			return null;
		}
		def.bodyA = bodies[0];
		def.bodyB = bodies[1];
		def.type = jointType;
		def.collideConnected = collideConnected;
		Gdx.app.error("GDXJoint.attach", "Successfully created joint " + toString());
		return world.createJoint(def);
	}
	
	public float getDistance(float x, float y, World world){
		Body[] bodies = getBodyAB(world);
		if(bodies[0] == null || bodies[1] == null){
			Gdx.app.error("GDXJoint.getDistance", "Body null! bodyA:" + bodyA + " bodyB:" + bodyB);
			return Float.MAX_VALUE;
		}
		//TODO might not work as position is always 0,0, its the fixture that has pts
		return Math.min(bodies[0].getPosition().dst(x,y), bodies[1].getPosition().dst(x, y));
	}
	
	protected Body[] getBodyAB(World world){
		Body[] bodies = new Body[2];
		for(Iterator<Body> iter = world.getBodies(); iter.hasNext();){
			Body body = iter.next();
			if(this.bodyA.equals(body.getUserData()))
				bodies[0] = body;
			else if(this.bodyB.equals(body.getUserData()))
				bodies[1] = body;
		}
		return bodies;
	}
	
	@Override public String toString(){
		return "[GDXJoint name:" + name + " type:" + jointType + "]";
	}
}
