package com.blastedstudios.gdxworld.plugin.mode.joint.window.rope;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.IJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.RopeJoint;

@PluginImplementation
public class RopePlugin implements IJointWindow<RopeJoint>{
	@Override public JointType getJointType() {
		return JointType.RopeJoint;
	}

	@Override public RopeJoint createJoint(RopeJoint joint) {
		return joint == null ? new RopeJoint() : joint;
	}

	@Override public BaseJointWindow createJointWindow(Skin skin, JointMode mode, RopeJoint joint) {
		return new RopeWindow(skin, mode, joint);
	}
}