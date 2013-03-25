package com.blastedstudios.gdxworld.ui.leveleditor.mode.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ColorTable extends Table {
	private final TextField rLabel, gLabel, bLabel, aLabel;
	
	public ColorTable(final Color color, final Skin skin){
		rLabel = new TextField(color.r+"", skin);
		gLabel = new TextField(color.g+"", skin);
		bLabel = new TextField(color.b+"", skin);
		aLabel = new TextField(color.a+"", skin);
		rLabel.setMessageText("<r, [0-1]>");
		gLabel.setMessageText("<g, [0-1]>");
		bLabel.setMessageText("<b, [0-1]>");
		aLabel.setMessageText("<a, [0-1]>");
		add(rLabel);
		add(gLabel);
		add(bLabel);
		add(aLabel);
	}
	
	public Color getColor(){
		return new Color(Float.parseFloat(rLabel.getText()), Float.parseFloat(gLabel.getText()),
				Float.parseFloat(bLabel.getText()), Float.parseFloat(aLabel.getText()));
	}
	
	public void setVertex(Color color){
		rLabel.setText(color.r+"");
		gLabel.setText(color.g+"");
		bLabel.setText(color.b+"");
		aLabel.setText(color.a+"");
	}
}
