package com.blastedstudios.gdxworld.ui.worldeditor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.GDXGame;

public class PropertiesWindow extends AbstractWindow {
	public PropertiesWindow(GDXGame game, Skin skin, final HashMap<String,String> propertyMap) {
		super("Properties Window", skin);
		final LinkedList<PropertyTable> propertyTables = new LinkedList<>();
		for(Entry<String,String> entry : propertyMap.entrySet()){
			PropertyTable table = new PropertyTable(entry.getKey(), entry.getValue(), skin);
			propertyTables.add(table);
			add(table);
			row();
		}
		final Button updateButton = new TextButton("Update", skin);
		updateButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				for(PropertyTable table : propertyTables)
					propertyMap.put(table.propertyName, table.propertyField.getText());
			}
		});
		add(updateButton).colspan(2);
		pack();
		setX(Gdx.graphics.getWidth() - getWidth());
		setY(Gdx.graphics.getHeight() - getHeight());
		setMovable(false);
	}
	
	private static class PropertyTable extends Table{
		final String propertyName;
		final TextField propertyField;
		
		PropertyTable(String propertyName, String propertyValue, Skin skin){
			this.propertyName = propertyName;
			propertyField = new TextField(propertyValue, skin);
			add(new Label(propertyName, skin));
			add(propertyField);
		}
	}
}
