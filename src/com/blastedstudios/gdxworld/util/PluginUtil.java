package com.blastedstudios.gdxworld.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import net.xeoh.plugins.base.util.uri.ClassURI;

import com.badlogic.gdx.Gdx;

public class PluginUtil {
	private static final PluginManagerUtil pluginManager;
	
	static{
		PluginManager pm = PluginManagerFactory.createPluginManager();
		pm.addPluginsFrom(ClassURI.CLASSPATH);
		pluginManager = new PluginManagerUtil(pm);
	}
	
	public static <T extends Plugin> Collection<T> getPlugins(Class<T> theInterface){
		Collection<T> plugins = pluginManager.getPlugins(theInterface);
		for(T plugin : plugins)
			Gdx.app.debug("PluginUtil.getPlugins", "Retrieved: " + plugin.getClass().getCanonicalName());
		return plugins;
	}
	
	/**
	 * @return list of plugins sorted according to dependency. If dependency
	 * not satisfied, does not include plugin
	 */
	public static <T extends Plugin> List<T> getPluginsSorted(Class<T> theInterface){
		List<T> activePlugins = new ArrayList<>();
		boolean changed = false;
		List<? extends Plugin> inactivePlugins = new ArrayList<>(pluginManager.getPlugins());
		for(int i=0; i<inactivePlugins.size(); i++){
			boolean dependenciesSatisfied = true;
			Class<? extends Plugin> clazz = inactivePlugins.get(i).getClass();
			if(clazz.isAnnotationPresent(Dependency.class)){
				for(Class<? extends Plugin> dependency : clazz.getAnnotation(Dependency.class).classes()){
					boolean found = activePlugins.contains(dependency);
					for(T active : activePlugins)
						if(active.getClass() == dependency)
							found = true;
					if(!found)
						dependenciesSatisfied = false;
				}
			}
			if(dependenciesSatisfied){
				Plugin plugin = inactivePlugins.remove(i);
				if(theInterface.isInstance(plugin))
					activePlugins.add(theInterface.cast(plugin));
				changed = true;
			}
			if(inactivePlugins.size() == i)
				if(!changed)
					break;
				else{
					i = 0;
					changed = false;
				}
			if(dependenciesSatisfied)
				i--;
		}
		return activePlugins;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Dependency {
		Class<? extends Plugin>[] classes();
	}
}
