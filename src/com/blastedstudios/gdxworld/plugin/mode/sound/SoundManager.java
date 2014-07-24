package com.blastedstudios.gdxworld.plugin.mode.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.blastedstudios.gdxworld.util.FileUtil;

public class SoundManager {
	private static Map<String, Sound> MAP = new HashMap<>();
	
	public static Sound getSound(String name){
		if(!MAP.containsKey(name)){
			FileHandle file = FileUtil.find(name);
			if(file == null)
				file = FileUtil.find(name + ".mp3");
			if(file != null){
				try{
					MAP.put(name, Gdx.audio.newSound(file));
					Gdx.app.log("SoundManager.getSound", "Added sound " + name);
				}catch(Exception e){
					Gdx.app.error("SoundManager.getSound", "Sound found but error loading " + 
							name + ", using empty");
					MAP.put(name, null);
				}
			}else{
				Gdx.app.error("SoundManager.getSound", "Sound " + name + " not found, using empty");
				MAP.put(name, null);
			}
		}
		return MAP.get(name);
	}
}