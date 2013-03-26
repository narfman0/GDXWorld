package com.blastedstudios.gdxworld.plugin.mode.light.typetable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.light.GDXLight;
import com.blastedstudios.gdxworld.world.light.PointLight;

public class PointLightTable extends AbstractLightTable {
	private TextField distanceField;
	private VertexTable coordinatesTable;

	public PointLightTable(final Skin skin, Color color, int rays, 
			float distance, Vector2 coordinates){
		super(skin, color, rays);
		distanceField = new TextField(distance+"", skin);
		coordinatesTable = new VertexTable(coordinates, skin, null, AbstractLightTable.WIDTH);
		add(new Label("Type: Point", skin));
		init();
		add(new Label("Distance: ", skin));
		add(distanceField).width(AbstractLightTable.WIDTH);
		distanceField.setMessageText("<distance>");
		add(new Label("Coordinates: ", skin));
		add(coordinatesTable);
	}

	@Override public GDXLight create() {
		PointLight light = new PointLight();
		light.setCoordinates(coordinatesTable.getVertex());
		light.setDistance(Float.parseFloat(distanceField.getText()));
		return super.create(light);
	}
}
