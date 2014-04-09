package com.blastedstudios.gdxworld.plugin.mode.joint.window.weld;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.IJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.WeldJoint;

@PluginImplementation
public class WeldPlugin implements IJointWindow<WeldJoint>{
	@Override public JointType getJointType() {
		return JointType.WeldJoint;
	}

	@Override public WeldJoint createJoint(WeldJoint joint) {
		return joint == null ? new WeldJoint() : joint;
	}

	@Override public BaseJointWindow createJointWindow(Skin skin, JointMode mode, WeldJoint joint) {
		return new WeldWindow(skin, mode, joint);
	}
}