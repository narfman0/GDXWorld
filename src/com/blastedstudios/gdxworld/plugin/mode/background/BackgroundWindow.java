package com.blastedstudios.gdxworld.plugin.mode.background;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXBackground;

class BackgroundWindow extends AbstractWindow {
	private final VertexTable centerTable;
	
	public BackgroundWindow(final Skin skin, final BackgroundMode mode, final GDXBackground background) {
		super("Background Editor", skin);
		centerTable = new VertexTable(background.getCoordinates(), skin, null);
		final TextField textureField = new TextField("", skin);
		textureField.setMessageText("<background texture>");
		textureField.setText(background.getTexture());
		final TextField depthField = new TextField("", skin);
		depthField.setMessageText("<depth>");
		depthField.setText(background.getDepth()+"");
		final TextField scaleField = new TextField("", skin);
		scaleField.setMessageText("<scale>");
		scaleField.setText(background.getScale()+"");
		
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				background.setTexture(textureField.getText());
				background.setCoordinates(centerTable.getVertex());
				background.setDepth(Float.parseFloat(depthField.getText()));
				background.setScale(Float.parseFloat(scaleField.getText()));
				mode.addBackground(background);
				mode.clean();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.clean();
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.removeBackground(background);
				mode.clean();
			}
		});

		add(new Label("Texture: ", skin));
		add(textureField);
		row();
		add(new Label("Depth: ", skin));
		add(depthField);
		row();
		add(new Label("Scale: ", skin));
		add(scaleField);
		row();
		add(new Label("Center: ", skin));
		add(centerTable);
		row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(deleteButton);
		controlTable.add(cancelButton);
		add(controlTable).colspan(2);
		setMovable(false);
		setHeight(400);
		setWidth(400);
	}

	public void setCenter(Vector2 center) {
		centerTable.setVertex(center.x, center.y);
	}
}
