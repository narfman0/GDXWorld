package com.blastedstudios.gdxworld.plugin.quest.manifestation.prismaticjoint;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PrismaticJointManifestationTable extends ManifestationTable {
	private final TextField maxMotorForceText, motorSpeedText, nameText;
	private final CheckBox enableMotorBox;
	
	public PrismaticJointManifestationTable(Skin skin, PrismaticJointManifestation manifestation) {
		super(skin);
		nameText = new TextField(manifestation.getName(), skin);
		nameText.setMessageText("<name>");
		maxMotorForceText = new TextField(manifestation.getMaxMotorForce()+"", skin);
		maxMotorForceText.setMessageText("<max motor force>");
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
		add(maxMotorForceText);
		row();
		add(new Label("Motor Speed: ", skin));
		add(motorSpeedText);
		row();
	}

	@Override public AbstractQuestManifestation apply() {
		return new PrismaticJointManifestation(nameText.getText(), enableMotorBox.isChecked(), 
				Float.parseFloat(maxMotorForceText.getText()), Float.parseFloat(motorSpeedText.getText()));
	}
}
