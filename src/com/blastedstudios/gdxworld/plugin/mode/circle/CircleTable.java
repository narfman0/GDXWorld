package com.blastedstudios.gdxworld.plugin.mode.circle;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.polygon.ShapeTable;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;

/**
 * Contain the minimum information to represent a circle
 */
public class CircleTable extends Table {
	private final TextField radiusField;
	private final ShapeTable shapeTable;

	public CircleTable(Skin skin, GDXCircle circle){
		radiusField = new TextField("", skin);
		radiusField.setMessageText("<radius>");
		radiusField.setText(circle.getRadius()+"");
		shapeTable = new ShapeTable(skin, circle);
		
		add(new Label("Radius: ", skin));
		add(radiusField);
		row();
		add(shapeTable).colspan(2);
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
		shapeTable.apply(circle);
		return circle;
	}
}
