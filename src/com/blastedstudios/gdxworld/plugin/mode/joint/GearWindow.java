package com.blastedstudios.gdxworld.plugin.mode.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.joint.GearJoint;

public class GearWindow extends BaseJointWindow {
	private final VertexTable centerTable;
	private final TextField ratioField, joint1Field, joint2Field;
	private final GearJoint joint;
	
	public GearWindow(Skin skin, JointMode mode, GearJoint joint){
		super("Prismatic Editor", skin, JointType.PrismaticJoint, mode, joint);
		this.joint = joint;
		centerTable = new VertexTable(joint.getCenter(), skin, null);
		ratioField = new TextField(joint.getRatio()+"", skin);
		ratioField.setMessageText("<ratio>");
		joint1Field = new TextField(joint.getJoint1(), skin);
		joint1Field.setMessageText("<joint 1>");
		joint2Field = new TextField(joint.getJoint2(), skin);
		joint2Field.setMessageText("<joint 2>");
		add(new Label("Center: ", skin));
		add(centerTable);
		row();
		add(new Label("Joint 1: ", skin));
		add(joint1Field);
		row();
		add(new Label("Joint 2: ", skin));
		add(joint2Field);
		row();
		add(new Label("Ratio: ", skin));
		add(ratioField);
		row();
		add(createControlTable()).colspan(2);
		pack();
	}
	
	@Override public void apply(){
		super.apply();
		joint.setCenter(centerTable.getVertex());
		joint.setRatio(Float.parseFloat(ratioField.getText()));
		joint.setJoint1(joint1Field.getText());
		joint.setJoint2(joint2Field.getText());
	}

	@Override public void clicked(Vector2 pos) {
		centerTable.setVertex(pos.x, pos.y);
	}

	@Override public Vector2 getCenter() {
		return centerTable.getVertex();
	}
}
