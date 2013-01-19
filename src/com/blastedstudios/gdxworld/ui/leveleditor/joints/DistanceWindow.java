package com.blastedstudios.gdxworld.ui.leveleditor.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.joint.DistanceJoint;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;

public class DistanceWindow extends BaseJointWindow {
	private final VertexTable anchorATable, anchorBTable;
	private final TextField dampeningRatioField, frequencyHzField, lengthField;

	public DistanceWindow(Skin skin, LevelEditorScreen levelEditorScreen) {
		super("Distance Editor", skin, JointType.WeldJoint, levelEditorScreen);
		anchorATable = new VertexTable(new Vector2(), skin, null);
		anchorBTable = new VertexTable(new Vector2(), skin, null);
		dampeningRatioField = new TextField("0", skin);
		dampeningRatioField.setMessageText("<dampening ratio>");
		frequencyHzField = new TextField("0", skin);
		frequencyHzField.setMessageText("<frequency hz>");
		lengthField = new TextField("1", skin);
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
		addCreateButton();
		pack();
	}
	
	@Override public GDXJoint generate(){
		DistanceJoint joint = new DistanceJoint();
		joint.setAnchorA(anchorATable.getVertex());
		joint.setAnchorB(anchorBTable.getVertex());
		joint.setDampeningRatio(Float.parseFloat(dampeningRatioField.getText()));
		joint.setFrequencyHz(Float.parseFloat(frequencyHzField.getText()));
		joint.setLength(Float.parseFloat(lengthField.getText()));
		apply(joint);
		return joint;
	}

	@Override public void clicked(Vector2 pos) {
		anchorATable.setVertex(pos.x, pos.y);
	}
}
