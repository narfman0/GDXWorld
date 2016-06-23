package com.blastedstudios.gdxworld.plugin.quest.trigger.kill;

import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

/**
 * Trigger monitoring when a particular named being is killed. Returns true
 * if being with name is dead.
 */
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

	@Override public boolean activate(float dt) {
		return getProvider().isDead(name);
	}
	
	@Override public AbstractQuestTrigger clone(){
		return super.clone(new KillTrigger(name));
	}

	@Override public String toString() {
		return "[KillTrigger:" + name + "]";
	}
}