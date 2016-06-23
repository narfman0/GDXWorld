package com.blastedstudios.gdxworld.plugin.mode.joint.window.prismatic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.PrismaticJoint;

public class PrismaticTable extends BaseJointTable {
	private final VertexTable anchorTable, axisTable;
	private final TextField lowerTranslationField, upperTranslationField,
		maxMotorForceField, motorSpeedField;
	private final CheckBox enableLimitBox, enableMotorBox;
	
	public PrismaticTable(Skin skin, PrismaticJoint joint, EventListener axisSelectListener){
		super(skin, joint, JointType.PrismaticJoint);
		anchorTable = new VertexTable(joint.getAnchor(), skin);
		axisTable = new VertexTable(joint.getAxis(), skin);
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
		add("Anchor: ");
		add(anchorTable);
		row();
		add("Axis: ");
		add(axisTable);
		if(axisSelectListener != null){
			TextButton axisSelectButton = new TextButton("+", skin);
			axisSelectButton.addListener(axisSelectListener);
			add(axisSelectButton);
		}
		row();
		add("Max Motor Force: ");
		add(maxMotorForceField);
		row();
		add("Motor Speed: ");
		add(motorSpeedField);
		row();
		add("Upper Translation: ");
		add(upperTranslationField);
		row();
		add("Lower Translation: ");
		add(lowerTranslationField);
		row();
		add("Enable Limit: ");
		add(enableLimitBox);
		row();
		add("Enable Motor: ");
		add(enableMotorBox);
	}
	
	public void apply(PrismaticJoint joint){
		super.apply(joint);
		joint.setAnchor(anchorTable.getVertex());
		joint.setAxis(axisTable.getVertex());
		joint.setEnableLimit(enableLimitBox.isChecked());
		joint.setEnableMotor(enableMotorBox.isChecked());
		joint.setLowerTranslation(Float.parseFloat(lowerTranslationField.getText()));
		joint.setUpperTranslation(Float.parseFloat(upperTranslationField.getText()));
		joint.setMaxMotorForce(Float.parseFloat(maxMotorForceField.getText()));
		joint.setMotorSpeed(Float.parseFloat(motorSpeedField.getText()));
	}

	@Override public boolean clicked(Vector2 pos, GDXLevel level) {
		if(!super.clicked(pos, level))
			anchorTable.setVertex(pos.x, pos.y);
		return true;
	}
	
	public void setAxisTable(Vector2 axis){
		axisTable.setVertex(axis.x, axis.y);
	}

	@Override public Vector2 getCenter() {
		return anchorTable.getVertex();
	}
}
