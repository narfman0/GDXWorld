package com.blastedstudios.gdxworld.plugin.mode.light.typetable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.light.ConeLight;
import com.blastedstudios.gdxworld.world.light.GDXLight;

public class ConeLightTable extends AbstractLightTable {
	private TextField distanceField, coneDegreeField, directionDegreeField;
	private VertexTable coordinatesTable;
	
	public ConeLightTable(final Skin skin, Color color, int rays, 
			float distance, Vector2 coordinates, float coneDegree,
			float directionDegree){
		super(skin, color, rays);
		directionDegreeField = new TextField(directionDegree+"", skin);
		coneDegreeField = new TextField(coneDegree+"", skin);
		distanceField = new TextField(distance+"", skin);
		coordinatesTable = new VertexTable(coordinates, skin, null, AbstractLightTable.WIDTH);
		add(new Label("Type:Cone", skin));
		init();
		add(new Label("Distance:", skin));
		add(distanceField).width(WIDTH);
		distanceField.setMessageText("<distance>");
		add(new Label("Cone Degree:", skin));
		add(coneDegreeField).width(WIDTH);
		coneDegreeField.setMessageText("<cone degree>");
		add(new Label("Direction Degree:", skin));
		add(directionDegreeField).width(WIDTH);
		directionDegreeField.setMessageText("<direction degree>");
		add(new Label("Coordinates:", skin));
		add(coordinatesTable);
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
