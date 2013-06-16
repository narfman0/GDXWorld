package com.blastedstudios.gdxworld;

import java.io.File;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.blastedstudios.gdxworld.ui.MainScreen;
import com.blastedstudios.gdxworld.ui.TempWorldScreen;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class GDXWorldEditor extends GDXGame {
	private static File loadFile;
	private static String[] args;
	
	@Override public void create () {
		if(args != null)
			parseArgs(args);
		pushScreen(new MainScreen(this));
		if(loadFile != null && loadFile.canRead())
			pushScreen(new WorldEditorScreen(this, GDXWorld.load(loadFile), loadFile));
		else if(TempWorldScreen.isTempFilePresent())
			pushScreen(new TempWorldScreen(this));
	}
	
	public static void main (String[] argv) {
		args = argv;
		new LwjglApplication(new GDXWorldEditor(), "GDX World Editor", 1280, 1024, true);
	}
	
	private void parseArgs(String[] argv){
		for(int i=0; i<argv.length; i+=2){
			if(argv[i].equals("-l"))
				loadFile = new File(argv[i+1]);
			//set properties via cli
			if(argv[i].startsWith("-P"))
				Properties.set(argv[i].substring(2), argv[i+1]);
		}
	}
}
