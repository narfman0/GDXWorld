package com.blastedstudios.gdxworld.plugin.quest.manifestation.particle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.particle.ParticleTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class ParticleManifestationTable extends ManifestationTable {
	private final ParticleTable particleTable;
	private final List modificationTypeList;
	
	public ParticleManifestationTable(Skin skin, ParticleManifestation manifestation) {
		super(skin);
		modificationTypeList = new List(ParticleManifestationTypeEnum.values(), skin);
		modificationTypeList.setSelectedIndex(manifestation.getModificationType().ordinal());
		particleTable = new ParticleTable(skin, manifestation.getPosition(), manifestation.getName(),
				manifestation.getEffectFile(), manifestation.getImagesDir(), manifestation.getDuration());
		add(particleTable);
		row();
		add(modificationTypeList);
	}

	@Override public AbstractQuestManifestation apply() {
		return new ParticleManifestation(particleTable.nameField.getText(), 
				particleTable.effectFileField.getText(), 
				particleTable.imagesDirField.getText(), 
				Integer.parseInt(particleTable.durationField.getText()),
				particleTable.positionTable.getVertex(), 
				ParticleManifestationTypeEnum.valueOf(modificationTypeList.getSelection()));
	}

	@Override public void touched(Vector2 coordinates) {
		if(particleTable.positionTable.isCursorActive())
			particleTable.positionTable.setVertex(coordinates.x, coordinates.y);
	}
}
