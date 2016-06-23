package com.blastedstudios.gdxworld.plugin.mode.joint.window.rope;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.RopeJoint;

public class RopeTable extends BaseJointTable {
	private final VertexTable centerTable;
	private final TextField maxLengthField;

	public RopeTable(Skin skin, RopeJoint joint){
		super(skin, joint, JointType.RopeJoint);
		centerTable = new VertexTable(joint.getCenter(), skin);
		maxLengthField = new TextField(joint.getMaxLength()+"", skin);
		maxLengthField.setMessageText("<max length between bodies>");
		add("Center: ");
		add(centerTable);
		row();
		add("Max Length: ");
		add(maxLengthField);
	}
	
	public void apply(RopeJoint joint){
		super.apply(joint);
		joint.setCenter(centerTable.getVertex());
		joint.setMaxLength(Float.parseFloat(maxLengthField.getText()));
	}

	@Override public boolean clicked(Vector2 pos, GDXLevel level) {
		if(!super.clicked(pos, level))
			centerTable.setVertex(pos.x, pos.y);
		return true;
	}

	@Override public Vector2 getCenter() {
		return centerTable.getVertex();
	}
}
