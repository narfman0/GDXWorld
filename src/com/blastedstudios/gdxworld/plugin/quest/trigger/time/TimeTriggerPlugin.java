package com.blastedstudios.gdxworld.plugin.quest.trigger.time;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class TimeTriggerPlugin implements IQuestComponentTrigger{
	@Override public String getBoxText() {
		return "Time";
	}

	@Override public Object getDefault() {
		return TimeTrigger.DEFAULT;
	}

	@Override public Class<? extends Table> getTableClass() {
		return TimeTriggerTable.class;
	}

	@Override public Class<?> getComponentClass() {
		return TimeTrigger.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new TimeTriggerTable(skin, (TimeTrigger) object);
	}
}