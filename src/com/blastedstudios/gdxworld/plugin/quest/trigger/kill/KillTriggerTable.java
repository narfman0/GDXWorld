package com.blastedstudios.gdxworld.plugin.quest.trigger.kill;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class KillTriggerTable extends TriggerTable{
	private final KillTrigger trigger;
	private final TextField nameField;
	
	public KillTriggerTable(Skin skin, KillTrigger trigger) {
		super(skin, trigger);
		this.trigger = trigger;
		nameField = new TextField(trigger.getName(), skin);
		nameField.setMessageText("<dialog text>");
		add("Name: ");
		add(nameField);
	}

	@Override public AbstractQuestTrigger apply() {
		super.apply(trigger);
		trigger.setName(nameField.getText());
		return trigger;
	}
}