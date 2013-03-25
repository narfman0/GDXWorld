package com.blastedstudios.gdxworld.ui.leveleditor.mode.light.typetable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.folder.ConeLight;
import com.blastedstudios.gdxworld.world.folder.GDXLight;

public class ConeLightTable extends AbstractLightTable {
	private TextField distanceField, coneDegreeField, directionDegreeField;
	private VertexTable coordinatesTable;
	
	public ConeLightTable(final Skin skin, Color color, int rays, 
			float distance, Vector2 coordinates, float coneDegree,
			float directionDegree){
		super(skin, color, rays);
		add(new Label("Type: Cone", skin));
		init();
		add(new Label("Distance: ", skin));
		add(distanceField = new TextField(distance+"", skin));
		distanceField.setMessageText("<distance>");
		add(new Label("Cone Degree: ", skin));
		add(coneDegreeField = new TextField(coneDegree+"", skin));
		coneDegreeField.setMessageText("<cone degree>");
		add(new Label("Direction Degree: ", skin));
		add(directionDegreeField = new TextField(directionDegree+"", skin));
		directionDegreeField.setMessageText("<direction degree>");
		add(coordinatesTable = new VertexTable(coordinates, skin, null));
	}

	@Override public GDXLight create() {
		ConeLight light = new ConeLight();
		light.setConeDegree(Float.parseFloat(coneDegreeField.getText()));
		light.setCoordinates(coordinatesTable.getVertex());
		light.setDirectionDegree(Float.parseFloat(directionDegreeField.getText()));
		light.setDistance(Float.parseFloat(distanceField.getText()));
		return super.create(light);
	}

}
