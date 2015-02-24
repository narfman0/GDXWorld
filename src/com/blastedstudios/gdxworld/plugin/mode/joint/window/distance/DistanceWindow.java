package com.blastedstudios.gdxworld.plugin.mode.joint.window.distance;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.DistanceJoint;

class DistanceWindow extends BaseJointWindow {
	private final DistanceJoint joint;
	private final DistanceTable table;

	public DistanceWindow(Skin skin, JointMode mode, DistanceJoint joint) {
		super("Distance Editor", skin, JointType.WeldJoint, mode, joint);
		this.joint = joint;
		add(table = new DistanceTable(skin, joint)).colspan(2);
		row();
		add(createControlTable()).colspan(2);
		pack();
	}
	
	@Override public void apply(){
		table.apply(joint);
	}

	@Override public boolean clicked(Vector2 pos, GDXLevel level) {
		return table.clicked(pos, level);
	}

	@Override public Vector2 getCenter() {
		return table.getCenter();
	}
}
