package com.blastedstudios.gdxworld.plugin.mode.joint.window.gear;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.IJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.GearJoint;

@PluginImplementation
public class GearPlugin implements IJointWindow<GearJoint>{
	@Override public JointType getJointType() {
		return JointType.GearJoint;
	}

	@Override public GearJoint createJoint(GearJoint joint) {
		return joint == null ? new GearJoint() : joint;
	}

	@Override public BaseJointWindow createJointWindow(Skin skin, JointMode mode, GearJoint joint) {
		return new GearWindow(skin, mode, joint);
	}
}