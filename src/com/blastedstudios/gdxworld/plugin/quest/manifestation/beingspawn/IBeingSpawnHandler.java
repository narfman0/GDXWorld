package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.world.GDXNPC;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

@PluginImplementation
public interface IBeingSpawnHandler extends Plugin{
	CompletionEnum beingSpawn(GDXNPC npc);
}
