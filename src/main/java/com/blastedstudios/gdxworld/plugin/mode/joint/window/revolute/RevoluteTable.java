package com.blastedstudios.gdxworld.plugin.mode.joint.window.revolute;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.RevoluteJoint;

public class RevoluteTable extends BaseJointTable {
	private final VertexTable anchorTable;
	private final TextField referenceAngleField, lowerAngleField,
		upperAngleField, maxMotorTorqueField, motorSpeedField;
	private final CheckBox enableLimitBox, enableMotorBox;

	public RevoluteTable(Skin skin, RevoluteJoint joint){
		super(skin, joint, JointType.RevoluteJoint);
		anchorTable = new VertexTable(joint.getAnchor().cpy(), skin);
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
		add("Anchor: ");
		add(anchorTable);
		row();
		add("Reference Angle: ");
		add(referenceAngleField);
		row();
		add("Lower Angle: ");
		add(lowerAngleField);
		row();
		add("Upper Angle: ");
		add(upperAngleField);
		row();
		add("Max Motor Torque: ");
		add(maxMotorTorqueField);
		row();
		add("Motor Speed: ");
		add(motorSpeedField);
		row();
		add("Enable Limit: ");
		add(enableLimitBox);
		row();
		add("Enable Motor: ");
		add(enableMotorBox);
	}
	
	public void apply(RevoluteJoint joint){
		super.apply(joint);
		joint.setEnableLimit(enableLimitBox.isChecked());
		joint.setEnableMotor(enableMotorBox.isChecked());
		joint.setLowerAngle(Float.parseFloat(lowerAngleField.getText()));
		joint.setUpperAngle(Float.parseFloat(upperAngleField.getText()));
		joint.setMaxMotorTorque(Float.parseFloat(maxMotorTorqueField.getText()));
		joint.setMotorSpeed(Float.parseFloat(motorSpeedField.getText()));
		joint.setAnchor(anchorTable.getVertex());
		joint.setReferenceAngle(Float.parseFloat(referenceAngleField.getText()));
	}

	@Override public boolean clicked(Vector2 pos, GDXLevel level) {
		if(!super.clicked(pos, level))
			anchorTable.setVertex(pos.x, pos.y);
		return true;
	}

	@Override public Vector2 getCenter() {
		return anchorTable.getVertex();
	}
}
