package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

public class DistanceJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float dampeningRatio, frequencyHz, length;
	private Vector2 anchorA = new Vector2(), anchorB = new Vector2();

	@Override public Joint attach(World world) {
		DistanceJointDef def = new DistanceJointDef();
		def.dampingRatio = dampeningRatio;
		def.frequencyHz = frequencyHz;
		def.length = length;
		Body[] bodies = getBodyAB(world);
		def.initialize(bodies[0], bodies[1], anchorA, anchorB);
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

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public Vector2 getAnchorA() {
		return anchorA;
	}

	public void setAnchorA(Vector2 anchorA) {
		this.anchorA = anchorA;
	}

	public Vector2 getAnchorB() {
		return anchorB;
	}

	public void setAnchorB(Vector2 anchorB) {
		this.anchorB = anchorB;
	}

	@Override public Vector2 getCenter() {
		return anchorA.cpy().add(anchorB).div(2);
	}

	@Override public Object clone() {
		DistanceJoint clone = new DistanceJoint();
		clone.setAnchorA(anchorA);
		clone.setAnchorB(anchorB);
		clone.setDampeningRatio(dampeningRatio);
		clone.setFrequencyHz(frequencyHz);
		clone.setLength(length);
		return super.clone(clone);
	}

	@Override public void translate(Vector2 center) {
		anchorA.add(center);
		anchorB.add(center);
	}

	@Override public void scl(float scalar) {
		anchorA.scl(scalar);
		anchorB.scl(scalar);
	}
}
