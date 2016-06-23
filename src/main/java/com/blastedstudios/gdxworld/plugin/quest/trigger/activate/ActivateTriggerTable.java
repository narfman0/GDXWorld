package com.blastedstudios.gdxworld.plugin.quest.trigger.activate;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class ActivateTriggerTable extends TriggerTable {
	private final ActivateTrigger trigger;
	
	public ActivateTriggerTable(Skin skin, ActivateTrigger trigger) {
		super(skin, trigger);
		this.trigger = trigger;
	}

	@Override public AbstractQuestTrigger apply() {
		super.apply(trigger);
		return trigger;
	}
}
