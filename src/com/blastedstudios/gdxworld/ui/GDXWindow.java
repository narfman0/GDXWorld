package com.blastedstudios.gdxworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public abstract class GDXWindow extends Window {
	public GDXWindow(String title, Skin skin) {
		super(title, skin);
	}

	/**
	 * @param x screen coordinates
	 * @param y screen coordinates
	 * @return if the world window contains teh given point (in screen coordinates)
	 */
	public boolean contains(float x, float y){
		return getX()+getWidth() > x && getX() < x &&
				getY()+getHeight() > Gdx.graphics.getHeight()-y && getY() < Gdx.graphics.getHeight()-y;
	}
}
