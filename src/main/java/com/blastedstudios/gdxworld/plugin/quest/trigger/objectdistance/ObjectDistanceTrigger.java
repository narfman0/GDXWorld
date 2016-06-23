package com.blastedstudios.gdxworld.plugin.quest.trigger.objectdistance;

import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

/**
 * Measures distance from player to a named object, and when that distance is
 * below the given threshold, triggers
 */
public class ObjectDistanceTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final ObjectDistanceTrigger DEFAULT = new ObjectDistanceTrigger(); 
	private String origin = "player", target = "";
	private float distance = 1f;
	private boolean actionRequired;
	
	public ObjectDistanceTrigger(){}
	
	public ObjectDistanceTrigger(String target, String origin, float distance, 
			boolean actionRequired){
		this.target = target;
		this.origin = origin;
		this.distance = distance;
		this.actionRequired = actionRequired;
	}

	@Override public boolean activate(float dt) {
		boolean activated = false;
		for(IObjectDistanceTriggerHandler handler : PluginUtil.getPlugins(IObjectDistanceTriggerHandler.class))
			activated |= handler.activate(this);
		return activated;
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
	
	@Override public AbstractQuestTrigger clone(){
		return super.clone(new ObjectDistanceTrigger(target, origin, distance, actionRequired));
	}

	@Override public String toString() {
		return "[ObjectDistanceTrigger: target:" + target + " distance:" + distance + "]";
	}

	public String getOrigin() {
		if(origin == null)
			origin = "player";
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
