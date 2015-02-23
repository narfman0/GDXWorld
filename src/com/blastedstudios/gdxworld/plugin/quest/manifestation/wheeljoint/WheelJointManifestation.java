package com.blastedstudios.gdxworld.plugin.quest.manifestation.wheeljoint;

import com.blastedstudios.gdxworld.world.joint.WheelJoint;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class WheelJointManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final WheelJointManifestation DEFAULT = new WheelJointManifestation();
	private WheelJoint joint = new WheelJoint();
	
	public WheelJointManifestation(){}
	
	public WheelJointManifestation(WheelJoint joint){
		this.setJoint(joint);
	}

	@Override public CompletionEnum execute(float dt) {
		//TODO make currently existing joints change properties
		joint.attach(executor.getWorld());
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new WheelJointManifestation(getJoint());
	}

	@Override public String toString() {
		return "[WheelJointManifestation joint:" + getJoint() + "]";
	}

	public WheelJoint getJoint() {
		return joint;
	}

	public void setJoint(WheelJoint joint) {
		this.joint = joint;
	}
}
