package com.blastedstudios.gdxworld.world.quest.manifestation;

public class EndLevelManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static EndLevelManifestation DEFAULT = new EndLevelManifestation(true);
	private boolean success;
	
	public EndLevelManifestation(){}
	
	public EndLevelManifestation(boolean success){
		this.success = success;
	}
	
	@Override public void execute() {
		executor.endLevel(success);
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
