package com.blastedstudios.gdxworld.plugin.mode.light.typetable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.light.ColorTable;
import com.blastedstudios.gdxworld.plugin.mode.light.LightWindow;
import com.blastedstudios.gdxworld.world.light.GDXLight;

public abstract class AbstractLightTable extends Table {
	public static int WIDTH = 50;
	private final Skin skin;
	protected final ColorTable colorTable;
	protected final TextField raysField;
	protected final Button selectButton;
	
	public AbstractLightTable(Skin skin, final LightWindow lightWindow, 
			Color color, int rays){
		this.skin = skin;
		colorTable = new ColorTable(color, skin);
		raysField = new TextField(rays+"", skin);
		raysField.setMessageText("<ray count>");
		final AbstractLightTable selected = this;
		selectButton = new TextButton("Select", skin);
		selectButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				lightWindow.setSelected(selected);
			}
		});
	}
	
	protected void init(){
		add(selectButton);
		add(new Label("Color: ", skin));
		add(colorTable);
		add(new Label("Rays: ", skin));
		add(raysField).width(WIDTH);
	}
	
	public abstract GDXLight create();
	
	protected GDXLight create(GDXLight light){
		light.setColor(GDXLight.convert(colorTable.parseColor()));
		light.setRays(Integer.parseInt(raysField.getText()));
		return light;
	}

	public abstract void setCoordinates(float x, float y);
}
