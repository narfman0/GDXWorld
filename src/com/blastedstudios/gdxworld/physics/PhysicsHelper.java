package com.blastedstudios.gdxworld.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsHelper {
	public static Body createCircle(World world, float radius, Vector2 position){
		Body body = world.createBody(new BodyDef());
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		body.createFixture(shape, 1);
		body.setTransform(position, 0);
		body.setActive(false);
		return body;
	}
}
