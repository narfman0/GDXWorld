package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

public class DistanceJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float dampeningRatio, frequencyHz, length;

	@Override public Joint attach(World world) {
		DistanceJointDef jointDef = new DistanceJointDef();
		jointDef.dampingRatio = dampeningRatio;
		jointDef.frequencyHz = frequencyHz;
		jointDef.length = length;
		return attach(world, jointDef);
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

	public void setFrequencyHz(float frequency) {
		this.frequencyHz = frequency;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}
}
