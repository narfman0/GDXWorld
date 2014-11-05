package com.blastedstudios.gdxworld.plugin.mode.joint.window.distance;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.IJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.DistanceJoint;

@PluginImplementation
public class DistancePlugin implements IJointWindow<DistanceJoint>{
	@Override public JointType getJointType() {
		return JointType.DistanceJoint;
	}

	@Override public DistanceJoint createJoint(DistanceJoint joint) {
		return joint == null ? new DistanceJoint() : joint;
	}

	@Override public BaseJointWindow createJointWindow(Skin skin, JointMode mode, DistanceJoint joint) {
		return new DistanceWindow(skin, mode, joint);
	}
}
