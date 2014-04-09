package com.blastedstudios.gdxworld.plugin.mode.joint.window.revolute;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.IJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.RevoluteJoint;

@PluginImplementation
public class RevolutePlugin implements IJointWindow<RevoluteJoint>{
	@Override public JointType getJointType() {
		return JointType.RevoluteJoint;
	}

	@Override public RevoluteJoint createJoint(RevoluteJoint joint) {
		return joint == null ? new RevoluteJoint() : joint;
	}

	@Override public BaseJointWindow createJointWindow(Skin skin, JointMode mode, RevoluteJoint joint) {
		return new RevoluteWindow(skin, mode, joint);
	}
}