package com.blastedstudios.gdxworld.ui.leveleditor.mode.light.typetable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.folder.GDXLight;
import com.blastedstudios.gdxworld.world.folder.PointLight;

public class PointLightTable extends AbstractLightTable {
	private TextField distanceField;
	private VertexTable coordinatesTable;

	public PointLightTable(final Skin skin, Color color, int rays, 
			float distance, Vector2 coordinates){
		super(skin, color, rays);
		add(new Label("Type: Point", skin));
		init();
		add(new Label("Distance: ", skin));
		add(distanceField = new TextField(distance+"", skin));
		distanceField.setMessageText("<distance>");
		add(coordinatesTable = new VertexTable(coordinates, skin, null));
	}

	@Override public GDXLight create() {
		PointLight light = new PointLight();
		light.setCoordinates(coordinatesTable.getVertex());
		light.setDistance(Float.parseFloat(distanceField.getText()));
		return super.create(light);
	}
}
