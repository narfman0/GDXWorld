package com.blastedstudios.gdxworld.plugin.quest.trigger.objectdistance;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public interface IObjectDistanceTriggerHandler extends Plugin{
	boolean activate(ObjectDistanceTrigger trigger);
}
