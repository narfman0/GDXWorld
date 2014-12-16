package com.blastedstudios.gdxworld.plugin.mode.metadata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.metadata.CameraShot;

class MetadataEditor extends AbstractWindow {
	private final TextField nameField;
	private final VertexTable positionTable, velocityTable;
	private final LevelEditorScreen screen;
	
	public MetadataEditor(final CameraShot shot, final Skin skin, final LevelEditorScreen screen,
			ClickListener acceptListener, ClickListener deleteListener) {
		super("Camera Shot Editor", skin);
		this.screen = screen;
		nameField = new TextField(shot.getName(), skin);
		nameField.setMessageText("<name>");
		positionTable = new VertexTable(shot.getPosition(), skin);
		velocityTable = new VertexTable(shot.getVelocity(), skin);
		final Button acceptButton = new TextButton("Accept", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				apply(shot);
			}
		});
		acceptButton.addListener(acceptListener);
		final Button deleteButton = new TextButton("Delete", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.getLevel().getMetadata().getCameraShots().remove(shot);
			}
		});
		deleteButton.addListener(deleteListener);
		
		add(new Label("Name: ", skin));
		add(nameField);
		row();
		add(new Label("Position: ", skin));
		add(positionTable);
		row();
		add(new Label("Velocity: ", skin));
		add(velocityTable);
		row();
		add(acceptButton);
		add(deleteButton);
		pack();
		setX(Gdx.graphics.getWidth());
		setMovable(false);
	}
	
	private void apply(CameraShot shot){
		shot.setName(nameField.getText());
		shot.setPosition(positionTable.getVertex());
		shot.setVelocity(velocityTable.getVertex());
	}

	public void touched(float x, float y) {
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			positionTable.setVertex(x,y);
		else if(Gdx.input.isKeyPressed(Keys.ALT_LEFT)){
			Vector2 origin = positionTable.getVertex();
			velocityTable.setVertex(x - origin.x, y - origin.y);
		}
		screen.loadLevel();
	}
}