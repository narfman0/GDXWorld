package com.blastedstudios.gdxworld.plugin.quest.manifestation.endlevel;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class EndLevelManifestationTable extends ManifestationTable {
	private final CheckBox successBox;
	private final TextField nextLevelTextField;
	
	public EndLevelManifestationTable(Skin skin, EndLevelManifestation manifestation) {
		super(skin);
		successBox = new CheckBox("Success", skin);
		successBox.setChecked(manifestation.isSuccess());
		nextLevelTextField = new TextField(manifestation.getNextLevel(), skin);
		nextLevelTextField.setMessageText("<next level>");
		add(successBox);
		row();
		add("Next Level ");
		add(nextLevelTextField);
	}

	@Override public AbstractQuestManifestation apply() {
		return new EndLevelManifestation(successBox.isChecked(), nextLevelTextField.getText());
	}
}
