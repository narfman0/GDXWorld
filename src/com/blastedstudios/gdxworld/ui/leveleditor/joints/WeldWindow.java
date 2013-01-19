package com.blastedstudios.gdxworld.ui.leveleditor.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.WeldJoint;

public class WeldWindow extends BaseJointWindow {
	private final TextField anchorXField, anchorYField, referenceAngle;

	public WeldWindow(Skin skin, LevelEditorScreen levelEditorScreen) {
		super("Weld Editor", skin, JointType.WeldJoint, levelEditorScreen);
		anchorXField = new TextField("", skin);
		anchorXField.setMessageText("<anchorX>");
		anchorYField = new TextField("", skin);
		anchorYField.setMessageText("<anchorY>");
		referenceAngle = new TextField("", skin);
		referenceAngle.setMessageText("<reference angle>");
		add(new Label("Anchor: ", skin));
		add(anchorXField);
		add(anchorYField);
		row();
		add(new Label("Reference Angle: ", skin));
		add(referenceAngle);
		addCreateButton();
		pack();
	}
	
	@Override public GDXJoint generate(){
		WeldJoint joint = new WeldJoint();
		joint.setAnchor(new Vector2(Float.parseFloat(anchorXField.getText()), 
				Float.parseFloat(anchorYField.getText())));
		joint.setReferenceAngle(Float.parseFloat(referenceAngle.getText()));
		apply(joint);
		return joint;
	}

}
