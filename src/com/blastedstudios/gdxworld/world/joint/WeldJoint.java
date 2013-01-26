package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class WeldJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float referenceAngle;
	private Vector2 anchor = new Vector2();

	@Override public Joint attach(World world) {
		WeldJointDef def = new WeldJointDef();
		def.referenceAngle = referenceAngle;
		Body[] bodies = getBodyAB(world);
		def.initialize(bodies[0], bodies[1], anchor);
		return attach(world, def);
	}

	public float getReferenceAngle() {
		return referenceAngle;
	}

	public void setReferenceAngle(float referenceAngle) {
		this.referenceAngle = referenceAngle;
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
		WeldJoint clone = new WeldJoint();
		clone.setAnchor(anchor.cpy());
		clone.setReferenceAngle(referenceAngle);
		return super.clone(clone);
	}
}
