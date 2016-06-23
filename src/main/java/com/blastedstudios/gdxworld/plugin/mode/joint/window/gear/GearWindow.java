package com.blastedstudios.gdxworld.plugin.mode.joint.window.gear;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.GearJoint;

public class GearWindow extends BaseJointWindow {
	private final GearJoint joint;
	private final GearTable table;
	
	public GearWindow(Skin skin, JointMode mode, GearJoint joint){
		super("Gear Editor", skin, JointType.GearJoint, mode, joint);
		this.joint = joint;
		add(table = new GearTable(skin, joint)).colspan(2);
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
