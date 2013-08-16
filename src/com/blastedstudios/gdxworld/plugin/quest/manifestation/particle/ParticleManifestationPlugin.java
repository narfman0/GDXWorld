package com.blastedstudios.gdxworld.plugin.quest.manifestation.particle;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class ParticleManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Particle";
	}

	@Override public ICloneable getDefault() {
		return ParticleManifestation.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new ParticleManifestationTable(skin, (ParticleManifestation) object);
	}
}