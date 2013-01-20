package com.blastedstudios.gdxworld.world.quest.manifestation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	/**
	 * Name of physics object on which we execute tweaks
	 */
	private final String name;
	/**
	 * Impulse to be executed on named physics object
	 */
	private final Vector2 impulse;
	/**
	 * Changing body type, for instance, unlocking door
	 */
	private final BodyType type;
	/**
	 * Provide torque, likely to turn a wheel or similar
	 */
	private final float torque;
	
	public PhysicsManifestation(String name, Vector2 impulse, BodyType type, float torque){
		this.name = name;
		this.impulse = impulse;
		this.type = type;
		this.torque = torque;
	}

	@Override public void execute() {
		Body body = executor.getPhysicsObject(name); 
		body.applyLinearImpulse(impulse, body.getPosition());
		body.setType(type);
		body.applyTorque(torque);
	}

	public String getName() {
		return name;
	}

	public Vector2 getImpulse() {
		return impulse;
	}

	public BodyType getType() {
		return type;
	}

	public float getTorque() {
		return torque;
	}

	@Override public Object clone() {
		return new PhysicsManifestation(name, impulse, type, torque);
	}
}