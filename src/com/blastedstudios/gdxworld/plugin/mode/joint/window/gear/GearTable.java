package com.blastedstudios.gdxworld.plugin.mode.joint.window.gear;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.GearJoint;

public class GearTable extends BaseJointTable {
	private final VertexTable centerTable;
	private final TextField ratioField, joint1Field, joint2Field;

	public GearTable(Skin skin, GearJoint joint){
		super(skin, joint, JointType.GearJoint);
		centerTable = new VertexTable(joint.getCenter(), skin);
		ratioField = new TextField(joint.getRatio()+"", skin);
		ratioField.setMessageText("<ratio>");
		joint1Field = new TextField(joint.getJoint1(), skin);
		joint1Field.setMessageText("<joint 1>");
		joint2Field = new TextField(joint.getJoint2(), skin);
		joint2Field.setMessageText("<joint 2>");
		add("Center: ");
		add(centerTable);
		row();
		add("Joint 1: ");
		add(joint1Field);
		row();
		add("Joint 2: ");
		add(joint2Field);
		row();
		add("Ratio: ");
		add(ratioField);
	}
	
	public void apply(GearJoint joint){
		super.apply(joint);
		joint.setCenter(centerTable.getVertex());
		joint.setRatio(Float.parseFloat(ratioField.getText()));
		joint.setJoint1(joint1Field.getText());
		joint.setJoint2(joint2Field.getText());
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
