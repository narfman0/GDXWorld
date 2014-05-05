package com.blastedstudios.gdxworld.plugin.mode.background;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
	private final VertexTable centerTable, scissorPosition, scissorDimension;
	private final GDXBackground background;
	private final TextField depthField;
	private final CheckBox scissor;
	private VertexTable selected = null;
	
	public BackgroundWindow(final Skin skin, final BackgroundMode mode, final GDXBackground background) {
		super("Background Editor", skin);
		this.background = background;
		centerTable = new VertexTable(background.getCoordinates(), skin);
		Button centerButton = new TextButton("+", skin);
		centerButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selected = centerTable;
			}
		});
		final TextField textureField = new TextField("", skin);
		textureField.setMessageText("<background texture>");
		textureField.setText(background.getTexture());
		depthField = new TextField("", skin);
		depthField.setMessageText("<depth>");
		depthField.setText(background.getDepth()+"");
		final TextField scaleField = new TextField("", skin);
		scaleField.setMessageText("<scale>");
		scaleField.setText(background.getScale()+"");
		scissor = new CheckBox("Use Scissor", skin);
		scissor.setChecked(background.isScissor());
		scissorPosition = new VertexTable(background.getScissorPosition(), skin);
		Button scissorPositionButton = new TextButton("+", skin);
		scissorPositionButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selected = scissorPosition;
			}
		});
		scissorDimension = new VertexTable(background.getScissorDimensions(), skin);
		
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				background.setTexture(textureField.getText());
				background.setCoordinates(centerTable.getVertex());
				background.setDepth(Float.parseFloat(depthField.getText()));
				background.setScale(Float.parseFloat(scaleField.getText()));
				background.setScissor(scissor.isChecked());
				background.setScissorDimensions(scissorDimension.getVertex());
				background.setScissorPosition(scissorPosition.getVertex());
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
		add(centerButton);
		row();
		add(scissor);
		row();
		add(new Label("Scissor Position: ", skin));
		add(scissorPosition);
		add(scissorPositionButton);
		row();
		add(new Label("Scissor Dimensions: ", skin));
		add(scissorDimension);
		row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(deleteButton);
		controlTable.add(cancelButton);
		add(controlTable).colspan(3);
		setMovable(false);
		pack();
	}

	public void setCenter(Vector2 center) {
		centerTable.setVertex(center.x, center.y);
	}
	
	public boolean clicked(Vector2 pos){
		if(selected != null){
			selected.setVertex(pos.x, pos.y);
			selected = null;
			return true;
		}
		return false;
	}

	public GDXBackground getGDXBackground() {
		return background;
	}
	
	public float getDepth(){
		return Float.parseFloat(depthField.getText());
	}
}
