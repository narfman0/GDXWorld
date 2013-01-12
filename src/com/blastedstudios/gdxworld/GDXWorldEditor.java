package com.blastedstudios.gdxworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.blastedstudios.gdxworld.ui.MainScreen;

public class GDXWorldEditor extends Game {
	@Override public void create () {
		setScreen(new MainScreen(this));
	}
	
	public static void main (String[] argv) {
		new LwjglApplication(new GDXWorldEditor(), "GDX World Editor", 1280, 1024, false);
	}
}
