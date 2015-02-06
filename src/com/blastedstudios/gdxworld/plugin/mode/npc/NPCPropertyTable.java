package com.blastedstudios.gdxworld.plugin.mode.npc;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.GDXNPC;

public class NPCPropertyTable extends Table {
	private final HashMap<String, TextField> properties = new HashMap<>();
	private final GDXNPC npc;

	public NPCPropertyTable(final Skin skin, final GDXNPC npc){
		super(skin);
		this.npc = npc;
		for(Entry<String,String> entry : npc.getProperties().entrySet()){
			add(entry.getKey() + ": ");
			final TextField propertyField = new TextField("", skin);
			propertyField.setMessageText("<" + entry.getKey() + ">");
			if(entry.getValue() != null)
				propertyField.setText(entry.getValue());
			properties.put(entry.getKey(), propertyField);
			add(propertyField);
			row();
		}
	}
	
	public void apply(){
		for(String key : npc.getProperties().keySet())
			npc.getProperties().put(key, properties.get(key).getText());
	}
}
