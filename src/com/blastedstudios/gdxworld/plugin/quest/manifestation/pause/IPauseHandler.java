package com.blastedstudios.gdxworld.plugin.quest.manifestation.pause;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

@PluginImplementation
public interface IPauseHandler extends Plugin{
	CompletionEnum pause(boolean pause);
}
