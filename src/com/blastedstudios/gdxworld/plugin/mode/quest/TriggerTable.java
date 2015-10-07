package com.blastedstudios.gdxworld.plugin.mode.quest;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public abstract class TriggerTable extends Table{
	private final TextField nameField;
	
	public TriggerTable(final Skin skin, AbstractQuestTrigger trigger){
		super(skin);
		nameField = new TextField(trigger.getName(), skin);
		add("Name: ");
		add(nameField);
		row();
	}
	
	public void apply(AbstractQuestTrigger trigger){
		trigger.setName(nameField.getText());
	}

	public abstract AbstractQuestTrigger apply();
	public void touched(Vector2 coordinates){}
}
