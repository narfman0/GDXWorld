package com.blastedstudios.gdxworld.world.shape;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;

public class GDXCircle extends GDXShape {
	private static final long serialVersionUID = 1L;
	private float radius = 1f;

	@Override public Body createFixture(World world, boolean overrideStatic) {
		FixtureDef fd = new FixtureDef();
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		BodyType type = overrideStatic ? BodyType.StaticBody : bodyType;
		Body body = PhysicsHelper.createCircle(world, radius, center, type);
		body.setTransform(center, 0);
		body.setUserData(name);
		return body;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	@Override public float getDistance(float x, float y) {
		return Math.max(0, center.dst(x, y) - radius);
	}

	@Override public Object clone() {
		GDXCircle clone = new GDXCircle();
		clone.setRadius(radius);
		return super.clone(clone);
	}
	
	@Override public String toString(){
		return "[GDXCircle: name " + name + " radius " + radius + " center " + center + "]";
	}

	@Override public Vector2 getDimensions() {
		return new Vector2(radius*2,radius*2);
	}
}
