package com.blastedstudios.gdxworld.plugin.mode.npc;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXNPC;

class NPCWindow extends AbstractWindow {
	private final VertexTable coordinates;
	private final NPCPropertyTable propertyTable;
	
	public NPCWindow(final Skin skin, final NPCMode mode, final GDXNPC npc) {
		super("NPC Editor", skin);
		propertyTable = new NPCPropertyTable(skin, npc);
		final TextField nameField = new TextField("", skin);
		nameField.setMessageText("<npc name>");
		nameField.setText(npc.getName());
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		coordinates = new VertexTable(npc.getCoordinates().cpy(), skin);
		coordinates.setVertex(npc.getCoordinates().x, npc.getCoordinates().y);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				npc.setName(nameField.getText());
				npc.setCoordinates(coordinates.getVertex());
				propertyTable.apply();
				mode.addNPC(npc);
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
				mode.removeNPC(npc);
				mode.clean();
			}
		});
		add(new Label("Name: ", skin));
		add(nameField);
		row();
		add(propertyTable).colspan(2);
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
	
	public Vector2 getCoordinates(){
		return coordinates.getVertex();
	}
}
