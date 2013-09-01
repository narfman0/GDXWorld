package com.blastedstudios.gdxworld.plugin.mode.sound;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.GDXSound;
import com.blastedstudios.gdxworld.world.GDXSound.SoundAction;

public class SoundTable extends Table {
	public final List soundActionList;
	public final TextField nameField, filenameField, volumeField, panField, pitchField;
	
	public SoundTable(final Skin skin, final GDXSound sound){
		soundActionList = new List(SoundAction.values(), skin);
		nameField = new TextField(sound.getName(), skin);
		nameField.setMessageText("<unique identifier>");
		filenameField = new TextField(sound.getFilename(), skin);
		filenameField.setMessageText("<filename>");
		volumeField = new TextField(sound.getVolume()+"", skin);
		volumeField.setMessageText("<volume>");
		panField = new TextField(sound.getPan()+"", skin);
		panField.setMessageText("<pan, left to right>");
		pitchField = new TextField(sound.getPitch()+"", skin);
		pitchField.setMessageText("<pitch>");
		
		add(new Label("Name: ", skin));
		add(nameField);
		add(new Label("Action: ", skin));
		add(soundActionList);
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
