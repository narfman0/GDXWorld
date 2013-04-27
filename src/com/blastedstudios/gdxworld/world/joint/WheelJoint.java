package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

public class WheelJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private float dampingRatio, frequencyHz, maxMotorTorque, motorSpeed;
	private boolean enableMotor;
	private Vector2 anchor = new Vector2(), axis = new Vector2();

	@Override public Joint attach(World world) {
		WheelJointDef def = new WheelJointDef();
		def.dampingRatio = dampingRatio;
		def.enableMotor = enableMotor;
		def.frequencyHz  = frequencyHz;
		def.maxMotorTorque = maxMotorTorque;
		def.motorSpeed = motorSpeed;
		Body[] bodies = getBodyAB(world);
		def.initialize(bodies[0], bodies[1], anchor, axis);
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
		WheelJoint clone = new WheelJoint();
		clone.setAnchor(anchor.cpy());
		clone.setAxis(axis.cpy());
		clone.setDampingRatio(dampingRatio);
		clone.setEnableMotor(enableMotor);
		clone.setFrequencyHz(frequencyHz);
		clone.setMaxMotorTorque(maxMotorTorque);
		clone.setMotorSpeed(motorSpeed);
		return super.clone(clone);
	}

	@Override public void translate(Vector2 center) {
		anchor.add(center);
		axis.add(center);
	}
}
