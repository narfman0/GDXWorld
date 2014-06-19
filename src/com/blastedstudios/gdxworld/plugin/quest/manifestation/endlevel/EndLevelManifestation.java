package com.blastedstudios.gdxworld.plugin.quest.manifestation.endlevel;

import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class EndLevelManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static EndLevelManifestation DEFAULT = new EndLevelManifestation(true);
	private boolean success;
	
	public EndLevelManifestation(){}
	
	public EndLevelManifestation(boolean success){
		this.success = success;
	}
	
	@Override public CompletionEnum execute() {
		for(IEndLevelHandler handler : PluginUtil.getPlugins(IEndLevelHandler.class))
			if(handler.endLevel(success) == CompletionEnum.COMPLETED)
				return CompletionEnum.COMPLETED;
		return CompletionEnum.EXECUTING;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override public AbstractQuestManifestation clone() {
		return new EndLevelManifestation(success);
	}

	@Override public String toString() {
		return "[EndLevelManifestation: success:" + success+"]";
	}
}
