package com.blastedstudios.gdxworld.plugin.mode.particle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.world.GDXParticle;

class ParticleWindow extends AbstractWindow {
	private final ParticleTable particleTable;
	
	public ParticleWindow(final ParticleMode mode, final Skin skin, final GDXParticle particle) {
		super("Particle Window", skin);
		particleTable = new ParticleTable(skin, true, true,
				particle.getPosition(), particle.getName(),
				particle.getEffectFile(), particle.getImagesDir(), particle.getDuration(),
				particle.getEmitterName());
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				particle.setName(particleTable.nameField.getText());
				particle.setPosition(particleTable.positionTable.getVertex());
				particle.setDuration(Integer.parseInt(particleTable.durationField.getText()));
				particle.setEffectFile(particleTable.effectFileField.getText());
				particle.setImagesDir(particleTable.imagesDirField.getText());
				particle.setEmitterName(particleTable.emitterNameField.getText());
				mode.addParticle(particle);
				mode.clean();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.clean();
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.removeParticle(particle);
				mode.clean();
			}
		});
		
		add(particleTable);
		row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(cancelButton);
		controlTable.add(deleteButton);
		add(controlTable);
		pack();
	}

	public Vector2 getPosition() {
		return particleTable.positionTable.getVertex();
	}
	
	public void setPosition(float x, float y){
		particleTable.positionTable.setVertex(x, y);
	}
}
