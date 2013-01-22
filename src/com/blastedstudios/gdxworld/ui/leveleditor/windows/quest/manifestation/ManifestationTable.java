package com.blastedstudios.gdxworld.ui.leveleditor.windows.quest.manifestation;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public abstract class ManifestationTable extends Table{
	public ManifestationTable(final Skin skin, AbstractQuestManifestation manifestation){
		super(skin);
	}
	
	public abstract AbstractQuestManifestation apply();
}