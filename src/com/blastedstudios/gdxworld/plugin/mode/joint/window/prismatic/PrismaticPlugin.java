package com.blastedstudios.gdxworld.plugin.mode.joint.window.prismatic;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.IJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.plugin.mode.joint.window.prismatic.PrismaticWindow;
import com.blastedstudios.gdxworld.world.joint.PrismaticJoint;

@PluginImplementation
public class PrismaticPlugin implements IJointWindow<PrismaticJoint>{
	@Override public JointType getJointType() {
		return JointType.PrismaticJoint;
	}

	@Override public PrismaticJoint createJoint(PrismaticJoint joint) {
		return joint == null ? new PrismaticJoint() : joint;
	}

	@Override public BaseJointWindow createJointWindow(Skin skin, JointMode mode, PrismaticJoint joint) {
		return new PrismaticWindow(skin, mode, joint);
	}
}