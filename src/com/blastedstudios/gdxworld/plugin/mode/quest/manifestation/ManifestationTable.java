package com.blastedstudios.gdxworld.plugin.mode.quest.manifestation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public abstract class ManifestationTable extends Table{
	public ManifestationTable(final Skin skin){
		super(skin);
	}
	
	public abstract AbstractQuestManifestation apply();
	public void touched(Vector2 coordinates){}
}