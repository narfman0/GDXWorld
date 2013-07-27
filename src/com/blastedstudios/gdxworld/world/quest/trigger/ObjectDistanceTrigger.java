package com.blastedstudios.gdxworld.world.quest.trigger;

/**
 * Measures distance from player to a named object, and when that distance is
 * below the given threshold, triggers
 */
public class ObjectDistanceTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final ObjectDistanceTrigger DEFAULT = new ObjectDistanceTrigger("Target", 1, false); 
	private String target = "";
	private float distance = 1f;
	private boolean actionRequired;
	
	public ObjectDistanceTrigger(){}
	
	public ObjectDistanceTrigger(String target, float distance, 
			boolean actionRequired){
		this.target = target;
		this.distance = distance;
		this.actionRequired = actionRequired;
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

	public boolean isActionRequired() {
		return actionRequired;
	}

	public void setActionRequired(boolean actionRequired) {
		this.actionRequired = actionRequired;
	}

	@Override public boolean activate() {
		return getProvider().getPlayerPosition().dst(
				getProvider().getPhysicsObject(target).getPosition()) <= distance &&
				(!actionRequired || getProvider().isAction());
	}
	
	@Override public AbstractQuestTrigger clone(){
		return new ObjectDistanceTrigger(target, distance, actionRequired);
	}

	@Override public String toString() {
		return "[ObjectDistanceTrigger: target:" + target + " distance:" + distance + "]";
	}
}
