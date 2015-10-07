package com.blastedstudios.gdxworld.plugin.quest.trigger.time;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class TimeTriggerTable extends TriggerTable {
	private final TimeTrigger trigger;
	private final TextField timeText;

	public TimeTriggerTable(Skin skin, TimeTrigger trigger) {
		super(skin, trigger);
		this.trigger = trigger;
		timeText = new TextField(trigger.getTime()+"", skin);
		timeText.setMessageText("<time in ms>");
		add("Time (ms): ");
		add(timeText);
	}

	@Override public AbstractQuestTrigger apply() {
		super.apply(trigger);
		trigger.setTime(Long.parseLong(timeText.getText()));
		return trigger;
	}
}
