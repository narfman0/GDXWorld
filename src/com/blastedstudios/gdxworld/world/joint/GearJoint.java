package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;

public class GearJoint extends GDXJoint {
	private static final long serialVersionUID = -8241713847302164652L;
	private String joint1, joint2;
	private transient Joint joint1J, joint2J;
	private float ratio;
	/**
	 * Center is used for drawing
	 */
	private transient Vector2 center;
	
	public GearJoint(Vector2 center){
		this.center = center;
	}
	
	/**
	 * Since GearJoint depends on the other joints being created, there is
	 * another initialization step before attaching can occur. Send the
	 * correlated Joints here to make sure attach works. This works because
	 * the other (named) non-GearJoints will be initialized and return their 
	 * joints, so a map of named GDXJoint to Box2D joint can bring in joint1
	 * and joint2 from GDXWorld
	 * TODO make gearjoints load last, send attached joints here since GDXJoints are named
	 * @param joint1J Physics joint representation of joint1
	 * @param joint2J Physics joint representation of joint2
	 */
	public void initialize(Joint joint1J, Joint joint2J){
		this.joint1J = joint1J;
		this.joint2J = joint2J;
	}
	
	@Override public Joint attach(World world) {
		GearJointDef def = new GearJointDef();
		def.joint1 = joint1J;
		def.joint2 = joint2J;
		def.ratio = ratio;
		return attach(world, def);
	}

	public float getRatio() {
		return ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public String getJoint1() {
		return joint1;
	}

	public void setJoint1(String joint1) {
		this.joint1 = joint1;
	}

	public String getJoint2() {
		return joint2;
	}

	public void setJoint2(String joint2) {
		this.joint2 = joint2;
	}

	@Override public Vector2 getCenter() {
		return center.cpy();
	}

	@Override public Object clone() {
		GearJoint clone = new GearJoint(center.cpy());
		clone.setJoint1(joint1);
		clone.setJoint2(joint2);
		clone.setRatio(ratio);
		return super.clone(clone);
	}

	@Override public void translate(Vector2 center) {}
}
