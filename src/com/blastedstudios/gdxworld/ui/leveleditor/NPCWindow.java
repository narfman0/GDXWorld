package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.world.GDXNPC;

public class NPCWindow extends GDXWindow {
	private final VertexTable coordinates;
	
	public NPCWindow(final Skin skin, final LevelEditorScreen screen, final GDXNPC npc) {
		super("NPC Editor", skin);
		final TextField nameField = new TextField("", skin);
		nameField.setMessageText("<npc name>");
		nameField.setText(npc.getName());
		final TextField behaviorField = new TextField("", skin);
		behaviorField.setMessageText("<behavior name>");
		behaviorField.setText(npc.getBehavior());
		final TextField pathField = new TextField("", skin);
		pathField.setMessageText("<path name>");
		pathField.setText(npc.getPath());
		final TextField resourceField = new TextField("", skin);
		resourceField.setMessageText("<resource>");
		resourceField.setText(npc.getResource());
		final TextField factionField = new TextField("", skin);
		factionField.setMessageText("<faction>");
		factionField.setText(npc.getFaction());
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		coordinates = new VertexTable(npc.getCoordinates(), skin, null);
		coordinates.setVertex(npc.getCoordinates().x, npc.getCoordinates().y);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				npc.setName(nameField.getText());
				npc.setBehavior(behaviorField.getText());
				npc.setCoordinates(coordinates.getVertex());
				npc.setPath(pathField.getText());
				npc.setResource(resourceField.getText());
				npc.setFaction(factionField.getText());
				screen.addNPC(npc);
				screen.removeNPCWindow();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.removeNPCWindow();
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.removeNPC(npc);
				screen.removeNPCWindow();
			}
		});
		add(new Label("Name: ", skin));
		add(nameField);
		row();
		add(new Label("Behavior: ", skin));
		add(behaviorField);
		row();
		add(new Label("Path: ", skin));
		add(pathField);
		row();
		add(new Label("Resource: ", skin));
		add(resourceField);
		row();
		add(new Label("Faction: ", skin));
		add(factionField);
		row();
		add(new Label("Coordinates: ", skin));
		add(coordinates).colspan(2);
		row();
		add(acceptButton);
		add(cancelButton);
		add(deleteButton);
		setMovable(false);
		pack();
	}

	public void setCoordinates(Vector2 coordinates) {
		this.coordinates.setVertex(coordinates.x, coordinates.y);
	}

}
