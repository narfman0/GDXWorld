package com.blastedstudios.gdxworld.plugin.quest.trigger.activate;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;

@PluginImplementation
public class ActivateTriggerPlugin implements IQuestComponentTrigger{
	@Override public String getBoxText() {
		return "Activate";
	}

	@Override public Object getDefault() {
		return ActivateTrigger.DEFAULT;
	}

	@Override public Class<? extends Table> getTableClass() {
		return ActivateTriggerTable.class;
	}

	@Override public Class<?> getComponentClass() {
		return ActivateTrigger.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new ActivateTriggerTable(skin, (ActivateTrigger) object);
	}
}