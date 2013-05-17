package com.blastedstudios.gdxworld.plugin.mode.polygon;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class ShapeTable extends Table {
	private final TextField densityField, frictionField, restitutionField;

	public ShapeTable(Skin skin, GDXShape shape){
		densityField = new TextField("", skin);
		densityField.setMessageText("<density, calculates mass>");
		densityField.setText(shape.getDensity()+"");
		frictionField = new TextField("", skin);
		frictionField.setMessageText("<friction>");
		frictionField.setText(shape.getFriction()+"");
		restitutionField = new TextField("", skin);
		restitutionField.setMessageText("<restitution>");
		restitutionField.setText(shape.getRestitution()+"");
		
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
		shape.setRestitution(Float.parseFloat(restitutionField.getText()));
		return shape;
	}
}
