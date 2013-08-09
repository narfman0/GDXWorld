package com.blastedstudios.gdxworld.plugin.quest.manifestation.prismaticjoint;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class PrismaticJointManifestationPlugin implements IQuestComponentManifestation{
	@Override public String getBoxText() {
		return "Prismatic Joint";
	}

	@Override public ICloneable getDefault() {
		return PrismaticJointManifestation.DEFAULT;
	}

	@Override public Class<?> getComponentClass() {
		return PrismaticJointManifestation.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new PrismaticJointManifestationTable(skin, (PrismaticJointManifestation) object);
	}
}