package com.blastedstudios.gdxworld.plugin.quest.manifestation.jointremove;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class JointRemoveManifestationTable extends ManifestationTable{
	private final TextField jointNameText;
	
	public JointRemoveManifestationTable(Skin skin, JointRemoveManifestation manifestation) {
		super(skin);
		jointNameText = new TextField(manifestation.getJointName(), skin);
		jointNameText.setMessageText("<joint name>");
		add(new Label("Joint Name", skin));
		add(jointNameText);
		row();
	}

	@Override public AbstractQuestManifestation apply() {
		return new JointRemoveManifestation(jointNameText.getText());
	}
}
