package com.blastedstudios.gdxworld.plugin.quest.trigger.input;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;

@PluginImplementation
public class InputTriggerPlugin implements IQuestComponentTrigger{
	@Override public String getBoxText() {
		return "Input";
	}

	@Override public Object getDefault() {
		return InputTrigger.DEFAULT;
	}

	@Override public Class<? extends Table> getTableClass() {
		return InputTriggerTable.class;
	}

	@Override public Class<?> getComponentClass() {
		return InputTrigger.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new InputTriggerTable(skin, (InputTrigger) object);
	}
}