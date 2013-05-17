package com.blastedstudios.gdxworld.plugin.mode.chain;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.polygon.ShapeTable;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

public class RectangleTable extends Table {
	private final TextField heightField, widthField;
	private final ShapeTable shapeTable;

	public RectangleTable(Skin skin, GDXPolygon polygon){
		widthField = new TextField(getDefaultWidth()+"", skin);
		widthField.setMessageText("<width>");
		heightField = new TextField(getDefaultHeight()+"", skin);
		heightField.setMessageText("<length>");
		shapeTable = new ShapeTable(skin, polygon);

		add(new Label("Width: ", skin));
		add(widthField);
		row();
		add(new Label("Height: ", skin));
		add(heightField);
		row();
		add(shapeTable).colspan(2);
	}
	
	public void apply(GDXPolygon polygon){
		polygon.getVertices().add(new Vector2(-parseWidth(), parseHeight()));
		polygon.getVertices().add(new Vector2(parseWidth(), parseHeight()));
		polygon.getVertices().add(new Vector2(parseWidth(), -parseHeight()));
		polygon.getVertices().add(new Vector2(-parseWidth(), -parseHeight()));
		shapeTable.apply(polygon);
	}
	
	private float parseWidth(){
		try{
			return Float.parseFloat(widthField.getText());
		}catch(Exception e){
			return getDefaultWidth();
		}
	}
	
	private float parseHeight(){
		try{
			return Float.parseFloat(heightField.getText());
		}catch(Exception e){
			return getDefaultHeight();
		}
	}
	
	private static float getDefaultWidth(){
		return Properties.getFloat("level.chain.rectangle.width",.3f); 
	}
	
	private static float getDefaultHeight(){
		return Properties.getFloat("level.chain.rectangle.height",.1f); 
	}
}
