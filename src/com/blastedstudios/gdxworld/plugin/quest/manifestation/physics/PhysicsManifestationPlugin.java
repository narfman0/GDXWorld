package com.blastedstudios.gdxworld.plugin.quest.manifestation.physics;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class PhysicsManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Physics";
	}

	@Override public ICloneable getDefault() {
		return PhysicsManifestation.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new PhysicsManifestationTable(skin, (PhysicsManifestation) object);
	}
}