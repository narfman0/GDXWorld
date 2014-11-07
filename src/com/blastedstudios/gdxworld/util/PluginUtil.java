package com.blastedstudios.gdxworld.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

public class PluginUtil {
	private static PluginManagerUtil pluginManager;
	// used to cache results. This was crawling to a halt in 32 bit, with 
	// top 6 CPU dominators as plugins related code
	private static HashMap<Object, Object> interfacePluginListMap = new HashMap<>();
	
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
	
	@SuppressWarnings("unchecked")
	public static <T extends Plugin> Collection<T> getPlugins(Class<T> theInterface){
		Collection<T> plugins = (Collection<T>) interfacePluginListMap.get(theInterface);
		if(plugins == null){
			LinkedList<T> pluginsList = new LinkedList<>(pluginManager.getPlugins(theInterface));
			Collections.sort(pluginsList, new Sorter<T>());
			interfacePluginListMap.put(theInterface, plugins = pluginsList);
		}
		return plugins;
	}
	
	/**
	 * @return list of plugins sorted according to dependency. If dependency
	 * not satisfied, does not include plugin
	 */
	public static <T extends Plugin> List<T> getPluginsSorted(Class<T> theInterface){
		LinkedList<T> activePlugins = new LinkedList<>();
		boolean changed = false;
		LinkedList<? extends Plugin> inactivePlugins = new LinkedList<>(pluginManager.getPlugins());
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
