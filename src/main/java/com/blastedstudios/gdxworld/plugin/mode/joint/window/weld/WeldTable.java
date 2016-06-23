package com.blastedstudios.gdxworld.plugin.mode.joint.window.weld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.WeldJoint;

public class WeldTable extends BaseJointTable {
	private final VertexTable anchorTable;
	private final TextField referenceAngleField;
	
	public WeldTable(Skin skin, WeldJoint joint){
		super(skin, joint, JointType.WeldJoint);
		anchorTable = new VertexTable(joint.getAnchor(), skin);
		referenceAngleField = new TextField(joint.getReferenceAngle()+"", skin);
		referenceAngleField.setMessageText("<reference angle>");
		add("Anchor: ");
		add(anchorTable);
		row();
		add("Reference Angle: ");
		add(referenceAngleField);
	}

	public void apply(WeldJoint joint){
		super.apply(joint);
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
