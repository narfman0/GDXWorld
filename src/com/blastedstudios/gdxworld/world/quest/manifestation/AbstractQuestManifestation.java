package com.blastedstudios.gdxworld.world.quest.manifestation;

import java.io.Serializable;

import com.blastedstudios.gdxworld.world.quest.ICloneable;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

/**
 * Once a quest has been triggered, there will be some sort of way it manifests
 * itself. The manifestor could be an action against an object, dialog, or 
 * similar. 
 */
public abstract class AbstractQuestManifestation implements ICloneable,Serializable{
	private static final long serialVersionUID = 1L;
	protected transient IQuestManifestationExecutor executor;
	
	/**
	 * Executes the manifestation after the trigger has been flipped
	 */
	public abstract CompletionEnum execute();

	public IQuestManifestationExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(IQuestManifestationExecutor executor) {
		this.executor = executor;
	}
	
	@Override abstract public AbstractQuestManifestation clone();
	@Override abstract public String toString();
}
