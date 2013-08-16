package com.blastedstudios.gdxworld.plugin.mode.particle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;

public class ParticleTable extends Table {
	public final VertexTable positionTable;
	public final TextField nameField, effectFileField, imagesDirField, durationField;

	public ParticleTable(final Skin skin, final Vector2 position, String name, 
			String effectFile, String imagesDir, int duration){
		positionTable = new VertexTable(position, skin, null);
		nameField = new TextField(name, skin);
		nameField.setMessageText("<name of particle>");
		effectFileField = new TextField(effectFile, skin);
		effectFileField.setMessageText("<effect file path e.g. data/part.p>");
		imagesDirField = new TextField(imagesDir, skin);
		imagesDirField.setMessageText("<images path e.g. data/textures>");
		durationField = new TextField(duration+"", skin);
		durationField.setMessageText("<duration of particles>");

		add(new Label("Name:", skin));
		add(nameField);
		row();
		add(new Label("Position:", skin));
		add(positionTable);
		row();
		add(new Label("Effect File:", skin));
		add(effectFileField);
		row();
		add(new Label("Images Path:", skin));
		add(imagesDirField);
		row();
		add(new Label("Duration:", skin));
		add(durationField);
	}
}
