package com.blastedstudios.gdxworld.world.shape;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GDXShape implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	protected String name;
	protected float density = 1f, friction = .5f, restitution = .3f;
	protected BodyType bodyType = BodyType.StaticBody;
	protected Vector2 center = new Vector2();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public BodyType getBodyType() {
		return bodyType;
	}

	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
	}

	public float getRestitution() {
		return restitution;
	}

	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	public Vector2 getCenter() {
		return center;
	}

	public void setCenter(Vector2 center) {
		this.center = center;
	}
	
	public abstract Vector2 getDimensions();
	
	/**
	 * @param overrideStatic use BodyType.StaticBody no matter what bodyType is set to
	 */
	public abstract Body createFixture(World world, boolean overrideStatic);
	
	public abstract float getDistance(float x, float y);

	@Override public String toString(){
		return "[GDXShape name:" + name + "]";
	}

	public abstract Object clone();
	
	protected Object clone(GDXShape clone){
		clone.setBodyType(bodyType);
		clone.setCenter(center.cpy());
		clone.setDensity(density);
		clone.setFriction(friction);
		clone.setName(name);
		clone.setRestitution(restitution);
		return clone;
	}
}
