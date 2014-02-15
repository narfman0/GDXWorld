package com.blastedstudios.gdxworld.plugin.mode.polygon;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class ShapeTable extends Table {
	private final TextField densityField, frictionField, restitutionField, resourceField;

	public ShapeTable(Skin skin, GDXShape shape){
		resourceField = new TextField(shape.getResource(), skin);
		resourceField.setMessageText("<resource name>");
		densityField = new TextField(shape.getDensity()+"", skin);
		densityField.setMessageText("<density, calculates mass>");
		frictionField = new TextField(shape.getFriction()+"", skin);
		frictionField.setMessageText("<friction>");
		restitutionField = new TextField(shape.getRestitution()+"", skin);
		restitutionField.setMessageText("<restitution>");

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
	}
	
	public GDXShape apply(GDXShape shape){
		shape.setDensity(Float.parseFloat(densityField.getText()));
		shape.setFriction(Float.parseFloat(frictionField.getText()));
		shape.setResource(resourceField.getText());
		shape.setRestitution(Float.parseFloat(restitutionField.getText()));
		return shape;
	}
}
