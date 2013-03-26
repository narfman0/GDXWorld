package com.blastedstudios.gdxworld.world.light;

import java.io.Serializable;

import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;

public abstract class GDXLight implements Cloneable,Serializable{
	public static final Color DEFAULT_COLOR = new Color(.9f,.9f,.9f,.1f);
	private static final long serialVersionUID = 1L;
	protected Color color = DEFAULT_COLOR.cpy();
	protected int rays = 1000;
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public int getRays() {
		return rays;
	}

	public void setRays(int rays) {
		this.rays = rays;
	}
	

	protected Object clone(GDXLight clone){
		clone.setRays(rays);
		clone.setColor(color.cpy());
		return clone;
	}
	
	@Override public abstract Object clone();
	public abstract Light create(RayHandler handler);
}
