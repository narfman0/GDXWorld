package com.blastedstudios.gdxworld.plugin.mode.joint.window.wheel;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.WheelJoint;

public class WheelWindow extends BaseJointWindow{
	private final WheelJoint joint;
	private final WheelJointTable table;

	public WheelWindow(Skin skin, JointMode mode, WheelJoint joint) {
		super("Wheel Editor", skin, JointType.WheelJoint, mode, joint);
		this.joint = joint;
		table = new WheelJointTable(skin, joint);
		add(table);
		row();
		add(createControlTable()).colspan(2);
		pack();
	}
	
	@Override public void apply(){
		super.apply();
		table.apply(joint);
	}

	@Override public boolean clicked(Vector2 pos) {
		if(!super.clicked(pos))
			table.getAnchorTable().setVertex(pos.x, pos.y);
		return true;
	}

	@Override public Vector2 getCenter() {
		return table.getAnchorTable().getVertex();
	}
}
