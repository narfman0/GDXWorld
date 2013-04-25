package com.blastedstudios.gdxworld.plugin.mode.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.DistanceJoint;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;

class DistanceWindow extends BaseJointWindow {
	private final VertexTable anchorATable, anchorBTable;
	private final TextField dampeningRatioField, frequencyHzField, lengthField;
	private final DistanceJoint joint;

	public DistanceWindow(Skin skin, JointMode mode, GDXJoint baseJoint) {
		super("Distance Editor", skin, JointType.WeldJoint, mode, baseJoint);
		this.joint = (DistanceJoint)baseJoint;
		anchorATable = new VertexTable(joint.getAnchorA(), skin, null);
		anchorBTable = new VertexTable(joint.getAnchorB(), skin, null);
		dampeningRatioField = new TextField(joint.getDampeningRatio()+"", skin);
		dampeningRatioField.setMessageText("<dampening ratio>");
		frequencyHzField = new TextField(joint.getFrequencyHz()+"", skin);
		frequencyHzField.setMessageText("<frequency hz>");
		lengthField = new TextField(joint.getLength()+"", skin);
		lengthField.setMessageText("<length>");
		add(new Label("Anchor A: ", skin));
		add(anchorATable);
		row();
		add(new Label("Anchor B: ", skin));
		add(anchorBTable);
		row();
		add(new Label("Dampening Ratio: ", skin));
		add(dampeningRatioField);
		row();
		add(new Label("Frequency Hz: ", skin));
		add(frequencyHzField);
		row();
		add(new Label("Length: ", skin));
		add(lengthField);
		addControlTable();
		pack();
	}
	
	@Override public GDXJoint generate(){
		joint.setAnchorA(anchorATable.getVertex());
		joint.setAnchorB(anchorBTable.getVertex());
		joint.setDampeningRatio(Float.parseFloat(dampeningRatioField.getText()));
		joint.setFrequencyHz(Float.parseFloat(frequencyHzField.getText()));
		joint.setLength(Float.parseFloat(lengthField.getText()));
		return apply(joint);
	}

	@Override public void clicked(Vector2 pos) {
		anchorATable.setVertex(pos.x, pos.y);
	}
}
