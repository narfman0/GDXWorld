package com.blastedstudios.gdxworld.plugin.mode.quest.trigger;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.ObjectDistanceTrigger;

public class ObjectDistanceTriggerTable extends TriggerTable {
	public static final String BOX_TEXT = "Object Distance";
	private final TextField targetText, distanceText;
	private final ObjectDistanceTrigger trigger;

	public ObjectDistanceTriggerTable(Skin skin, ObjectDistanceTrigger trigger) {
		super(skin);
		this.trigger = trigger;
		targetText = new TextField(trigger.getTarget(), skin);
		targetText.setMessageText("<target text>");
		distanceText = new TextField(trigger.getDistance()+"", skin);
		distanceText.setMessageText("<distance text>");
		add(new Label("Target: ", skin));
		add(targetText);
		row();
		add(new Label("Distance: ", skin));
		add(distanceText);
		row();
	}

	@Override public AbstractQuestTrigger apply() {
		trigger.setTarget(targetText.getText());
		trigger.setDistance(Float.parseFloat(distanceText.getText()));
		return trigger;
	}
}
