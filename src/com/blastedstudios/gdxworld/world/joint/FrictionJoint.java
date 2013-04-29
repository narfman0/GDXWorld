package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;

public class FrictionJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float maxForce, maxTorque;
	private Vector2 anchor = new Vector2();

	@Override public Joint attach(World world) {
		FrictionJointDef def = new FrictionJointDef();
		def.maxForce = maxForce;
		def.maxTorque = maxTorque;
		Body[] bodies = getBodyAB(world);
		def.initialize(bodies[0], bodies[1], anchor);
		return attach(world, def);
	}

	public float getMaxForce() {
		return maxForce;
	}

	public void setMaxForce(float maxForce) {
		this.maxForce = maxForce;
	}

	public float getMaxTorque() {
		return maxTorque;
	}

	public void setMaxTorque(float maxTorque) {
		this.maxTorque = maxTorque;
	}

	public Vector2 getAnchor() {
		return anchor;
	}

	public void setAnchor(Vector2 anchor) {
		this.anchor = anchor;
	}

	@Override public Vector2 getCenter() {
		return anchor.cpy();
	}

	@Override public Object clone() {
		FrictionJoint clone = new FrictionJoint();
		clone.setAnchor(anchor);
		clone.setMaxForce(maxForce);
		clone.setMaxTorque(maxTorque);
		return super.clone(clone);
	}

	@Override public void translate(Vector2 center) {
		anchor.add(center);
	}

	@Override public void scl(float scalar) {
		anchor.scl(scalar);
	}
}
