package com.blastedstudios.gdxworld.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;

public class AxisCalculatorWindow extends AbstractWindow {
	private final VertexTable beginTable, endTable;
	
	public AxisCalculatorWindow(Skin skin, Vector2 axis, final IAxisReceiver receiver) {
		super("Axis Calculator", skin);
		beginTable = new VertexTable(new Vector2(), skin, null);
		endTable = new VertexTable(axis, skin, null);
		
		Button acceptButton = new TextButton("Accept", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				receiver.setAxis(endTable.getVertex().sub(beginTable.getVertex()));
			}
		});
		
		add(new Label("Begin: ", skin));
		add(beginTable);
		row();
		add(new Label("Begin: ", skin));
		add(endTable);
		row();
		add(acceptButton);
		pack();
		setX(Gdx.graphics.getWidth());
		setY(Gdx.graphics.getHeight());
		setMovable(false);
	}

	public static interface IAxisReceiver{
		public void setAxis(Vector2 axis);
	}
}
