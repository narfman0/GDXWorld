package com.blastedstudios.gdxworld.plugin.quest.manifestation.dialog;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class DialogManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Dialog";
	}

	@Override public ICloneable getDefault() {
		return DialogManifestation.DEFAULT;
	}

	@Override public Class<?> getComponentClass() {
		return DialogManifestation.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new DialogManifestationTable(skin, (DialogManifestation) object);
	}
}
