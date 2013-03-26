package com.blastedstudios.gdxworld.util;

import java.util.Collection;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import net.xeoh.plugins.base.util.uri.ClassURI;

import com.badlogic.gdx.Gdx;

public class PluginUtil {
	public static <T extends Plugin> Collection<T> getPlugins(Class<T> theInterface){
		PluginManager pm = PluginManagerFactory.createPluginManager();
		pm.addPluginsFrom(ClassURI.CLASSPATH);
		PluginManagerUtil pluginManager = new PluginManagerUtil(pm);
		Collection<T> plugins = pluginManager.getPlugins(theInterface);
		for(T plugin : plugins)
			Gdx.app.debug("PluginUtil.getPlugins", "Retrieved: " + plugin.getClass().getCanonicalName());
		return plugins;
	}
}
