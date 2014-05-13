package com.blastedstudios.gdxworld.plugin.quest.manifestation.jointremove;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Joint;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class JointRemoveManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	public static final JointRemoveManifestation DEFAULT = new JointRemoveManifestation();
	private String jointName = "";
	
	public JointRemoveManifestation(){}
	
	public JointRemoveManifestation(String jointName){
		this.jointName = jointName;
	}

	@Override public CompletionEnum execute() {
		Joint joint = executor.getPhysicsJoint(jointName);
		if(joint != null)
			executor.getWorld().destroyJoint(joint);
		else
			Gdx.app.error("JointRemoveManifestation.execute", "Box2d joint null with jointName: " + jointName);
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new JointRemoveManifestation(jointName);
	}

	@Override public String toString() {
		return "[JointRemoveManifestation jointName:" + jointName + "]";
	}

	public String getJointName() {
		return jointName;
	}

	public void setJointName(String jointName) {
		this.jointName = jointName;
	}
}
