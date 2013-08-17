package com.blastedstudios.gdxworld.plugin.quest.manifestation.particle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
		modificationTypeList.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				if(modificationTypeList.getSelectedIndex() == ParticleManifestationTypeEnum.CREATE.ordinal())
					particleTable.setComponentsVisible(true);
				else if(modificationTypeList.getSelectedIndex() == ParticleManifestationTypeEnum.MODIFY.ordinal()){
					particleTable.setComponentsVisible(true);
					particleTable.effectFileField.setVisible(false);
					particleTable.imagesDirField.setVisible(false);
				}else if(modificationTypeList.getSelectedIndex() == ParticleManifestationTypeEnum.REMOVE.ordinal()){
					particleTable.setComponentsVisible(false);
					particleTable.nameField.setVisible(true);
				}
			}
		});
		particleTable = new ParticleTable(skin, manifestation.getPosition(), manifestation.getName(),
				manifestation.getEffectFile(), manifestation.getImagesDir(), manifestation.getDuration(),
				manifestation.getEmitterName());
		add(modificationTypeList);
		row();
		add(particleTable);
	}

	@Override public AbstractQuestManifestation apply() {
		return new ParticleManifestation(particleTable.nameField.getText(), 
				particleTable.effectFileField.getText(), 
				particleTable.imagesDirField.getText(), 
				Integer.parseInt(particleTable.durationField.getText()),
				particleTable.positionTable.getVertex(), 
				ParticleManifestationTypeEnum.valueOf(modificationTypeList.getSelection()),
				particleTable.emitterNameField.getText());
	}

	@Override public void touched(Vector2 coordinates) {
		if(particleTable.positionTable.isCursorActive())
			particleTable.positionTable.setVertex(coordinates.x, coordinates.y);
	}
}
