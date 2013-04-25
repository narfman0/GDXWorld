package com.blastedstudios.gdxworld.plugin.mode.quest.manifestation;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.BeingSpawnManifestation;

@PluginImplementation
public class BeingSpawnManifestationTable extends ManifestationTable{
	public static final String BOX_TEXT = "Being Spawn";
	private final VertexTable coordinates;
	private final BeingSpawnManifestation manifestation;
	private final TextField beingField;
	
	public BeingSpawnManifestationTable(Skin skin, BeingSpawnManifestation manifestation){
		super(skin);
		this.manifestation = manifestation;
		beingField = new TextField(manifestation.getBeing(), skin);
		beingField.setMessageText("<being name>");
		coordinates = new VertexTable(manifestation.getCoordinates(), skin, null);
		add(new Label("Being: ", skin));
		add(beingField);
		row();
		add(new Label("Coordinates: ", skin));
		add(coordinates);
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setCoordinates(coordinates.getVertex());
		manifestation.setBeing(beingField.getText());
		return manifestation;
	}
}
