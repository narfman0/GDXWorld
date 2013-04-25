package com.blastedstudios.gdxworld.world.quest.trigger;

public class KillTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final KillTrigger DEFAULT = new KillTrigger("Name");
	private String name = "";
	
	public KillTrigger(){}
	
	public KillTrigger(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override public boolean activate() {
		return getProvider().isDead(name);
	}
	
	@Override public AbstractQuestTrigger clone(){
		return new KillTrigger(name);
	}

	@Override public String toString() {
		return "[KillTrigger:" + name + "]";
	}
}