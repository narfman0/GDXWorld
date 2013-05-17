package com.blastedstudios.gdxworld.plugin.mode.circle;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;

/**
 * Contain the minimum information to represent a circle
 */
public class CircleTable extends Table {
	private final TextField radiusField, densityField, frictionField, restitutionField;

	public CircleTable(Skin skin, GDXCircle circle){
		radiusField = new TextField("", skin);
		radiusField.setMessageText("<radius>");
		radiusField.setText(circle.getRadius()+"");
		densityField = new TextField("", skin);
		densityField.setMessageText("<density, calculates mass>");
		densityField.setText(circle.getDensity()+"");
		frictionField = new TextField("", skin);
		frictionField.setMessageText("<friction>");
		frictionField.setText(circle.getFriction()+"");
		restitutionField = new TextField("", skin);
		restitutionField.setMessageText("<restitution>");
		restitutionField.setText(circle.getRestitution()+"");
		
		add(new Label("Radius: ", skin));
		add(radiusField);
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
	
	public float getRadius(){
		try{
			return Float.parseFloat(radiusField.getText());
		}catch(Exception e){
			return 1f;
		}
	}
	
	public GDXCircle apply(GDXCircle circle){
		circle.setRadius(getRadius());
		circle.setDensity(Float.parseFloat(densityField.getText()));
		circle.setFriction(Float.parseFloat(frictionField.getText()));
		circle.setRestitution(Float.parseFloat(restitutionField.getText()));
		return circle;
	}
}
