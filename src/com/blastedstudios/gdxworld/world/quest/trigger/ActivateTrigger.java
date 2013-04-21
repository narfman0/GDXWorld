package com.blastedstudios.gdxworld.world.quest.trigger;

public class ActivateTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	
	@Override public boolean activate() {
		return true;
	}

	@Override public Object clone() {
		return new ActivateTrigger();
	}

	@Override public String toString() {
		return "[ActivateTrigger]";
	}
}
