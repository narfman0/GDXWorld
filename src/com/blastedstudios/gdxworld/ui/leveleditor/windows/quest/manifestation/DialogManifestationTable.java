package com.blastedstudios.gdxworld.ui.leveleditor.windows.quest.manifestation;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.DialogManifestation;

public class DialogManifestationTable extends ManifestationTable {
	private final TextField dialogText, originText;
	private final DialogManifestation manifestation;
	
	public DialogManifestationTable(Skin skin, DialogManifestation manifestation) {
		super(skin, manifestation);
		this.manifestation = manifestation;
		dialogText = new TextField(manifestation.getDialog(), skin);
		dialogText.setMessageText("<dialog text>");
		originText = new TextField(manifestation.getOrigin(), skin);
		originText.setMessageText("<origin text>");
		add(new Label("Dialog: ", skin));
		add(dialogText);
		add(new Label("Origin: ", skin));
		add(originText);
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setOrigin(originText.getText());
		manifestation.setDialog(dialogText.getText());
		return manifestation;
	}
}
