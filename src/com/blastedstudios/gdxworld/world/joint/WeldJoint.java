package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class WeldJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float referenceAngle;

	@Override public Joint attach(World world) {
		WeldJointDef def = new WeldJointDef();
		def.referenceAngle = referenceAngle;
		return attach(world, def);
	}

	public float getReferenceAngle() {
		return referenceAngle;
	}

	public void setReferenceAngle(float referenceAngle) {
		this.referenceAngle = referenceAngle;
	}

}
