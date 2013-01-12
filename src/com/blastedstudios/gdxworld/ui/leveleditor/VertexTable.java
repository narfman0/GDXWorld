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
	public VertexTable(Vector2 vertex, final Skin skin){
		final Button deleteButton = new TextButton("Delete", skin);
		final TextField coordXLabel = new TextField(vertex.x+"", skin);
		final TextField coordYLabel = new TextField(vertex.y+"", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
			}
		});
		add(coordXLabel);
		add(coordYLabel);
		add(deleteButton);
	}
}
