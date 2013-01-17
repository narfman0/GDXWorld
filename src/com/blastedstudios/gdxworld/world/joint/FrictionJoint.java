package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;

public class FrictionJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float maxForce, maxTorque;

	@Override public Joint attach(World world) {
		FrictionJointDef def = new FrictionJointDef();
		def.maxForce = maxForce;
		def.maxTorque = maxTorque;
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

}
