package com.blastedstudios.gdxworld.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

/**
 * Asset manager wrapper to also provide option of getting assets by unique
 * name. Has convenience methods specifically for sound and texture.
 * 
 * TODO This is a semi-dirty workaround for not using full paths on save.
 * Should make the ui such that a user can configure asset paths, then as one
 * is typing, suggestions pop up matching the name. Then it can use the
 * relative path as saved in the preferences. Then we can remove this class. 
 * 
 * Check note in GDXLevel.createAssetList as well
 */
public class AssetManagerWrapper extends AssetManager {
	private final HashMap<String, String> namePathMap = new HashMap<String, String>();

	public <T> T getAsset(String asset, Class<T> type){
		String path = namePathMap.get(asset);
		return get(path == null ? asset : path, type);
	}
	
	public <T> void loadAsset(String name, Class<T> type){
		FileHandle handle = FileUtil.find(name);
		if(handle == null || !handle.exists()){
			Gdx.app.error("AssetManagerWrapper.loadAsset", "Asset not found: " + name);
			return;
		}
		String path = handle.path();
		namePathMap.put(name, path);
		load(path, type);
	}
	
	@Override public synchronized boolean isLoaded (String name) {
		if(namePathMap.containsKey(name))
			name = namePathMap.get(name);
		return super.isLoaded(name);
	}

	public Texture getTexture(String name){
		return getAsset(name, Texture.class);
	}
	
	public void loadTexture(String name){
		loadAsset(name, Texture.class);
	}
	
	public Sound getSound(String name){
		return getAsset(name, Sound.class);
	}
	
	public void loadSound(String name){
		loadAsset(name, Sound.class);
	}
	
	@Override public void dispose(){
		namePathMap.clear();
		super.dispose();
	}
}
