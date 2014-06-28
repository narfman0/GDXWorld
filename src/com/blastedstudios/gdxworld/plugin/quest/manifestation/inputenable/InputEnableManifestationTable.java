package com.blastedstudios.gdxworld.plugin.quest.manifestation.inputenable;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class InputEnableManifestationTable extends ManifestationTable{
	private final InputEnableManifestation manifestation;
	private final CheckBox inputEnable;
	
	public InputEnableManifestationTable(Skin skin, InputEnableManifestation manifestation){
		super(skin);
		this.manifestation = manifestation;
		inputEnable = new CheckBox("InputEnable", skin);
		inputEnable.setChecked(manifestation.isInputEnable());
		add(inputEnable);
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setInputEnable(inputEnable.isChecked());
		return manifestation;
	}
}