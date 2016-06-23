package com.blastedstudios.gdxworld.plugin.quest.manifestation.groupcreate;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class GroupCreateManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Group Create";
	}

	@Override public ICloneable getDefault() {
		return GroupCreateManifestation.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new GroupCreateManifestationTable(skin, (GroupCreateManifestation) object);
	}
}
