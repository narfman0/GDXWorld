package com.blastedstudios.gdxworld.plugin.mode.particle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;

public class ParticleTable extends Table {
	public final VertexTable positionTable;
	public final TextField nameField, effectFileField, imagesDirField, durationField,
		emitterNameField, attachedBodyField;
	private final Skin skin;

	public ParticleTable(final Skin skin, boolean showPaths, boolean showModifiers, 
			final Vector2 position, String name, String effectFile, 
			String imagesDir, int duration, String emitterName, String attachedBody){
		this.skin = skin;
		positionTable = new VertexTable(position, skin);
		nameField = new TextField(name, skin);
		nameField.setMessageText("<name of particle>");
		effectFileField = new TextField(effectFile, skin);
		effectFileField.setMessageText("<effect file path e.g. data/part.p>");
		imagesDirField = new TextField(imagesDir, skin);
		imagesDirField.setMessageText("<images path e.g. data/textures>");
		durationField = new TextField(duration+"", skin);
		durationField.setMessageText("<duration of particles>");
		emitterNameField = new TextField(emitterName, skin);
		emitterNameField.setMessageText("<emitter name e.g. smoke>");
		attachedBodyField = new TextField(attachedBody, skin);
		attachedBodyField.setMessageText("<attached body>");
		addComponents(showPaths, showModifiers);
	}
	
	public void addComponents(boolean showPaths, boolean showModifiers){
		add(new Label("Name:", skin));
		add(nameField);
		if(showModifiers){
			row();
			add(new Label("Position:", skin));
			add(positionTable);
			row();
			add(new Label("Duration:", skin));
			add(durationField);
			row();
			add(new Label("Emitter Name:", skin));
			add(emitterNameField);
			row();
			add(new Label("Attached Body:", skin));
			add(attachedBodyField);
		}
		if(showPaths){
			row();
			add(new Label("Effect File:", skin));
			add(effectFileField);
			row();
			add(new Label("Images Path:", skin));
			add(imagesDirField);
		}
	}
}
