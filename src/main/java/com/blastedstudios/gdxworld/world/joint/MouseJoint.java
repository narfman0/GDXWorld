package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class MouseJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float dampeningRatio, frequencyHz, maxForce;
	private transient Vector2 center;
	
	public MouseJoint(Vector2 center){
		this.center = center;
	}

	@Override public Joint attach(World world) {
		MouseJointDef def = new MouseJointDef();
		def.dampingRatio = dampeningRatio;
		def.frequencyHz = frequencyHz;
		def.maxForce = maxForce;
		return attach(world, def);
	}

	public float getDampeningRatio() {
		return dampeningRatio;
	}

	public void setDampeningRatio(float dampeningRatio) {
		this.dampeningRatio = dampeningRatio;
	}

	public float getFrequencyHz() {
		return frequencyHz;
	}

	public void setFrequencyHz(float frequencyHz) {
		this.frequencyHz = frequencyHz;
	}

	public float getMaxForce() {
		return maxForce;
	}

	public void setMaxForce(float maxForce) {
		this.maxForce = maxForce;
	}

	@Override public Vector2 getCenter() {
		return center.cpy();
	}

	@Override public Object clone() {
		MouseJoint clone = new MouseJoint(center.cpy());
		clone.setDampeningRatio(dampeningRatio);
		clone.setFrequencyHz(frequencyHz);
		clone.setMaxForce(maxForce);
		return super.clone(clone);
	}

	@Override public void translate(Vector2 center) {
		center.add(center);
	}

	@Override public void scl(float scalar) {
		center.scl(scalar);
	}
}
