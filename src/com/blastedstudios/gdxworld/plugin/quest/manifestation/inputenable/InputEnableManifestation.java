package com.blastedstudios.gdxworld.plugin.quest.manifestation.inputenable;

import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class InputEnableManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final InputEnableManifestation DEFAULT = new InputEnableManifestation();
	private boolean inputEnable;
	
	public InputEnableManifestation(){}
	public InputEnableManifestation(boolean inputEnable){
		this.inputEnable = inputEnable;
	}
	
	@Override public CompletionEnum execute(float dt) {
		for(IInputEnableHandler handler : PluginUtil.getPlugins(IInputEnableHandler.class))
			if(handler.inputEnable(inputEnable) == CompletionEnum.COMPLETED)
				return CompletionEnum.COMPLETED;
		return CompletionEnum.EXECUTING;
	}

	@Override public AbstractQuestManifestation clone() {
		return new InputEnableManifestation(inputEnable);
	}

	@Override public String toString() {
		return "[InputEnableManifestation inputEnable:" + inputEnable + "]";
	}
	
	public boolean isInputEnable() {
		return inputEnable;
	}
	
	public void setInputEnable(boolean inputEnable) {
		this.inputEnable = inputEnable;
	}
}