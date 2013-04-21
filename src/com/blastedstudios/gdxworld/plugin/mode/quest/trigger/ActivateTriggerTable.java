package com.blastedstudios.gdxworld.plugin.mode.quest.trigger;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.ActivateTrigger;

public class ActivateTriggerTable extends TriggerTable {
	public ActivateTriggerTable(Skin skin, ActivateTrigger trigger) {
		super(skin);
	}

	@Override public AbstractQuestTrigger apply() {
		return new ActivateTrigger();
	}
}
