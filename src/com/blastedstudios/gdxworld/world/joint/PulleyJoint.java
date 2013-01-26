package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;

public class PulleyJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float lengthA, lengthB, ratio;
	private Vector2 anchorA = new Vector2(), anchorB = new Vector2(),
			groundAnchorA = new Vector2(), groundAnchorB = new Vector2();

	@Override public Joint attach(World world) {
		PulleyJointDef def = new PulleyJointDef();
		def.lengthA = lengthA;
		def.lengthB = lengthB;
		def.ratio = ratio;
		Body[] bodies = getBodyAB(world);
		def.initialize(bodies[0], bodies[1], groundAnchorA, groundAnchorB, anchorA, anchorB, ratio);
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

	public Vector2 getGroundAnchorA() {
		return groundAnchorA;
	}

	public void setGroundAnchorA(Vector2 groundAnchorA) {
		this.groundAnchorA = groundAnchorA;
	}

	public Vector2 getGroundAnchorB() {
		return groundAnchorB;
	}

	public void setGroundAnchorB(Vector2 groundAnchorB) {
		this.groundAnchorB = groundAnchorB;
	}

	@Override public Vector2 getCenter() {
		return anchorA.cpy().add(anchorB).div(2);
	}

	@Override public Object clone() {
		PulleyJoint clone = new PulleyJoint();
		clone.setAnchorA(anchorA.cpy());
		clone.setAnchorB(anchorB.cpy());
		clone.setGroundAnchorA(groundAnchorA.cpy());
		clone.setGroundAnchorB(groundAnchorB.cpy());
		clone.setLengthA(lengthA);
		clone.setLengthB(lengthB);
		clone.setRatio(ratio);
		return super.clone(clone);
	}
}
