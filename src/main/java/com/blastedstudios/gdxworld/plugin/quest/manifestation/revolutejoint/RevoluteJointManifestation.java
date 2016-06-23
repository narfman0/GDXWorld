package com.blastedstudios.gdxworld.plugin.quest.manifestation.revolutejoint;

import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class RevoluteJointManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final RevoluteJointManifestation DEFAULT = new RevoluteJointManifestation();
	private String name = "";
	private boolean enableMotor = false;
	private float maxMotorTorque = 0f, motorSpeed = 0f;
	
	public RevoluteJointManifestation(){}
	
	public RevoluteJointManifestation(String name, boolean enableMotor, 
			float maxMotorTorque, float motorSpeed){
		this.name = name;
		this.enableMotor = enableMotor;
		this.maxMotorTorque = maxMotorTorque;
		this.motorSpeed = motorSpeed;
	}

	@Override public CompletionEnum execute(float dt) {
		RevoluteJoint joint = ((RevoluteJoint)executor.getPhysicsJoint(name));
		joint.enableMotor(enableMotor);
		joint.setMaxMotorTorque(maxMotorTorque);
		joint.setMotorSpeed(motorSpeed);
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new RevoluteJointManifestation(name, enableMotor, maxMotorTorque, motorSpeed);
	}

	@Override public String toString() {
		return "[RevoluteJointManifestation name:" + name + " enableMotor:" + enableMotor + "]";
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
