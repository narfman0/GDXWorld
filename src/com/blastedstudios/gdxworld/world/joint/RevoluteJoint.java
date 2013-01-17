package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class RevoluteJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private boolean enableLimit, enableMotor;
	private float lowerAngle, maxMotorTorque, motorSpeed, referenceAngle;

	@Override public Joint attach(World world) {
		RevoluteJointDef def = new RevoluteJointDef();
		def.enableLimit = enableLimit;
		def.enableMotor = enableMotor;
		def.lowerAngle = lowerAngle;
		def.maxMotorTorque = maxMotorTorque;
		def.motorSpeed = motorSpeed;
		def.referenceAngle = referenceAngle;
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

	public float getLowerAngle() {
		return lowerAngle;
	}

	public void setLowerAngle(float lowerAngle) {
		this.lowerAngle = lowerAngle;
	}

	public float getMaxMotorTorque() {
		return maxMotorTorque;
	}

	public void setMaxMotorTorque(float maxMotorTorque) {
		this.maxMotorTorque = maxMotorTorque;
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

}
