package com.blastedstudios.gdxworld.world.quest.trigger;

public class PersonTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	private final String origin, target;
	private final float distance;
	
	public PersonTrigger(String origin, String target, float distance){
		this.origin = origin;
		this.target = target;
		this.distance = distance;
	}

	@Override public boolean activate() {
		return getProvider().isNear(origin, target, distance);
	}
	
	@Override public Object clone(){
		return new PersonTrigger(origin, target, distance);
	}

	@Override public String toString() {
		return "[PersonTrigger: origin:" + origin + " target:" + target + " distance:" + distance + "]";
	}
}