package com.blastedstudios.gdxworld.plugin.quest.manifestation.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PhysicsManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	public static PhysicsManifestation DEFAULT = new PhysicsManifestation();
	/**
	 * Name of physics object on which we execute tweaks
	 */
	private String name = "";
	/**
	 * Impulse to be executed on named physics object
	 */
	private Vector2 impulse = new Vector2(), position = new Vector2(), velocity = new Vector2();
	/*
	 * Changing body type, for instance, unlocking door
	 */
	private BodyType type = BodyType.StaticBody;
	/**
	 * Provide torque, likely to turn a wheel or similar
	 */
	private float torque, angle;
	private boolean hasPosition, hasVelocity, hasAngle, 
			relativePosition = true, relativeVelocity = true, relativeAngle = true;
	
	public PhysicsManifestation(){}
	
	public PhysicsManifestation(String name, Vector2 impulse, BodyType type, float torque, 
			boolean hasPosition, boolean relativePosition, Vector2 position, 
			boolean hasVelocity, boolean relativeVelocity, Vector2 velocity,
			boolean hasAngle, boolean relativeAngle, float angle){
		this.name = name;
		this.impulse = impulse;
		this.type = type;
		this.torque = torque;
		this.hasPosition = hasPosition;
		this.relativePosition = relativePosition;
		this.position = position;
		this.hasVelocity = hasVelocity;
		this.relativeVelocity = relativeVelocity;
		this.velocity = velocity;
		this.hasAngle = hasAngle;
		this.relativeAngle = relativeAngle;
		this.angle = angle;
	}

	@Override public CompletionEnum execute() {
		Body body = executor.getPhysicsObject(name); 
		if(body == null){
			Gdx.app.error("PhysicsManifestation.execute", "Can't find physics object " + name);
			return CompletionEnum.COMPLETED;
		}
		body.applyLinearImpulse(impulse, body.getPosition(),true);
		if(hasVelocity)
			body.setLinearVelocity(relativeVelocity ? body.getLinearVelocity().cpy().add(velocity) : velocity);
		if(hasPosition)
			body.setTransform(relativePosition ? body.getPosition().cpy().add(position) : position, 
					hasAngle ? (relativeAngle ? body.getAngle() + angle : angle) : body.getAngle());
		body.setType(type);
		body.applyTorque(torque,true);
		return CompletionEnum.COMPLETED;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector2 getImpulse() {
		return impulse;
	}

	public void setImpulse(Vector2 impulse) {
		this.impulse = impulse;
	}

	public BodyType getType() {
		return type;
	}

	public void setType(BodyType type) {
		this.type = type;
	}

	public float getTorque() {
		return torque;
	}

	public void setTorque(float torque) {
		this.torque = torque;
	}

	@Override public AbstractQuestManifestation clone() {
		return new PhysicsManifestation(name, impulse, type, torque, 
				hasPosition, relativePosition, position, 
				hasVelocity, relativeVelocity, velocity, 
				hasAngle, relativeAngle, angle);
	}

	@Override public String toString() {
		return "[PhysicsManifestation: name:" + name + " impulse:" + impulse + 
				" type:" + type + " torque:" + torque + "]";
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public boolean isHasPosition() {
		return hasPosition;
	}

	public void setHasPosition(boolean hasPosition) {
		this.hasPosition = hasPosition;
	}

	public boolean isHasVelocity() {
		return hasVelocity;
	}

	public void setHasVelocity(boolean hasVelocity) {
		this.hasVelocity = hasVelocity;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public boolean isHasAngle() {
		return hasAngle;
	}

	public void setHasAngle(boolean hasAngle) {
		this.hasAngle = hasAngle;
	}

	public boolean isRelativePosition() {
		return relativePosition;
	}

	public void setRelativePosition(boolean relativePosition) {
		this.relativePosition = relativePosition;
	}

	public boolean isRelativeVelocity() {
		return relativeVelocity;
	}

	public void setRelativeVelocity(boolean relativeVelocity) {
		this.relativeVelocity = relativeVelocity;
	}

	public boolean isRelativeAngle() {
		return relativeAngle;
	}

	public void setRelativeAngle(boolean relativeAngle) {
		this.relativeAngle = relativeAngle;
	}
}