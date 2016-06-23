package com.blastedstudios.gdxworld.plugin.mode.npc;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXNPC;

public class NPCTable extends Table {
	private final NPCPropertyTable propertyTable;
	private final VertexTable coordinates;
	private final TextField nameField;
	private final GDXNPC npc;
	
	public NPCTable(final Skin skin, final GDXNPC npc){
		super(skin);
		this.npc = npc;
		propertyTable = new NPCPropertyTable(skin, npc);
		coordinates = new VertexTable(npc.getCoordinates().cpy(), skin);
		coordinates.setVertex(npc.getCoordinates().x, npc.getCoordinates().y);
		nameField = new TextField("", skin);
		nameField.setMessageText("<npc name>");
		nameField.setText(npc.getName());
		
		add("Name: ");
		add(nameField);
		row();
		add(propertyTable).colspan(2);
		row();
		add("Coordinates: ");
		add(coordinates).colspan(2);
	}
	
	public void apply(){
		npc.setName(nameField.getText());
		npc.setCoordinates(coordinates.getVertex());
		propertyTable.apply();
	}

	public void setCoordinates(Vector2 coordinates) {
		this.coordinates.setVertex(coordinates.x, coordinates.y);
	}
	
	public Vector2 getCoordinates(){
		return coordinates.getVertex();
	}
}
