package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

public class WheelJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float dampingRatio, frequencyHz, maxMotorTorque, motorSpeed;
	private boolean enableMotor;

	@Override public Joint attach(World world) {
		WheelJointDef def = new WheelJointDef();
		def.dampingRatio = dampingRatio;
		def.enableMotor = enableMotor;
		def.frequencyHz  = frequencyHz;
		def.maxMotorTorque = maxMotorTorque;
		def.motorSpeed = motorSpeed;
		return attach(world, def);
	}

	public float getDampingRatio() {
		return dampingRatio;
	}

	public void setDampingRatio(float dampingRatio) {
		this.dampingRatio = dampingRatio;
	}

	public boolean isEnableMotor() {
		return enableMotor;
	}

	public void setEnableMotor(boolean enableMotor) {
		this.enableMotor = enableMotor;
	}

	public float getFrequencyHz() {
		return frequencyHz;
	}

	public void setFrequencyHz(float frequencyHz) {
		this.frequencyHz = frequencyHz;
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

}
