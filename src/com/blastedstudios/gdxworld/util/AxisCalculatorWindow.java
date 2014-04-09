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
	private VertexTable selectTable = null;
	private final boolean consumeAll;
	
	public AxisCalculatorWindow(Skin skin, Vector2 axis, final IAxisReceiver receiver,
			boolean consumeAll) {
		super("Axis Calculator", skin);
		this.consumeAll = consumeAll;
		beginTable = new VertexTable(new Vector2(), skin, null);
		endTable = new VertexTable(axis, skin, null);
		
		Button acceptButton = new TextButton("Accept", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				receiver.setAxis(endTable.getVertex().sub(beginTable.getVertex()));
			}
		});
		Button beginButton = new TextButton("+", skin);
		beginButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selectTable = beginTable;
			}
		});
		Button endButton = new TextButton("+", skin);
		endButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selectTable = endTable;
			}
		});
		
		add(new Label("Begin: ", skin));
		add(beginTable);
		add(beginButton);
		row();
		add(new Label("End: ", skin));
		add(endTable);
		add(endButton);
		row();
		add(acceptButton);
		pack();
		setX(Gdx.graphics.getWidth());
		setY(Gdx.graphics.getHeight());
		setMovable(false);
	}
	
	/**
	 * @return true if click event was consumed, in the case that a table is being edited
	 */
	public boolean clicked(Vector2 pos){
		if(selectTable != null){
			selectTable.setVertex(pos.x, pos.y);
			selectTable = null;
			return true;
		}
		return consumeAll;
	}

	public static interface IAxisReceiver{
		public void setAxis(Vector2 axis);
	}
}
