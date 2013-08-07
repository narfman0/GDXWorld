package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class BeingSpawnManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Being Spawn";
	}

	@Override public ICloneable getDefault() {
		return BeingSpawnManifestation.DEFAULT;
	}

	@Override public Class<? extends Table> getTableClass() {
		return BeingSpawnManifestationTable.class;
	}

	@Override public Class<?> getComponentClass() {
		return BeingSpawnManifestation.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new BeingSpawnManifestationTable(skin, (BeingSpawnManifestation) object);
	}
}
