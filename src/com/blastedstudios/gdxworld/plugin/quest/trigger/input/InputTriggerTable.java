package com.blastedstudios.gdxworld.plugin.quest.trigger.input;

import java.lang.reflect.Field;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class InputTriggerTable extends TriggerTable {
	private final TextField inputText;
	
	public InputTriggerTable(Skin skin, InputTrigger trigger) {
		super(skin);
		inputText = new TextField(fromKey(trigger.getInput()), skin);
		inputText.setMessageText("<input key (e.g. Keys.W)>");
		add(new Label("Input key: ", skin));
		add(inputText);
	}

	@Override public AbstractQuestTrigger apply() {
		return new InputTrigger(toKey(inputText.getText()));
	}
	
	private static int toKey(String key){
		try{
			return Integer.parseInt(key);
		}catch(Exception e){
			try {
				return (Integer)Keys.class.getField(key).get(Keys.class);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return 0;
	}

	private String fromKey(int key){
		try{
			for(Field field : Keys.class.getFields())
				if(field.get(Keys.class).equals(((Integer)key)))
					return field.getName();
		}catch(Exception e){}
		return key+"";
	}
}
