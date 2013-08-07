package com.blastedstudios.gdxworld.plugin.quest.trigger.kill;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;
import com.blastedstudios.gdxworld.world.quest.ICloneable;

@PluginImplementation
public class KillTriggerPlugin implements IQuestComponentTrigger{
	@Override public String getBoxText() {
		return "Kill";
	}

	@Override public ICloneable getDefault() {
		return KillTrigger.DEFAULT;
	}

	@Override public Class<? extends Table> getTableClass() {
		return KillTriggerTable.class;
	}

	@Override public Class<?> getComponentClass() {
		return KillTrigger.class;
	}

	@Override public Table createTable(Skin skin, Object object) {
		return new KillTriggerTable(skin, (KillTrigger) object);
	}
}