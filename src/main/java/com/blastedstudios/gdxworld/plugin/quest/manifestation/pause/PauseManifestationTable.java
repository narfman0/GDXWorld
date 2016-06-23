package com.blastedstudios.gdxworld.plugin.quest.manifestation.pause;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PauseManifestationTable extends ManifestationTable{
	private final PauseManifestation manifestation;
	private final CheckBox pause;
	
	public PauseManifestationTable(Skin skin, PauseManifestation manifestation){
		super(skin);
		this.manifestation = manifestation;
		pause = new CheckBox("Pause", skin);
		pause.setChecked(manifestation.isPause());
		add(pause);
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setPause(pause.isChecked());
		return manifestation;
	}
}