package com.blastedstudios.gdxworld.plugin.quest.trigger.activate;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class ActivateTriggerTable extends TriggerTable {
	public ActivateTriggerTable(Skin skin, ActivateTrigger trigger) {
		super(skin);
	}

	@Override public AbstractQuestTrigger apply() {
		return new ActivateTrigger();
	}
}
