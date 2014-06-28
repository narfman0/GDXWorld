package com.blastedstudios.gdxworld.plugin.quest.manifestation.inputenable;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class InputEnableManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Input Enable";
	}

	@Override public ICloneable getDefault() {
		return InputEnableManifestation.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new InputEnableManifestationTable(skin, (InputEnableManifestation) object);
	}
}
