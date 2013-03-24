package com.blastedstudios.gdxworld.util;

import com.badlogic.gdx.Gdx;

public class Properties extends java.util.Properties{
	private static final long serialVersionUID = 1L;
	private static java.util.Properties properties;

	static{
		properties = new java.util.Properties();
		try {
			properties.load(FileUtil.find(Gdx.files.internal("data"),"gdxworld.properties").read());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean contains(String key){
		return properties.containsKey(key);
	}
	
	public static String get(String key){
		return properties.getProperty(key);
	}
	
	public static boolean getBool(String key){
		return Boolean.parseBoolean(get(key));
	}
	
	public static int getInt(String key){
		return Integer.parseInt(get(key));
	}

	public static float getFloat(String key) {
		return Float.parseFloat(get(key));
	}
	
	public static Object set(String key, String value){
		return properties.setProperty(key, value);
	}
}
