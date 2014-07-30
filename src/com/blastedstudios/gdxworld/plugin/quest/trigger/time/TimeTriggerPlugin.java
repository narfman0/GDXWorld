package com.blastedstudios.gdxworld.plugin.quest.trigger.time;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class TimeTriggerPlugin implements IQuestComponentTrigger{
	@Override public String getBoxText() {
		return "Time";
	}

	@Override public ICloneable getDefault() {
		return TimeTrigger.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new TimeTriggerTable(skin, (TimeTrigger) object);
	}
}