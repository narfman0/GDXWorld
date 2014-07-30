package com.blastedstudios.gdxworld.plugin.quest.manifestation.revolutejoint;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class RevoluteJointManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Revolute Joint";
	}

	@Override public ICloneable getDefault() {
		return RevoluteJointManifestation.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new RevoluteJointManifestationTable(skin, (RevoluteJointManifestation) object);
	}
}