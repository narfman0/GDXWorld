package com.blastedstudios.gdxworld.plugin.mode.quest.manifestation;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.EndLevelManifestation;

public class EndLevelManifestationTable extends ManifestationTable {
	private final CheckBox successBox;
	
	public EndLevelManifestationTable(Skin skin, EndLevelManifestation manifestation) {
		super(skin);
		successBox = new CheckBox("Success", skin);
		successBox.setChecked(manifestation.isSuccess());
		add(successBox);
	}

	@Override public AbstractQuestManifestation apply() {
		return new EndLevelManifestation(successBox.isChecked());
	}
}
