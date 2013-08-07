package com.blastedstudios.gdxworld.plugin.quest.trigger.objectdistance;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class ObjectDistanceTriggerTable extends TriggerTable {
	private final TextField targetText, distanceText;
	private final ObjectDistanceTrigger trigger;
	private final CheckBox actionRequiredBox;

	public ObjectDistanceTriggerTable(Skin skin, ObjectDistanceTrigger trigger) {
		super(skin);
		this.trigger = trigger;
		targetText = new TextField(trigger.getTarget(), skin);
		targetText.setMessageText("<target text>");
		distanceText = new TextField(trigger.getDistance()+"", skin);
		distanceText.setMessageText("<distance text>");
		actionRequiredBox = new CheckBox("Action Required", skin);
		actionRequiredBox.setChecked(trigger.isActionRequired());
		add(new Label("Target: ", skin));
		add(targetText);
		add(actionRequiredBox);
		row();
		add(new Label("Distance: ", skin));
		add(distanceText);
		row();
	}

	@Override public AbstractQuestTrigger apply() {
		trigger.setTarget(targetText.getText());
		trigger.setDistance(Float.parseFloat(distanceText.getText()));
		trigger.setActionRequired(actionRequiredBox.isChecked());
		return trigger;
	}
}
