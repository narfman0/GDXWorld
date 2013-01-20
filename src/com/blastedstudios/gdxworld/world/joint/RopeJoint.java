package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

public class RopeJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float maxLength;
	private transient Vector2 center;
	
	public RopeJoint(Vector2 center){
		this.center = center;
	}
	
	@Override public Joint attach(World world) {
		RopeJointDef def = new RopeJointDef();
		def.maxLength = maxLength;
		return attach(world, def);
	}

	public float getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(float maxLength) {
		this.maxLength = maxLength;
	}

	@Override public Vector2 getCenter() {
		return center.cpy();
	}
}
