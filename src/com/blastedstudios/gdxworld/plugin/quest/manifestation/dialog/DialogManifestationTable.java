package com.blastedstudios.gdxworld.plugin.quest.manifestation.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class DialogManifestationTable extends ManifestationTable {
	private final TextField dialogText, originText, typeText;
	private final DialogManifestation manifestation;
	
	public DialogManifestationTable(Skin skin, DialogManifestation manifestation) {
		super(skin);
		this.manifestation = manifestation;
		dialogText = new TextField(manifestation.getDialog(), skin);
		dialogText.setMessageText("<dialog text>");
		originText = new TextField(manifestation.getOrigin(), skin);
		originText.setMessageText("<origin text>");
		typeText = new TextField(manifestation.getType(), skin);
		typeText.setMessageText("<type text>");
		add(new Label("Dialog: ", skin));
		add(dialogText);
		add(new Label("Origin: ", skin));
		add(originText);
		add(new Label("Type: ", skin));
		add(typeText);
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setOrigin(originText.getText());
		manifestation.setDialog(dialogText.getText());
		manifestation.setType(typeText.getText());
		return manifestation;
	}
}
