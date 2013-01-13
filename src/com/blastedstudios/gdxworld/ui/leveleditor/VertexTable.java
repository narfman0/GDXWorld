package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class VertexTable extends Table {
	private final TextField coordXLabel, coordYLabel;
	private final Vector2 vertex;
	
	public VertexTable(Vector2 vertex, final Skin skin, final PolygonWindow listener){
		this.vertex = vertex;
		final VertexTable table = this;
		final Button deleteButton = new TextButton("Delete", skin);
		coordXLabel = new TextField(vertex.x+"", skin);
		coordXLabel.setWidth(10);
		coordYLabel = new TextField(vertex.y+"", skin);
		coordYLabel.setWidth(10);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.remove(table.getVertex());
			}
		});
		add(coordXLabel);
		add(coordYLabel);
		add(deleteButton);
	}
	
	public Vector2 getVertex(){
		return vertex.set(Float.parseFloat(coordXLabel.getText()), Float.parseFloat(coordYLabel.getText()));
	}
}
