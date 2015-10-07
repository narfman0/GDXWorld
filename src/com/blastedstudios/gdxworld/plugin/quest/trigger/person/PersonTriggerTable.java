package com.blastedstudios.gdxworld.plugin.quest.trigger.person;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class PersonTriggerTable extends TriggerTable {
	private final PersonTrigger trigger;
	private final TextField targetText, originText, distanceText;

	public PersonTriggerTable(Skin skin, PersonTrigger trigger) {
		super(skin, trigger);
		this.trigger = trigger;
		originText = new TextField(trigger.getOrigin(), skin);
		originText.setMessageText("<origin text>");
		targetText = new TextField(trigger.getTarget(), skin);
		targetText.setMessageText("<target text>");
		distanceText = new TextField(trigger.getDistance()+"", skin);
		distanceText.setMessageText("<distance text>");
		add("Origin: ");
		add(originText);
		row();
		add("Target: ");
		add(targetText);
		row();
		add("Distance: ");
		add(distanceText);
		row();
	}

	@Override public AbstractQuestTrigger apply() {
		super.apply(trigger);
		trigger.setOrigin(originText.getText());
		trigger.setTarget(targetText.getText());
		trigger.setDistance(Float.parseFloat(distanceText.getText()));
		return trigger;
	}

}
