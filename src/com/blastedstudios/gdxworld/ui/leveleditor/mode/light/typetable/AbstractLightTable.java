package com.blastedstudios.gdxworld.ui.leveleditor.mode.light.typetable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.light.ColorTable;
import com.blastedstudios.gdxworld.world.light.GDXLight;

public abstract class AbstractLightTable extends Table {
	public static int WIDTH = 50;
	private final Skin skin;
	protected final ColorTable colorTable;
	protected final TextField raysField;
	
	public AbstractLightTable(Skin skin, Color color, int rays){
		this.skin = skin;
		colorTable = new ColorTable(color, skin);
		raysField = new TextField(rays+"", skin);
		raysField.setMessageText("<ray count>");
	}
	
	protected void init(){
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
}
