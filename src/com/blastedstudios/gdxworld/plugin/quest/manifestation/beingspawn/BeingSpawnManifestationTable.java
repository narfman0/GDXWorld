package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class BeingSpawnManifestationTable extends ManifestationTable{
	private final VertexTable coordinatesTable;
	private final BeingSpawnManifestation manifestation;
	private final TextField beingField, pathField;
	
	public BeingSpawnManifestationTable(Skin skin, BeingSpawnManifestation manifestation){
		super(skin);
		this.manifestation = manifestation;
		beingField = new TextField(manifestation.getBeing(), skin);
		beingField.setMessageText("<being name>");
		pathField = new TextField(manifestation.getPath(), skin);
		pathField.setMessageText("<path>");
		coordinatesTable = new VertexTable(manifestation.getCoordinates(), skin, null);
		add(new Label("Being: ", skin));
		add(beingField);
		row();
		add(new Label("Path: ", skin));
		add(pathField);
		row();
		add(new Label("Coordinates: ", skin));
		add(coordinatesTable);
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setCoordinates(coordinatesTable.getVertex());
		manifestation.setBeing(beingField.getText());
		manifestation.setPath(pathField.getText());
		return manifestation;
	}

	@Override public void touched(Vector2 coordinates) {
		this.coordinatesTable.setVertex(coordinates.x, coordinates.y);
	}
}
