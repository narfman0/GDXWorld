package com.blastedstudios.gdxworld.plugin.mode.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.joint.PrismaticJoint;

public class PrismaticWindow extends BaseJointWindow {
	private final VertexTable anchorTable, axisTable;
	private final TextField lowerTranslationField, upperTranslationField,
		maxMotorForceField, motorSpeedField;
	private final CheckBox enableLimitBox, enableMotorBox;
	private final PrismaticJoint joint;
	
	public PrismaticWindow(Skin skin, JointMode mode, PrismaticJoint joint){
		super("Prismatic Editor", skin, JointType.PrismaticJoint, mode, joint);
		this.joint = joint;
		anchorTable = new VertexTable(joint.getAnchor(), skin, null);
		axisTable = new VertexTable(joint.getAxis(), skin, null);
		maxMotorForceField = new TextField(joint.getMaxMotorForce()+"", skin);
		maxMotorForceField.setMessageText("<max motor torque>");
		lowerTranslationField = new TextField(joint.getLowerTranslation()+"", skin);
		lowerTranslationField.setMessageText("<lower translation>");
		upperTranslationField = new TextField(joint.getUpperTranslation()+"", skin);
		upperTranslationField.setMessageText("<upper translation>");
		motorSpeedField = new TextField(joint.getMotorSpeed()+"", skin);
		motorSpeedField.setMessageText("<motor speed>");
		enableLimitBox = new CheckBox("", skin);
		enableLimitBox.setChecked(joint.isEnableLimit());
		enableMotorBox = new CheckBox("", skin);
		enableMotorBox.setChecked(joint.isEnableMotor());
		add(new Label("Anchor: ", skin));
		add(anchorTable);
		row();
		add(new Label("Axis: ", skin));
		add(axisTable);
		row();
		add(new Label("Max Motor Force: ", skin));
		add(maxMotorForceField);
		row();
		add(new Label("Motor Speed: ", skin));
		add(motorSpeedField);
		row();
		add(new Label("Upper Translation: ", skin));
		add(upperTranslationField);
		row();
		add(new Label("Lower Translation: ", skin));
		add(lowerTranslationField);
		row();
		add(new Label("Enable Limit: ", skin));
		add(enableLimitBox);
		row();
		add(new Label("Enable Motor: ", skin));
		add(enableMotorBox);
		row();
		add(createControlTable()).colspan(2);
		pack();
	}
	
	@Override public void apply(){
		super.apply();
		joint.setAnchor(anchorTable.getVertex());
		joint.setAxis(axisTable.getVertex());
		joint.setEnableLimit(enableLimitBox.isChecked());
		joint.setEnableMotor(enableMotorBox.isChecked());
		joint.setLowerTranslation(Float.parseFloat(lowerTranslationField.getText()));
		joint.setUpperTranslation(Float.parseFloat(upperTranslationField.getText()));
		joint.setMaxMotorForce(Float.parseFloat(maxMotorForceField.getText()));
		joint.setMotorSpeed(Float.parseFloat(motorSpeedField.getText()));
	}

	@Override public void clicked(Vector2 pos) {
		anchorTable.setVertex(pos.x, pos.y);
	}

	@Override public Vector2 getCenter() {
		return anchorTable.getVertex();
	}
}
