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
	
	public abstract Joint attach(World world);
	
	/**
	 * @return finished attached joint using GDXJoint data
	 */
	protected Joint attach(World world, JointDef def){
		Body bodyA = null, bodyB = null;
		for(Iterator<Body> iter = world.getBodies(); iter.hasNext();){
			Body body = iter.next();
			if(body.getUserData().equals(this.bodyA))
				bodyA = body;
			else if(body.getUserData().equals(this.bodyB))
				bodyB = body;
		}
		if(bodyA == null || bodyB == null){
			Gdx.app.error("GDXJoint.attach", "Body null! bodyA:" + this.bodyA + " bodyB:" + this.bodyB);
			return null;
		}
		def.bodyA = bodyA;
		def.bodyB = bodyB;
		def.type = jointType;
		def.collideConnected = collideConnected;
		Gdx.app.error("GDXJoint.attach", "Successfully created joint " + toString());
		return world.createJoint(def);
	}
	
	@Override public String toString(){
		return "[GDXJoint name:" + name + " type:" + jointType + "]";
	}
}
