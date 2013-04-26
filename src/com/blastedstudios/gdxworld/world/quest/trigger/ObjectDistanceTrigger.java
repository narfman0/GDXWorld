package com.blastedstudios.gdxworld.world.quest.trigger;

public class ObjectDistanceTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final ObjectDistanceTrigger DEFAULT = new ObjectDistanceTrigger("Target", 1); 
	private String target = "";
	private float distance = 1f;
	
	public ObjectDistanceTrigger(){}
	
	public ObjectDistanceTrigger(String target, float distance){
		this.target = target;
		this.distance = distance;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	@Override public boolean activate() {
		return getProvider().getPlayerPosition().dst(
				getProvider().getPhysicsObject(target).getPosition()) <= distance;
	}
	
	@Override public AbstractQuestTrigger clone(){
		return new ObjectDistanceTrigger(target, distance);
	}

	@Override public String toString() {
		return "[ObjectDistanceTrigger: target:" + target + " distance:" + distance + "]";
	}
}
