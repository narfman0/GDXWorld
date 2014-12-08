package com.blastedstudios.gdxworld.plugin.mode.joint.window.rope;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.joint.RopeJoint;

class RopeWindow extends BaseJointWindow {
	private final VertexTable centerTable;
	private final RopeJoint joint;
	private final TextField maxLengthField;

	public RopeWindow(Skin skin, JointMode mode, RopeJoint joint) {
		super("Rope Editor", skin, JointType.RopeJoint, mode, joint);
		this.joint = joint;
		centerTable = new VertexTable(joint.getCenter(), skin);
		maxLengthField = new TextField(joint.getMaxLength()+"", skin);
		maxLengthField.setMessageText("<max length between bodies>");
		add("Center: ");
		add(centerTable);
		row();
		add("Max Length: ");
		add(maxLengthField);
		row();
		add(createControlTable()).colspan(2);
		pack();
	}
	
	@Override public void apply(){
		super.apply();
		joint.setCenter(centerTable.getVertex());
		joint.setMaxLength(Float.parseFloat(maxLengthField.getText()));
	}

	@Override public boolean clicked(Vector2 pos) {
		if(!super.clicked(pos))
			centerTable.setVertex(pos.x, pos.y);
		return true;
	}

	@Override public Vector2 getCenter() {
		return centerTable.getVertex();
	}
}
