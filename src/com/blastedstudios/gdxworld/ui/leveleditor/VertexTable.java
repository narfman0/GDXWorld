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
	
	/**
	 * @param vertex current vertex to set text field data
	 * @param listener used only in a list to delete vertex. if null, does not
	 * add delete button
	 */
	public VertexTable(Vector2 vertex, final Skin skin, final VertexRemoveListener listener){
		this.vertex = vertex;
		final VertexTable table = this;
		final Button deleteButton = new TextButton("Delete", skin);
		coordXLabel = new TextField(vertex.x+"", skin);
		coordYLabel = new TextField(vertex.y+"", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.remove(table.getVertex());
			}
		});
		add(coordXLabel);
		add(coordYLabel);
		if(listener != null)
			add(deleteButton);
	}
	
	public Vector2 getVertex(){
		return vertex.set(Float.parseFloat(coordXLabel.getText()), Float.parseFloat(coordYLabel.getText()));
	}
	
	public void setVertex(float x, float y){
		coordXLabel.setText(x+"");
		coordYLabel.setText(y+"");
	}
	
	public interface VertexRemoveListener{
		public void remove(Vector2 vertex);
	}
}
