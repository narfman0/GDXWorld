package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;

public class PulleyJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float lengthA, lengthB, ratio;

	@Override public Joint attach(World world) {
		PulleyJointDef def = new PulleyJointDef();
		def.lengthA = lengthA;
		def.lengthB = lengthB;
		def.ratio = ratio;
		return attach(world, def);
	}

	public float getLengthA() {
		return lengthA;
	}

	public void setLengthA(float lengthA) {
		this.lengthA = lengthA;
	}

	public float getLengthB() {
		return lengthB;
	}

	public void setLengthB(float lengthB) {
		this.lengthB = lengthB;
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

}
