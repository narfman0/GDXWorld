package com.blastedstudios.gdxworld.plugin.mode.polygon;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.FilterTable;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class ShapeTable extends Table {
	private final TextField densityField, frictionField, restitutionField, resourceField, tagField;
	private final FilterTable filterTable;

	public ShapeTable(Skin skin, GDXShape shape){
		super(skin);
		resourceField = new TextField(shape.getResource(), skin);
		resourceField.setMessageText("<resource name>");
		densityField = new TextField(shape.getDensity()+"", skin);
		densityField.setMessageText("<density, calculates mass>");
		frictionField = new TextField(shape.getFriction()+"", skin);
		frictionField.setMessageText("<friction>");
		restitutionField = new TextField(shape.getRestitution()+"", skin);
		restitutionField.setMessageText("<restitution>");
		tagField = new TextField(shape.getTag(), skin);
		tagField.setMessageText("<tag>");
		filterTable = new FilterTable(skin, shape.getFilter());

		add("Resource: ");
		add(resourceField);
		row();
		add("Tag: ");
		add(tagField);
		row();
		add("Friction: ");
		add(frictionField);
		row();
		add("Density: ");
		add(densityField);
		row();
		add("Restitution: ");
		add(restitutionField);
		row();
		add("Filter ");
		add(filterTable);
	}
	
	public GDXShape apply(GDXShape shape){
		shape.setDensity(Float.parseFloat(densityField.getText()));
		shape.setFriction(Float.parseFloat(frictionField.getText()));
		shape.setResource(resourceField.getText());
		shape.setRestitution(Float.parseFloat(restitutionField.getText()));
		shape.setFilter(filterTable.createFilterFromUI());
		shape.setTag(tagField.getText());
		return shape;
	}
}
