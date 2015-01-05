package com.blastedstudios.gdxworld.plugin.quest.manifestation.platform;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PlatformManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	private static final float TURN_DISTANCE = Properties.getFloat("platform.turn.distance", 5f);
	public static PlatformManifestation DEFAULT = new PlatformManifestation("Name", new Vector2(), 
			new Vector2(), 5000, "", 10, 10, 10, 10);
	/**
	 * Name of physics object on which we execute tweaks
	 */
	private String name = "", joint = "";
	private Vector2 pointA = new Vector2(), pointB = new Vector2();
	/**
	 * When platform reaches destination, wait this many seconds before turning
	 */
	private long waitDuration = 5000;
	private float maxMotorForceA, motorSpeedA, maxMotorForceB, motorSpeedB;
	
	private transient boolean towardA;
	/**
	 * Time to start changing direction after waiting
	 */
	private transient long timeChangeDirection;
	
	public PlatformManifestation(){}
	
	public PlatformManifestation(String name, Vector2 pointA, Vector2 pointB, long waitDuration, String joint,
			float maxMotorForceA, float motorSpeedA, float maxMotorForceB, float motorSpeedB){
		this.name = name;
		this.pointA = pointA;
		this.pointB = pointB;
		this.waitDuration = waitDuration;
		this.joint = joint;
		this.maxMotorForceA = maxMotorForceA;
		this.motorSpeedA = motorSpeedA;
		this.maxMotorForceB = maxMotorForceB;
		this.motorSpeedB = motorSpeedB;
	}
	
	@Override public CompletionEnum execute(float dt) {
		PrismaticJoint joint = (PrismaticJoint)executor.getPhysicsJoint(this.joint);
		joint.enableMotor(true);
		joint.setMaxMotorForce(maxMotorForceB);
		joint.setMotorSpeed(motorSpeedB);
		executor.getPhysicsObject(name).setType(BodyType.DynamicBody);
		return CompletionEnum.EXECUTING;
	}
	
	@Override public CompletionEnum tick(){
		PrismaticJoint joint = (PrismaticJoint)executor.getPhysicsJoint(this.joint);
		if(joint.isMotorEnabled() && towardA && executor.getPhysicsObject(name).getWorldCenter().dst(pointA) < TURN_DISTANCE){
			towardA = false;
			joint.setMaxMotorForce(maxMotorForceB);
			joint.setMotorSpeed(motorSpeedB);
			joint.enableMotor(false);
			executor.getPhysicsObject(name).setType(BodyType.StaticBody);
			timeChangeDirection = System.currentTimeMillis() + waitDuration;
		}else if(joint.isMotorEnabled() && !towardA && executor.getPhysicsObject(name).getWorldCenter().dst(pointB) < TURN_DISTANCE){
			towardA = true;
			joint.setMaxMotorForce(maxMotorForceA);
			joint.setMotorSpeed(motorSpeedA);
			joint.enableMotor(false);
			executor.getPhysicsObject(name).setType(BodyType.StaticBody);
			timeChangeDirection = System.currentTimeMillis() + waitDuration;
		}else if(!joint.isMotorEnabled() && System.currentTimeMillis() > timeChangeDirection){
			joint.enableMotor(true);
			executor.getPhysicsObject(name).setType(BodyType.DynamicBody);
		}
		return CompletionEnum.EXECUTING;
	}

	@Override public AbstractQuestManifestation clone() {
		return new PlatformManifestation(name, pointA.cpy(), pointB.cpy(), waitDuration, joint, 
				maxMotorForceA, motorSpeedA, maxMotorForceB, motorSpeedB);
	}

	@Override public String toString() {
		return "[PlatformManifestation: name:" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector2 getPointA() {
		return pointA;
	}

	public void setPointA(Vector2 pointA) {
		this.pointA = pointA;
	}

	public Vector2 getPointB() {
		return pointB;
	}

	public void setPointB(Vector2 pointB) {
		this.pointB = pointB;
	}

	public long getWaitDuration() {
		return waitDuration;
	}

	public void setWaitDuration(long waitDuration) {
		this.waitDuration = waitDuration;
	}

	public String getJoint() {
		return joint;
	}

	public void setJoint(String joint) {
		this.joint = joint;
	}

	public float getMaxMotorForceA() {
		return maxMotorForceA;
	}

	public void setMaxMotorForceA(float maxMotorForceA) {
		this.maxMotorForceA = maxMotorForceA;
	}

	public float getMotorSpeedA() {
		return motorSpeedA;
	}

	public void setMotorSpeedA(float motorSpeedA) {
		this.motorSpeedA = motorSpeedA;
	}

	public float getMaxMotorForceB() {
		return maxMotorForceB;
	}

	public void setMaxMotorForceB(float maxMotorForceB) {
		this.maxMotorForceB = maxMotorForceB;
	}

	public float getMotorSpeedB() {
		return motorSpeedB;
	}

	public void setMotorSpeedB(float motorSpeedB) {
		this.motorSpeedB = motorSpeedB;
	}
}
