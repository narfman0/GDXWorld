package com.blastedstudios.gdxworld.plugin.quest.manifestation.particle;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public interface IParticleHandler extends Plugin{
	CompletionEnum particle(String name, String effectFile, String imagesDir,
			int duration, Vector2 position,
			ParticleManifestationTypeEnum modificationType, String emitterName, 
			String attachedBody);
}
