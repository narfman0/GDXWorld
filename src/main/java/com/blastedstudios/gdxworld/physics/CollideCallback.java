package com.blastedstudios.gdxworld.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

public class CollideCallback implements QueryCallback {
	private Body body;

	@Override public boolean reportFixture(Fixture fixture) {
		body = fixture.getBody();
		return false;
	}

	public Body getBody() {
		return body;
	}
}
