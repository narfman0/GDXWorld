package com.blastedstudios.gdxworld.ui.leveleditor.mode.quest.trigger;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.PersonTrigger;

public class PersonTriggerTable extends TriggerTable {
	private final TextField targetText, originText, distanceText;
	private final PersonTrigger trigger;

	public PersonTriggerTable(Skin skin, PersonTrigger trigger) {
		super(skin);
		this.trigger = trigger;
		originText = new TextField(trigger.getOrigin(), skin);
		originText.setMessageText("<origin text>");
		targetText = new TextField(trigger.getTarget(), skin);
		targetText.setMessageText("<target text>");
		distanceText = new TextField(trigger.getDistance()+"", skin);
		distanceText.setMessageText("<distance text>");
		add(new Label("Origin: ", skin));
		add(originText);
		row();
		add(new Label("Target: ", skin));
		add(targetText);
		row();
		add(new Label("Distance: ", skin));
		add(distanceText);
		row();
	}

	@Override public AbstractQuestTrigger apply() {
		trigger.setOrigin(originText.getText());
		trigger.setTarget(targetText.getText());
		trigger.setDistance(Float.parseFloat(distanceText.getText()));
		return trigger;
	}

}
