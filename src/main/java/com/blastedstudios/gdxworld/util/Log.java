package com.blastedstudios.gdxworld.util;

import com.badlogic.gdx.Gdx;

import net.xeoh.plugins.base.Plugin;

/**
 * Log wrapper, so as to hijack any logs if we desire. I want to utilize java
 * logging support to go to file or elsewhere, thus will still handle the gdx
 * way but also want to handle other ways.
 */
public class Log {
	public static void debug(String tag, String message){
		Gdx.app.debug(tag, message);
		for(ILogHandler handler : PluginUtil.getPlugins(ILogHandler.class))
			handler.debug(tag, message);
	}
	
	public static void log(String tag, String message){
		Gdx.app.log(tag, message);
		for(ILogHandler handler : PluginUtil.getPlugins(ILogHandler.class))
			handler.log(tag, message);
	}
	
	public static void error(String tag, String message){
		Gdx.app.error(tag, message);
		for(ILogHandler handler : PluginUtil.getPlugins(ILogHandler.class))
			handler.error(tag, message);
	}
	
	public interface ILogHandler extends Plugin{
		void debug(String tag, String message);
		void error(String tag, String message);
		void log(String tag, String message);
	}
}
