package com.blastedstudios.gdxworld.plugin.quest.manifestation.inputenable;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

@PluginImplementation
public interface IInputEnableHandler extends Plugin{
	CompletionEnum inputEnable(boolean inputEnable);
}
