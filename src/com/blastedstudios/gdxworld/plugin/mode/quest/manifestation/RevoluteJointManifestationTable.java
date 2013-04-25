package com.blastedstudios.gdxworld.plugin.mode.quest.manifestation;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.RevoluteJointManifestation;

public class RevoluteJointManifestationTable extends ManifestationTable {
	public static final String BOX_TEXT = "Revolute Joint";
	private final TextField maxMotorTorqueText, motorSpeedText, nameText;
	private final CheckBox enableMotorBox;
	
	public RevoluteJointManifestationTable(Skin skin, RevoluteJointManifestation manifestation) {
		super(skin);
		nameText = new TextField(manifestation.getName(), skin);
		nameText.setMessageText("<name>");
		maxMotorTorqueText = new TextField(manifestation.getMaxMotorTorque()+"", skin);
		maxMotorTorqueText.setMessageText("<max motor torque>");
		motorSpeedText = new TextField(manifestation.getMotorSpeed()+"", skin);
		motorSpeedText.setMessageText("<motor speed>");
		enableMotorBox = new CheckBox("Enable Motor", skin);
		enableMotorBox.setChecked(manifestation.isEnableMotor());
		add(new Label("Name", skin));
		add(nameText);
		row();
		add(enableMotorBox);
		row();
		add(new Label("Max Motor Torque: ", skin));
		add(maxMotorTorqueText);
		row();
		add(new Label("Motor Speed: ", skin));
		add(motorSpeedText);
		row();
	}

	@Override public AbstractQuestManifestation apply() {
		return new RevoluteJointManifestation(nameText.getText(), enableMotorBox.isChecked(), 
				Float.parseFloat(maxMotorTorqueText.getText()), Float.parseFloat(motorSpeedText.getText()));
	}

}
