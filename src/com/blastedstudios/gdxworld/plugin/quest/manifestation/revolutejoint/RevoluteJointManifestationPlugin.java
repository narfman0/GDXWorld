package com.blastedstudios.gdxworld.plugin.quest.manifestation.revolutejoint;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;

@PluginImplementation
public class RevoluteJointManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Revolute Joint";
	}

	@Override public Object getDefault() {
		return RevoluteJointManifestation.DEFAULT;
	}

	@Override public Class<? extends Table> getTableClass() {
		return RevoluteJointManifestationTable.class;
	}

	@Override public Class<?> getComponentClass() {
		return RevoluteJointManifestation.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new RevoluteJointManifestationTable(skin, (RevoluteJointManifestation) object);
	}
}