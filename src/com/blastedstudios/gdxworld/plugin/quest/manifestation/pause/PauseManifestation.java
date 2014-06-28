package com.blastedstudios.gdxworld.plugin.quest.manifestation.pause;

import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PauseManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final PauseManifestation DEFAULT = new PauseManifestation();
	private boolean pause;
	
	public PauseManifestation(){}
	public PauseManifestation(boolean pause){
		this.pause = pause;
	}
	
	@Override public CompletionEnum execute() {
		for(IPauseHandler handler : PluginUtil.getPlugins(IPauseHandler.class))
			if(handler.pause(pause) == CompletionEnum.COMPLETED)
				return CompletionEnum.COMPLETED;
		return CompletionEnum.EXECUTING;
	}

	@Override public AbstractQuestManifestation clone() {
		return new PauseManifestation(pause);
	}

	@Override public String toString() {
		return "[PauseManifestation pause:" + pause + "]";
	}
	
	public boolean isPause() {
		return pause;
	}
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}
}