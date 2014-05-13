package com.blastedstudios.gdxworld.plugin.quest.manifestation.jointremove;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class JointRemoveManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Joint Remove";
	}

	@Override public ICloneable getDefault() {
		return JointRemoveManifestation.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new JointRemoveManifestationTable(skin, (JointRemoveManifestation) object);
	}
}
