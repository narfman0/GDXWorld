package com.blastedstudios.gdxworld;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import net.xeoh.plugins.base.util.uri.ClassURI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.blastedstudios.gdxworld.ui.MainScreen;
import com.blastedstudios.gdxworld.ui.TempWorldScreen;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class GDXWorldEditor extends GDXGame {
	private static FileHandle loadFile;
	private static String[] args;
	
	@Override public void create () {
		if(args != null)
			parseArgs(args);
		PluginUtil.initialize(ClassURI.CLASSPATH);
		FileHandle handle = Gdx.files.internal("data/gdxworld.properties"); 
		if(handle != null)
			Properties.load(handle.read());
		else 
			Log.error("Properties.<init>", "Failed to load properties, file not found!");
		pushScreen(new MainScreen(this));
		if(loadFile != null)
			pushScreen(new WorldEditorScreen(this, GDXWorld.load(loadFile), loadFile));
		else if(TempWorldScreen.isTempFilePresent())
			pushScreen(new TempWorldScreen(this));
	}
	
	public static void main (String[] argv) {
		args = argv;
		new LwjglApplication(new GDXWorldEditor(), generateConfiguration("GDX World Editor"));
	}
	
	public static LwjglApplicationConfiguration generateConfiguration(String title){
		boolean fullscreen = Properties.getBool("graphics.fullscreen", false);
		Dimension dimension = getDimension(fullscreen);
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = title;
	    cfg.width = dimension.width;
	    cfg.height = dimension.height;
	    cfg.fullscreen = fullscreen;
	    cfg.vSyncEnabled = Properties.getBool("graphics.vsync", true);
	    return cfg;
	}
	
	public static Dimension getDimension(boolean fullscreen){
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = Properties.getInt("graphics.width", gd.getDisplayMode().getWidth());
		int height = Properties.getInt("graphics.height", gd.getDisplayMode().getHeight());
		if(!fullscreen){
			width = width < 1800 ? 800 : 1680;
			height = height < 1080 ? 600 : 1000;
		}
		return new Dimension(width, height);
	}
	
	private void parseArgs(String[] argv){
		for(int i=0; i<argv.length; i+=2){
			if(argv[i].equals("-l"))
				loadFile = new FileHandle(argv[i+1]);
			//set properties via cli
			if(argv[i].startsWith("-P"))
				Properties.set(argv[i].substring(2), argv[i+1]);
		}
	}
}
