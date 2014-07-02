package com.blastedstudios.gdxworld.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Properties {
	private static final String DEFAULT_PROPERTIES = "gdxworld.properties";
	private static java.util.Properties properties;

	static{
		properties = new java.util.Properties();
		if(Gdx.files != null)
			load(FileUtil.find(Gdx.files.internal("data"), DEFAULT_PROPERTIES).read());
		else{
			System.out.println("Failed to load properties, trying manually");
			FileHandle handle = new FileHandle("data");
			if(handle.exists())
				load(FileUtil.find(handle, DEFAULT_PROPERTIES).read());
			else
				System.err.println("Failed to load properties!");
		}
	}
	
	public static void load(InputStream stream){
		try {
			properties.load(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void store(OutputStream stream, String comments){
		try {
			properties.store(stream, comments);
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
	
	public static String get(String key, String defaultVal){
		if(!properties.containsKey(key))
			set(key, defaultVal);
		return properties.getProperty(key);
	}
	
	public static boolean getBool(String key){
		return Boolean.parseBoolean(get(key));
	}
	
	public static boolean getBool(String key, boolean defaultVal){
		return Boolean.parseBoolean(get(key, defaultVal+""));
	}
	
	public static short getShort(String key){
		return Short.parseShort(get(key));
	}
	
	public static short getShort(String key, short defaultVal){
		return Short.parseShort(get(key,defaultVal+""));
	}
	
	public static int getInt(String key){
		return Integer.parseInt(get(key));
	}
	
	public static int getInt(String key, int defaultVal){
		return Integer.parseInt(get(key,defaultVal+""));
	}
	
	public static long getLong(String key){
		return Long.parseLong(get(key));
	}
	
	public static long getLong(String key, long defaultVal){
		return Long.parseLong(get(key,defaultVal+""));
	}

	public static float getFloat(String key) {
		return Float.parseFloat(get(key));
	}

	public static float getFloat(String key, float defaultVal) {
		return Float.parseFloat(get(key,defaultVal+""));
	}
	
	public static Object set(String key, String value){
		return properties.setProperty(key, value);
	}
	
	public static Map<String,String> parseProperties(String key, String defaultValue){
		TreeMap<String,String> propertiesMap = new TreeMap<>();
		String propValue = Properties.get(key, defaultValue);
		String[] properties = propValue.contains(",") ?
				propValue.split(",") : new String[]{propValue};
		for(String property : properties)
			if(!property.trim().equals(""))
				propertiesMap.put(property, "");
		return propertiesMap;
	}
}
