package com.blastedstudios.gdxworld.world.quest.manifestation;

import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

public class PrismaticJointManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final PrismaticJointManifestation DEFAULT = new PrismaticJointManifestation();
	private String name = "";
	private boolean enableMotor = false;
	private float maxMotorForce = 0f, motorSpeed = 0f;
	
	public PrismaticJointManifestation(){}
	
	public PrismaticJointManifestation(String name, boolean enableMotor, 
			float maxMotorTorque, float motorSpeed){
		this.name = name;
		this.enableMotor = enableMotor;
		this.maxMotorForce = maxMotorTorque;
		this.motorSpeed = motorSpeed;
	}

	@Override public CompletionEnum execute() {
		PrismaticJoint joint = ((PrismaticJoint)executor.getPhysicsJoint(name));
		joint.enableMotor(enableMotor);
		joint.setMaxMotorForce(maxMotorForce);
		joint.setMotorSpeed(motorSpeed);
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new PrismaticJointManifestation(name, enableMotor, maxMotorForce, motorSpeed);
	}

	@Override public String toString() {
		return "[PrismaticJointManifestation name:" + name + " enableMotor:" + enableMotor + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setMaxMotorTorque(float maxMotorForce) {
		this.maxMotorForce = maxMotorForce;
	}

	public float getMotorSpeed() {
		return motorSpeed;
	}

	public void setMotorSpeed(float motorSpeed) {
		this.motorSpeed = motorSpeed;
	}
}
