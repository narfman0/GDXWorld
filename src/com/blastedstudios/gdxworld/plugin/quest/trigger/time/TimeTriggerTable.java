package com.blastedstudios.gdxworld.plugin.quest.trigger.time;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class TimeTriggerTable extends TriggerTable {
	private final TextField timeText;
	private final TimeTrigger trigger;

	public TimeTriggerTable(Skin skin, TimeTrigger trigger) {
		super(skin);
		this.trigger = trigger;
		timeText = new TextField(trigger.getTime()+"", skin);
		timeText.setMessageText("<time in ms>");
		add(new Label("Time (ms): ", skin));
		add(timeText);
	}

	@Override public AbstractQuestTrigger apply() {
		trigger.setTime(Long.parseLong(timeText.getText()));
		return trigger;
	}

}
