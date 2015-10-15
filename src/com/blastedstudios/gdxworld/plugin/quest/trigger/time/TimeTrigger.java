package com.blastedstudios.gdxworld.plugin.quest.trigger.time;

import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class TimeTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final TimeTrigger DEFAULT = new TimeTrigger(0); 
	private long time;
	private transient float duration = -1f;
	
	public TimeTrigger(){}
	public TimeTrigger(long time){
		this.time = time;
	}

	@Override public boolean activate(float dt) {
		if(duration == -1f)
			reinitialize();
		duration -= dt;
		return duration <= 0f;
	}
	
	@Override public AbstractQuestTrigger clone(){
		return super.clone(new TimeTrigger(time));
	}

	@Override public String toString() {
		return "[TimeTrigger time:" + time + "]";
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	@Override public void reinitialize() {
		duration = ((float)time)/1000f;
	}
}