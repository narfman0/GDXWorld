package com.blastedstudios.gdxworld.world.quest.trigger;

import java.io.Serializable;

public abstract class AbstractQuestTrigger implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private transient IQuestTriggerInformationProvider provider;

	/**
	 * Checks to see if the quest is ready to be activated
	 * @return true if the trigger has been flipped
	 */
	public abstract boolean activate();

	public IQuestTriggerInformationProvider getProvider() {
		return provider;
	}

	public void setProvider(IQuestTriggerInformationProvider provider) {
		this.provider = provider;
	}
	
	@Override abstract public Object clone();
}
