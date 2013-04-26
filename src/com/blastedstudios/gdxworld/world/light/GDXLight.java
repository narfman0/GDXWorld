package com.blastedstudios.gdxworld.world.light;

import java.io.Serializable;

import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;

public abstract class GDXLight implements Cloneable,Serializable,Comparable<GDXLight>{
	public static final Color DEFAULT_COLOR = new Color(.9f,.9f,.9f,.5f);
	private static final long serialVersionUID = 1L;
	/**
	 * Color in RGBA8888 format so it can be serialized
	 */
	protected int color = Color.rgba8888(DEFAULT_COLOR);
	protected int rays = 1000;

	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	public static Color convert(int color) {
		Color colorObj = new Color();
		Color.rgba8888ToColor(colorObj, color);
		return colorObj;
	}
	
	public static int convert(Color color) {
		return Color.rgba8888(color);
	}

	public int getRays() {
		return rays;
	}

	public void setRays(int rays) {
		this.rays = rays;
	}
	

	protected Object clone(GDXLight clone){
		clone.setRays(rays);
		clone.setColor(color);
		return clone;
	}
	
	@Override public abstract Object clone();
	public abstract Light create(RayHandler handler);
}
