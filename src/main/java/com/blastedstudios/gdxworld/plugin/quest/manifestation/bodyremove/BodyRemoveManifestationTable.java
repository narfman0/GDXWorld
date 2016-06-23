package com.blastedstudios.gdxworld.plugin.quest.manifestation.bodyremove;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class BodyRemoveManifestationTable extends ManifestationTable{
	private final TextField nameText;
	
	public BodyRemoveManifestationTable(Skin skin, BodyRemoveManifestation manifestation) {
		super(skin);
		nameText = new TextField(manifestation.getName(), skin);
		nameText.setMessageText("<body name>");
		add(new Label("Body Name", skin));
		add(nameText);
		row();
	}

	@Override public AbstractQuestManifestation apply() {
		return new BodyRemoveManifestation(nameText.getText());
	}
}
