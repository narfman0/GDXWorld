package com.blastedstudios.gdxworld.ui.leveleditor.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.WeldJoint;

public class WeldWindow extends BaseJointWindow {
	private final VertexTable anchorTable;
	private final TextField referenceAngleField;

	public WeldWindow(Skin skin, LevelEditorScreen levelEditorScreen) {
		super("Weld Editor", skin, JointType.WeldJoint, levelEditorScreen);
		anchorTable = new VertexTable(new Vector2(), skin, null);
		referenceAngleField = new TextField("0", skin);
		referenceAngleField.setMessageText("<reference angle>");
		add(new Label("Anchor: ", skin));
		add(anchorTable);
		row();
		add(new Label("Reference Angle: ", skin));
		add(referenceAngleField);
		addCreateButton();
		pack();
	}
	
	@Override public GDXJoint generate(){
		WeldJoint joint = new WeldJoint();
		joint.setAnchor(anchorTable.getVertex());
		joint.setReferenceAngle(Float.parseFloat(referenceAngleField.getText()));
		return apply(joint);
	}

	@Override public void clicked(Vector2 pos) {
		anchorTable.setVertex(pos.x, pos.y);
	}

}
