package com.blastedstudios.gdxworld.plugin.mode.sound;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.GDXSound;

public class SoundTable extends Table {
	public final TextField nameField, filenameField, volumeField, panField, pitchField;
	
	public SoundTable(final Skin skin, String name, String filename, float volume,
			float pan, float pitch){
		nameField = new TextField(name, skin);
		nameField.setMessageText("<unique identifier>");
		filenameField = new TextField(filename, skin);
		filenameField.setMessageText("<filename>");
		volumeField = new TextField(volume+"", skin);
		volumeField.setMessageText("<volume>");
		panField = new TextField(pan+"", skin);
		panField.setMessageText("<pan, left to right>");
		pitchField = new TextField(pitch+"", skin);
		pitchField.setMessageText("<pitch>");
		
		add(new Label("Name: ", skin));
		add(nameField);
		add(new Label("Filename: ", skin));
		add(filenameField);
		row();
		add(new Label("Volume: ", skin));
		add(volumeField);
		add(new Label("Pan: ", skin));
		add(panField);
		add(new Label("Pitch: ", skin));
		add(pitchField);
	}

	public void update(GDXSound sound) {
		sound.setName(nameField.getText());
		sound.setFilename(filenameField.getText());
		sound.setPan(Float.parseFloat(panField.getText()));
		sound.setPitch(Float.parseFloat(pitchField.getText()));
		sound.setVolume(Float.parseFloat(volumeField.getText()));
	}
}
