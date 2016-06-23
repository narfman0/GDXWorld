package com.blastedstudios.gdxworld.plugin.mode.joint.window.revolute;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.RevoluteJoint;

class RevoluteWindow extends BaseJointWindow {
	private final RevoluteJoint joint;
	private final RevoluteTable table;

	public RevoluteWindow(Skin skin, JointMode mode, RevoluteJoint joint) {
		super("Revolute Editor", skin, JointType.RevoluteJoint, mode, joint);
		this.joint = joint;
		add(table = new RevoluteTable(skin, joint)).colspan(2);
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
