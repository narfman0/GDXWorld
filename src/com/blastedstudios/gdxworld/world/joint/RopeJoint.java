package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

public class RopeJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float maxLength;
	private Vector2 center = new Vector2();
	
	@Override public Joint attach(World world) {
		RopeJointDef def = new RopeJointDef();
		def.maxLength = maxLength;
		return attach(world, def);
	}

	@Override public Object clone() {
		RopeJoint clone = new RopeJoint();
		clone.setMaxLength(maxLength);
		clone.setCenter(center.cpy());
		return super.clone(clone);
	}

	@Override public void translate(Vector2 center) {
		center.add(center);
	}

	@Override public void scl(float scalar) {
		center.scl(scalar);
	}

	@Override public Vector2 getCenter() {
		return center.cpy();
	}

	public void setCenter(Vector2 center) {
		this.center = center;
	}

	public float getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(float maxLength) {
		this.maxLength = maxLength;
	}
}
