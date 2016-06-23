package com.blastedstudios.gdxworld.plugin.quest.manifestation.sound;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

@PluginImplementation
public interface ISoundHandler extends Plugin{
	CompletionEnum sound(float dt, SoundManifestationEnum manifestationType,
			String name, String filename, float volume, float pan, float pitch);
	CompletionEnum tick(float dt);
}
