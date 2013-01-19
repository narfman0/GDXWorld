package com.blastedstudios.gdxworld.ui.leveleditor.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.RevoluteJoint;

public class RevoluteWindow extends BaseJointWindow {
	private final TextField anchorXField, anchorYField, referenceAngleField,
		lowerAngleField, maxMotorTorqueField, motorSpeedField;
	private final CheckBox enableLimitBox, enableMotorBox;

	public RevoluteWindow(Skin skin, LevelEditorScreen levelEditorScreen) {
		super("Revolute Editor", skin, JointType.RevoluteJoint, levelEditorScreen);
		anchorXField = new TextField("", skin);
		anchorXField.setMessageText("<anchorX>");
		anchorYField = new TextField("", skin);
		anchorYField.setMessageText("<anchorY>");
		referenceAngleField = new TextField("", skin);
		referenceAngleField.setMessageText("<reference angle>");
		lowerAngleField = new TextField("", skin);
		lowerAngleField.setMessageText("<lower angle>");
		maxMotorTorqueField = new TextField("", skin);
		maxMotorTorqueField.setMessageText("<max motor torque>");
		motorSpeedField = new TextField("", skin);
		motorSpeedField.setMessageText("<motor speed>");
		enableLimitBox = new CheckBox("", skin);
		enableMotorBox = new CheckBox("", skin);
		add(new Label("Anchor: ", skin));
		add(anchorXField);
		add(anchorYField);
		row();
		add(new Label("Reference Angle: ", skin));
		add(referenceAngleField);
		row();
		add(new Label("Lower Angle: ", skin));
		add(lowerAngleField);
		row();
		add(new Label("Max Motor Torque: ", skin));
		add(maxMotorTorqueField);
		row();
		add(new Label("Motor Speed: ", skin));
		add(motorSpeedField);
		row();
		add(new Label("Enable Limit Box: ", skin));
		add(enableLimitBox);
		row();
		add(new Label("Enable Motor Box: ", skin));
		add(enableMotorBox);
		addCreateButton();
		pack();
	}
	
	@Override public GDXJoint generate(){
		RevoluteJoint joint = new RevoluteJoint();
		joint.setEnableLimit(enableLimitBox.isChecked());
		joint.setEnableMotor(enableMotorBox.isChecked());
		joint.setLowerAngle(Float.parseFloat(lowerAngleField.getText()));
		joint.setMaxMotorTorque(Float.parseFloat(maxMotorTorqueField.getText()));
		joint.setMotorSpeed(Float.parseFloat(motorSpeedField.getText()));
		joint.setAnchor(new Vector2(Float.parseFloat(anchorXField.getText()), 
				Float.parseFloat(anchorYField.getText())));
		joint.setReferenceAngle(Float.parseFloat(referenceAngleField.getText()));
		apply(joint);
		return joint;
	}
}
