package com.blastedstudios.gdxworld.world.metadata;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class CameraShot implements Cloneable, Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private Vector2 position = new Vector2(), velocity = new Vector2();
	private String name = "Shot-" + count++;
	
	@Override public Object clone(){
		CameraShot shots = new CameraShot();
		shots.position.set(position);
		shots.velocity.set(velocity);
		shots.name = name;
		return shots;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
