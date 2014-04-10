package com.blastedstudios.gdxworld.plugin.quest.manifestation.platform;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PlatformManifestationTable extends ManifestationTable{
	private final PlatformManifestation manifestation;
	private final TextField nameField, waitDurationField, jointField, 
		maxMotorForceAField, maxMotorForceBField, motorSpeedAField, motorSpeedBField;
	private final VertexTable pointATable, pointBTable;
	
	public PlatformManifestationTable(Skin skin, PlatformManifestation manifestation) {
		super(skin);
		this.manifestation = manifestation;
		nameField = new TextField(manifestation.getName(), skin);
		nameField.setMessageText("<name>");
		waitDurationField = new TextField(manifestation.getWaitDuration()+"", skin);
		waitDurationField.setMessageText("<wait duration>");
		jointField = new TextField(manifestation.getJoint(), skin);
		jointField.setMessageText("<joint>");
		maxMotorForceAField = new TextField(manifestation.getMaxMotorForceA()+"", skin);
		maxMotorForceAField.setMessageText("<max motor force towards a>");
		motorSpeedAField = new TextField(manifestation.getMotorSpeedA()+"", skin);
		motorSpeedAField.setMessageText("<motor speed towards a>");
		maxMotorForceBField = new TextField(manifestation.getMaxMotorForceB()+"", skin);
		maxMotorForceBField.setMessageText("<max motor force towards b>");
		motorSpeedBField = new TextField(manifestation.getMotorSpeedB()+"", skin);
		motorSpeedBField.setMessageText("<motor speed towards b>");
		pointATable = new VertexTable(manifestation.getPointA(), skin, null);
		pointBTable = new VertexTable(manifestation.getPointB(), skin, null);

		add(new Label("Name", skin));
		add(nameField);
		row();
		add(new Label("Wait Duration", skin));
		add(waitDurationField);
		row();
		add(new Label("Joint", skin));
		add(jointField);
		row();
		add(new Label("Max Motor Force A", skin));
		add(maxMotorForceAField);
		row();
		add(new Label("Motor Speed A", skin));
		add(motorSpeedAField);
		row();
		add(new Label("Max Motor Force B", skin));
		add(maxMotorForceBField);
		row();
		add(new Label("Motor Speed B", skin));
		add(motorSpeedBField);
		row();
		add(new Label("Point A", skin));
		add(pointATable);
		row();
		add(new Label("Point B", skin));
		add(pointBTable);
		row();
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setName(nameField.getText());
		manifestation.setPointA(pointATable.getVertex());
		manifestation.setPointB(pointBTable.getVertex());
		manifestation.setWaitDuration(Long.parseLong(waitDurationField.getText()));
		manifestation.setJoint(jointField.getText());
		manifestation.setMaxMotorForceA(Float.parseFloat(maxMotorForceAField.getText()));
		manifestation.setMaxMotorForceB(Float.parseFloat(maxMotorForceBField.getText()));
		manifestation.setMotorSpeedA(Float.parseFloat(motorSpeedAField.getText()));
		manifestation.setMotorSpeedB(Float.parseFloat(motorSpeedBField.getText()));
		return manifestation;
	}
}
