package com.blastedstudios.gdxworld.plugin.quest.manifestation.sound;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.plugin.mode.sound.SoundTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class SoundManifestationTable extends ManifestationTable{
	private final List<SoundManifestationEnum> modificationTypeList;
	private final SoundTable soundTable;

	public SoundManifestationTable(Skin skin, SoundManifestation sound) {
		super(skin);
		soundTable = new SoundTable(skin, sound.getName(), sound.getFilename(), sound.getVolume(),
				sound.getPan(), sound.getPitch());
		modificationTypeList = new List<SoundManifestationEnum>(skin);
		modificationTypeList.setItems(SoundManifestationEnum.values());
		modificationTypeList.setSelectedIndex(sound.getManifestationType().ordinal());
		add(soundTable);
		row();
		add(modificationTypeList);
	}

	@Override public AbstractQuestManifestation apply() {
		return new SoundManifestation(modificationTypeList.getSelected(),
				soundTable.nameField.getText(), soundTable.filenameField.getText(), 
				Float.parseFloat(soundTable.volumeField.getText()), Float.parseFloat(soundTable.panField.getText()),
				Float.parseFloat(soundTable.pitchField.getText()));
	}

}
