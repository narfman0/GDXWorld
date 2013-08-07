package com.blastedstudios.gdxworld.plugin.quest.trigger.activate;

import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

/**
 * Trigger that activates immediately. Could be used for simple things like
 * defining player start location or other trivial things
 */
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
