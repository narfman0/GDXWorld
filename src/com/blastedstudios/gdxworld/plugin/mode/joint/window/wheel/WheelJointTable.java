package com.blastedstudios.gdxworld.plugin.mode.joint.window.wheel;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.joint.WheelJoint;

public class WheelJointTable extends BaseJointTable {
	private final TextField maxMotorTorqueField, motorSpeedField, dampingRatioField, frequencyHzField;
	private final CheckBox enableMotorBox;
	private final VertexTable anchorTable, axisTable;

	public WheelJointTable(Skin skin, WheelJoint joint){
		super(skin, joint, JointType.WheelJoint);
		anchorTable = new VertexTable(joint.getAnchor(), skin);
		axisTable = new VertexTable(joint.getAxis(), skin);
		maxMotorTorqueField = new TextField(joint.getMaxMotorTorque()+"", skin);
		maxMotorTorqueField.setMessageText("<max motor torque>");
		motorSpeedField = new TextField(joint.getMotorSpeed()+"", skin);
		motorSpeedField.setMessageText("<motor speed>");
		dampingRatioField = new TextField(joint.getDampingRatio()+"", skin);
		dampingRatioField.setMessageText("<damping ratio>");
		frequencyHzField = new TextField(joint.getFrequencyHz()+"", skin);
		frequencyHzField.setMessageText("<frequency hz>");
		enableMotorBox = new CheckBox("", skin);
		enableMotorBox.setChecked(joint.isEnableMotor());
		add(new Label("Anchor: ", skin));
		add(anchorTable);
		row();
		add(new Label("Axis: ", skin));
		add(axisTable);
		row();
		add(new Label("Max Motor Torque: ", skin));
		add(maxMotorTorqueField);
		row();
		add(new Label("Motor Speed: ", skin));
		add(motorSpeedField);
		row();
		add(new Label("Enable Motor: ", skin));
		add(enableMotorBox);
		row();
		add(new Label("Frequency (hz): ", skin));
		add(frequencyHzField);
		row();
		add(new Label("Damping Ratio: ", skin));
		add(dampingRatioField);
	}
	
	public void apply(WheelJoint joint){
		super.apply(joint);
		joint.setAnchor(anchorTable.getVertex());
		joint.setAxis(axisTable.getVertex());
		joint.setDampingRatio(Float.parseFloat(dampingRatioField.getText()));
		joint.setEnableMotor(enableMotorBox.isChecked());
		joint.setFrequencyHz(Float.parseFloat(frequencyHzField.getText()));
		joint.setMaxMotorTorque(Float.parseFloat(maxMotorTorqueField.getText()));
		joint.setMotorSpeed(Float.parseFloat(motorSpeedField.getText()));
	}

	public VertexTable getAnchorTable() {
		return anchorTable;
	}
}
