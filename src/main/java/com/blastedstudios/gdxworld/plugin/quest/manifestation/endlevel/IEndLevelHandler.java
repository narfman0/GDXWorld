package com.blastedstudios.gdxworld.plugin.quest.manifestation.endlevel;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

@PluginImplementation
public interface IEndLevelHandler extends Plugin{
	CompletionEnum endLevel(boolean success, String nextLevel);
}
