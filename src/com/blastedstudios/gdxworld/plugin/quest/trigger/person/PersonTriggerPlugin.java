package com.blastedstudios.gdxworld.plugin.quest.trigger.person;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class PersonTriggerPlugin implements IQuestComponentTrigger{
	@Override public String getBoxText() {
		return "Person";
	}

	@Override public ICloneable getDefault() {
		return PersonTrigger.DEFAULT;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new PersonTriggerTable(skin, (PersonTrigger) object);
	}
}