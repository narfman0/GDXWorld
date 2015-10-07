package com.blastedstudios.gdxworld.plugin.quest.trigger.objectdistance;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class ObjectDistanceTriggerTable extends TriggerTable {
	private final ObjectDistanceTrigger trigger;
	private final TextField targetText, originText, distanceText;
	private final CheckBox actionRequiredBox;

	public ObjectDistanceTriggerTable(Skin skin, ObjectDistanceTrigger trigger) {
		super(skin, trigger);
		this.trigger = trigger;
		targetText = new TextField(trigger.getTarget(), skin);
		targetText.setMessageText("<target>");
		originText = new TextField(trigger.getOrigin(), skin);
		originText.setMessageText("<origin>");
		distanceText = new TextField(trigger.getDistance()+"", skin);
		distanceText.setMessageText("<distance>");
		actionRequiredBox = new CheckBox("Action Required", skin);
		actionRequiredBox.setChecked(trigger.isActionRequired());
		add("Origin: ");
		add(originText);
		row();
		add("Target: ");
		add(targetText);
		row();
		add("Distance: ");
		add(distanceText);
		row();
		add(actionRequiredBox);
	}

	@Override public AbstractQuestTrigger apply() {
		super.apply(trigger);
		trigger.setTarget(targetText.getText());
		trigger.setDistance(Float.parseFloat(distanceText.getText()));
		trigger.setActionRequired(actionRequiredBox.isChecked());
		trigger.setOrigin(originText.getText());
		return trigger;
	}
}
