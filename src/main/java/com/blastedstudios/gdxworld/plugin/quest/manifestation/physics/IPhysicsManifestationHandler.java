package com.blastedstudios.gdxworld.plugin.quest.manifestation.physics;

import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public interface IPhysicsManifestationHandler extends Plugin{
	CompletionEnum execute(PhysicsManifestation manifestation);
}
