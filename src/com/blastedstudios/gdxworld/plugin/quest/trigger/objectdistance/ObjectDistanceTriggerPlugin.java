package com.blastedstudios.gdxworld.plugin.quest.trigger.objectdistance;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;

@PluginImplementation
public class ObjectDistanceTriggerPlugin implements IQuestComponentTrigger{
	@Override public String getBoxText() {
		return "Object Distance";
	}

	@Override public Object getDefault() {
		return ObjectDistanceTrigger.DEFAULT;
	}

	@Override public Class<? extends Table> getTableClass() {
		return ObjectDistanceTriggerTable.class;
	}

	@Override public Class<?> getComponentClass() {
		return ObjectDistanceTrigger.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new ObjectDistanceTriggerTable(skin, (ObjectDistanceTrigger) object);
	}
}