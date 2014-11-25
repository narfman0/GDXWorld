package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.npc.NPCTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class BeingSpawnManifestationTable extends ManifestationTable{
	private final BeingSpawnManifestation manifestation;
	private final NPCTable table;
	
	public BeingSpawnManifestationTable(Skin skin, BeingSpawnManifestation manifestation){
		super(skin);
		table = new NPCTable(skin, manifestation.getNpc());
		this.manifestation = manifestation;
		add(table);
	}

	@Override public AbstractQuestManifestation apply() {
		table.apply();
		return manifestation;
	}

	@Override public void touched(Vector2 coordinates) {
		table.setCoordinates(coordinates);
	}
}
