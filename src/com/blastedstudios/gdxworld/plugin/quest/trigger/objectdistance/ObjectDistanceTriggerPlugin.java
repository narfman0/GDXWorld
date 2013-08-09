package com.blastedstudios.gdxworld.plugin.quest.trigger.objectdistance;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class ObjectDistanceTriggerPlugin implements IQuestComponentTrigger{
	@Override public String getBoxText() {
		return "Object Distance";
	}

	@Override public ICloneable getDefault() {
		return ObjectDistanceTrigger.DEFAULT;
	}

	@Override public Class<?> getComponentClass() {
		return ObjectDistanceTrigger.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new ObjectDistanceTriggerTable(skin, (ObjectDistanceTrigger) object);
	}
}