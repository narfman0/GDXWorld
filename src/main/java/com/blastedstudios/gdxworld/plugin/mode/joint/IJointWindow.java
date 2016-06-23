package com.blastedstudios.gdxworld.plugin.mode.joint;

import net.xeoh.plugins.base.Plugin;

import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;

public interface IJointWindow<T extends GDXJoint> extends Plugin{
	public JointType getJointType();
	public T createJoint(T joint);
	public BaseJointWindow createJointWindow(Skin skin, JointMode mode, T joint);
}
