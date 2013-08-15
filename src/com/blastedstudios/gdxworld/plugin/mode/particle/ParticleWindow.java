package com.blastedstudios.gdxworld.plugin.mode.particle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXParticle;

class ParticleWindow extends AbstractWindow {
	private final VertexTable positionTable;
	private final TextField nameField, effectFileField, imagesDirField, durationField;
	
	public ParticleWindow(final ParticleMode mode, final Skin skin, final GDXParticle particle) {
		super("Particle Window", skin);
		positionTable = new VertexTable(particle.getPosition(), skin, null);
		nameField = new TextField(particle.getName(), skin);
		nameField.setMessageText("<name of particle>");
		effectFileField = new TextField(particle.getEffectFile(), skin);
		effectFileField.setMessageText("<effect file path e.g. data/part.p>");
		imagesDirField = new TextField(particle.getImagesDir(), skin);
		imagesDirField.setMessageText("<images path e.g. data/textures>");
		durationField = new TextField(particle.getDuration()+"", skin);
		durationField.setMessageText("<duration of particles>");
		
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				particle.setName(nameField.getText());
				particle.setPosition(positionTable.getVertex());
				particle.setDuration(Integer.parseInt(durationField.getText()));
				particle.setEffectFile(effectFileField.getText());
				particle.setImagesDir(imagesDirField.getText());
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
		
		add(new Label("Name:", skin));
		add(nameField);
		row();
		add(new Label("Position:", skin));
		add(positionTable);
		row();
		add(new Label("Effect File:", skin));
		add(effectFileField);
		row();
		add(new Label("Images Path:", skin));
		add(imagesDirField);
		row();
		add(new Label("Duration:", skin));
		add(durationField);
		row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(cancelButton);
		controlTable.add(deleteButton);
		add(controlTable).colspan(2);
		pack();
	}

	public Vector2 getPosition() {
		return positionTable.getVertex();
	}
	
	public void setPosition(float x, float y){
		positionTable.setVertex(x, y);
	}
}
