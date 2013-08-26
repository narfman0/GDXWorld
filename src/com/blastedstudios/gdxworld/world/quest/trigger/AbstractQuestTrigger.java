package com.blastedstudios.gdxworld.world.quest.trigger;

import java.io.Serializable;

import com.blastedstudios.gdxworld.world.quest.ICloneable;

public abstract class AbstractQuestTrigger implements ICloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private transient IQuestTriggerInformationProvider provider;

	/**
	 * Checks to see if the quest is ready to be activated
	 * @return true if the trigger has been flipped
	 */
	public abstract boolean activate();
	public void initialize(){}

	public IQuestTriggerInformationProvider getProvider() {
		return provider;
	}

	public void setProvider(IQuestTriggerInformationProvider provider) {
		this.provider = provider;
	}
	
	@Override abstract public AbstractQuestTrigger clone();
	@Override abstract public String toString();
}
