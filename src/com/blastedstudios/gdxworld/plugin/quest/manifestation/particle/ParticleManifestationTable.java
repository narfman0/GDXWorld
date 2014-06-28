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
	private final List<ParticleManifestationTypeEnum> modificationTypeList;
	
	public ParticleManifestationTable(Skin skin, ParticleManifestation manifestation) {
		super(skin);
		modificationTypeList = new List<ParticleManifestationTypeEnum>(skin);
		modificationTypeList.setItems(ParticleManifestationTypeEnum.values());
		modificationTypeList.setSelectedIndex(manifestation.getModificationType().ordinal());
		modificationTypeList.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				particleTable.clear();
				ParticleManifestationTypeEnum type = ParticleManifestationTypeEnum.values()[
				            modificationTypeList.getSelectedIndex()];
				boolean showPaths = type == ParticleManifestationTypeEnum.CREATE;
				boolean showModifiers = type != ParticleManifestationTypeEnum.REMOVE;
				particleTable.addComponents(showPaths, showModifiers);
			}
		});
		boolean showPaths = manifestation.getModificationType() == ParticleManifestationTypeEnum.CREATE;
		boolean showModifiers = manifestation.getModificationType() != ParticleManifestationTypeEnum.REMOVE;
		particleTable = new ParticleTable(skin, showPaths, showModifiers,
				manifestation.getPosition(), manifestation.getName(), 
				manifestation.getEffectFile(), manifestation.getImagesDir(), 
				manifestation.getDuration(), manifestation.getEmitterName(),
				manifestation.getAttachedBody());
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
				modificationTypeList.getSelected(),
				particleTable.emitterNameField.getText(),
				particleTable.attachedBodyField.getText());
	}

	@Override public void touched(Vector2 coordinates) {
		if(particleTable.positionTable.isCursorActive())
			particleTable.positionTable.setVertex(coordinates.x, coordinates.y);
	}
}
