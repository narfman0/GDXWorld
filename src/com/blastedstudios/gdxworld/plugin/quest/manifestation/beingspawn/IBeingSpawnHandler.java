package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public interface IBeingSpawnHandler extends Plugin{
	CompletionEnum beingSpawn(String being, Vector2 coordinates, String path);
}
