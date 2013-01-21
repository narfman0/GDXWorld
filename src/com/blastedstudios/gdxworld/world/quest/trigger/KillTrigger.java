package com.blastedstudios.gdxworld.world.quest.trigger;

public class KillTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	private final String name;
	
	public KillTrigger(String name){
		this.name = name;
	}

	@Override public boolean activate() {
		return getProvider().isDead(name);
	}
	
	@Override public Object clone(){
		return new KillTrigger(name);
	}

	@Override public String toString() {
		return "[KillTrigger:" + name + "]";
	}
}