package com.blastedstudios.gdxworld.world.joint;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.util.Log;

/**
 * Joints connect shapes in unique ways. 
 * See http://www.box2d.org/manual.html chapter 8 for more details.
 */
public abstract class GDXJoint implements Cloneable,Serializable {
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Joint-"+count++, bodyA = "", bodyB = "";
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
			Log.error("GDXJoint.attach", "Body null! bodyA:" + this.bodyA + " bodyB:" + this.bodyB);
			return null;
		}
		def.bodyA = bodies[0];
		def.bodyB = bodies[1];
		def.type = jointType;
		def.collideConnected = collideConnected;
		Joint joint = world.createJoint(def);
		Log.error("GDXJoint.attach", "Successfully created joint " + toString());
		joint.setUserData(name);
		return joint;
	}
	
	protected Body[] getBodyAB(World world){
		Body[] bodies = new Body[2];
		Array<Body> bodyArray = new Array<>(world.getBodyCount());
		world.getBodies(bodyArray);
		for(Body body : bodyArray){
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
	
	public abstract Vector2 getCenter();
	public abstract void translate(Vector2 center);
	public abstract void scl(float scalar);
	@Override public abstract Object clone();
	
	protected Object clone(GDXJoint clone){
		clone.setBodyA(bodyA);
		clone.setBodyB(bodyB);
		clone.setCollideConnected(collideConnected);
		clone.setJointType(jointType);
		clone.setName(name);
		return clone;
	}
}
