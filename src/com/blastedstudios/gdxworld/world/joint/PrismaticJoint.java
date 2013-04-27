package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;

public class PrismaticJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private boolean enableLimit, enableMotor;
	private float lowerTranslation, maxMotorForce, motorSpeed, referenceAngle;
	private Vector2 anchor = new Vector2(), axis = new Vector2();

	@Override public Joint attach(World world) {
		PrismaticJointDef def = new PrismaticJointDef();
		def.enableLimit = enableLimit;
		def.enableMotor = enableMotor;
		def.motorSpeed = motorSpeed;
		def.lowerTranslation = lowerTranslation;
		def.maxMotorForce = maxMotorForce;
		def.motorSpeed = motorSpeed;
		def.referenceAngle = referenceAngle;
		Body[] bodies = getBodyAB(world);
		def.initialize(bodies[0], bodies[1], anchor, axis);
		return attach(world, def);
	}

	public boolean isEnableLimit() {
		return enableLimit;
	}

	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}

	public boolean isEnableMotor() {
		return enableMotor;
	}

	public void setEnableMotor(boolean enableMotor) {
		this.enableMotor = enableMotor;
	}

	public float getMaxMotorForce() {
		return maxMotorForce;
	}

	public void setMaxMotorForce(float maxMotorForce) {
		this.maxMotorForce = maxMotorForce;
	}

	public float getMotorSpeed() {
		return motorSpeed;
	}

	public void setMotorSpeed(float motorSpeed) {
		this.motorSpeed = motorSpeed;
	}

	public float getReferenceAngle() {
		return referenceAngle;
	}

	public void setReferenceAngle(float referenceAngle) {
		this.referenceAngle = referenceAngle;
	}

	public float getLowerTranslation() {
		return lowerTranslation;
	}

	public void setLowerTranslation(float lowerTranslation) {
		this.lowerTranslation = lowerTranslation;
	}

	public Vector2 getAnchor() {
		return anchor;
	}

	public void setAnchor(Vector2 anchor) {
		this.anchor = anchor;
	}

	public Vector2 getAxis() {
		return axis;
	}

	public void setAxis(Vector2 axis) {
		this.axis = axis;
	}

	@Override public Vector2 getCenter() {
		return anchor.cpy();
	}

	@Override public Object clone() {
		PrismaticJoint clone = new PrismaticJoint();
		clone.setAnchor(anchor.cpy());
		clone.setAxis(axis.cpy());
		clone.setEnableLimit(enableLimit);
		clone.setEnableMotor(enableMotor);
		clone.setLowerTranslation(lowerTranslation);
		clone.setMaxMotorForce(maxMotorForce);
		clone.setMotorSpeed(motorSpeed);
		clone.setReferenceAngle(referenceAngle);
		return super.clone(clone);
	}

	@Override public void translate(Vector2 center) {
		anchor.add(center);
		axis.add(center);
	}
}
