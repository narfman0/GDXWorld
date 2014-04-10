package com.blastedstudios.gdxworld.plugin.mode.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.RevoluteJoint;

class RevoluteWindow extends BaseJointWindow {
	private final VertexTable anchorTable;
	private final TextField referenceAngleField, lowerAngleField,
		upperAngleField, maxMotorTorqueField, motorSpeedField;
	private final CheckBox enableLimitBox, enableMotorBox;
	private final RevoluteJoint joint;

	public RevoluteWindow(Skin skin, JointMode mode, RevoluteJoint joint) {
		super("Revolute Editor", skin, JointType.RevoluteJoint, mode, joint);
		this.joint = joint;
		anchorTable = new VertexTable(joint.getAnchor().cpy(), skin, null);
		referenceAngleField = new TextField(joint.getReferenceAngle()+"", skin);
		referenceAngleField.setMessageText("<reference angle>");
		lowerAngleField = new TextField(joint.getLowerAngle()+"", skin);
		lowerAngleField.setMessageText("<lower angle>");
		upperAngleField = new TextField(joint.getUpperAngle()+"", skin);
		upperAngleField.setMessageText("<upper angle>");
		maxMotorTorqueField = new TextField(joint.getMaxMotorTorque()+"", skin);
		maxMotorTorqueField.setMessageText("<max motor torque>");
		motorSpeedField = new TextField(joint.getMotorSpeed()+"", skin);
		motorSpeedField.setMessageText("<motor speed>");
		enableLimitBox = new CheckBox("", skin);
		enableLimitBox.setChecked(joint.isEnableLimit());
		enableMotorBox = new CheckBox("", skin);
		enableMotorBox.setChecked(joint.isEnableMotor());
		add(new Label("Anchor: ", skin));
		add(anchorTable);
		row();
		add(new Label("Reference Angle: ", skin));
		add(referenceAngleField);
		row();
		add(new Label("Lower Angle: ", skin));
		add(lowerAngleField);
		row();
		add(new Label("Upper Angle: ", skin));
		add(upperAngleField);
		row();
		add(new Label("Max Motor Torque: ", skin));
		add(maxMotorTorqueField);
		row();
		add(new Label("Motor Speed: ", skin));
		add(motorSpeedField);
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
		joint.setEnableLimit(enableLimitBox.isChecked());
		joint.setEnableMotor(enableMotorBox.isChecked());
		joint.setLowerAngle(Float.parseFloat(lowerAngleField.getText()));
		joint.setUpperAngle(Float.parseFloat(upperAngleField.getText()));
		joint.setMaxMotorTorque(Float.parseFloat(maxMotorTorqueField.getText()));
		joint.setMotorSpeed(Float.parseFloat(motorSpeedField.getText()));
		joint.setAnchor(anchorTable.getVertex());
		joint.setReferenceAngle(Float.parseFloat(referenceAngleField.getText()));
	}

	@Override public boolean clicked(Vector2 pos) {
		if(!super.clicked(pos))
			anchorTable.setVertex(pos.x, pos.y);
		return true;
	}

	@Override public Vector2 getCenter() {
		return anchorTable.getVertex();
	}
}
