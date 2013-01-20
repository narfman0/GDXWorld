package com.blastedstudios.gdxworld.world.quest.trigger;

public class PersonTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	private final String name;
	private final float distance;
	
	public PersonTrigger(String name, float distance){
		this.name = name;
		this.distance = distance;
	}

	@Override public boolean activate() {
		return getProvider().isNear(name, distance);
	}
	
	@Override public Object clone(){
		return new PersonTrigger(name, distance);
	}
}