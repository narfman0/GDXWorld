package com.blastedstudios.gdxworld.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import com.badlogic.gdx.Gdx;

public class PluginUtil {
	private static PluginManagerUtil pluginManager;
	
	/**
	 * Initialize pluginManager for the util, should be calld firstest
	 * @param pluginOrigin URIs representing the plugins. Suggest using
	 * ClassURI.CLASSPATH for desktop, android will have to list all the
	 * plugins manually since they are lame. 
	 */
	public static void initialize(URI... pluginOrigin){
		PluginManager pm = PluginManagerFactory.createPluginManager();
		for(URI uri : pluginOrigin)
			pm.addPluginsFrom(uri);
		pluginManager = new PluginManagerUtil(pm);
	}
	
	public static <T extends Plugin> Collection<T> getPlugins(Class<T> theInterface){
		List<T> plugins = new LinkedList<>(pluginManager.getPlugins(theInterface));
		Collections.sort(plugins, new Sorter<T>());
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
		Collections.sort(activePlugins, new Sorter<T>());
		return activePlugins;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Dependency {
		Class<? extends Plugin>[] classes();
	}
	
	private static class Sorter<T> implements Comparator<T>{
		@Override public int compare(T o1, T o2) {
			return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
		}
	}
}
