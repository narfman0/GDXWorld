package com.blastedstudios.gdxworld.plugin.mode.polygon;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.FilterTable;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class ShapeTable extends Table {
	private final TextField densityField, frictionField, restitutionField, resourceField;
	private final FilterTable filterTable;

	public ShapeTable(Skin skin, GDXShape shape){
		resourceField = new TextField(shape.getResource(), skin);
		resourceField.setMessageText("<resource name>");
		densityField = new TextField(shape.getDensity()+"", skin);
		densityField.setMessageText("<density, calculates mass>");
		frictionField = new TextField(shape.getFriction()+"", skin);
		frictionField.setMessageText("<friction>");
		restitutionField = new TextField(shape.getRestitution()+"", skin);
		restitutionField.setMessageText("<restitution>");
		filterTable = new FilterTable(skin, shape.getFilter());

		add(new Label("Resource: ", skin));
		add(resourceField);
		row();
		add(new Label("Friction: ", skin));
		add(frictionField);
		row();
		add(new Label("Density: ", skin));
		add(densityField);
		row();
		add(new Label("Restitution: ", skin));
		add(restitutionField);
		row();
		add(new Label("Filter ", skin));
		add(filterTable);
	}
	
	public GDXShape apply(GDXShape shape){
		shape.setDensity(Float.parseFloat(densityField.getText()));
		shape.setFriction(Float.parseFloat(frictionField.getText()));
		shape.setResource(resourceField.getText());
		shape.setRestitution(Float.parseFloat(restitutionField.getText()));
		shape.setFilter(filterTable.createFilterFromUI());
		return shape;
	}
}
