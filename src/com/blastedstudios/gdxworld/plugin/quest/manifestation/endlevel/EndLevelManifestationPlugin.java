package com.blastedstudios.gdxworld.plugin.quest.manifestation.endlevel;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;

@PluginImplementation
public class EndLevelManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "End Level";
	}

	@Override public Object getDefault() {
		return EndLevelManifestation.DEFAULT;
	}

	@Override public Class<? extends Table> getTableClass() {
		return EndLevelManifestationTable.class;
	}

	@Override public Class<?> getComponentClass() {
		return EndLevelManifestation.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new EndLevelManifestationTable(skin, (EndLevelManifestation) object);
	}
}