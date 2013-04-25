package com.blastedstudios.gdxworld.world.quest.trigger;

public class ActivateTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final ActivateTrigger DEFAULT = new ActivateTrigger();
	
	@Override public boolean activate() {
		return true;
	}

	@Override public AbstractQuestTrigger clone() {
		return new ActivateTrigger();
	}

	@Override public String toString() {
		return "[ActivateTrigger]";
	}
}
