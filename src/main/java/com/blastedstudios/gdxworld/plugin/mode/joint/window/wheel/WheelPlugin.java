package com.blastedstudios.gdxworld.plugin.mode.joint.window.wheel;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.IJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.WheelJoint;

@PluginImplementation
public class WheelPlugin implements IJointWindow<WheelJoint>{
	@Override public JointType getJointType() {
		return JointType.WheelJoint;
	}

	@Override public WheelJoint createJoint(WheelJoint joint) {
		return joint == null ? new WheelJoint() : joint;
	}

	@Override public BaseJointWindow createJointWindow(Skin skin, JointMode mode, WheelJoint joint) {
		return new WheelWindow(skin, mode, joint);
	}
}