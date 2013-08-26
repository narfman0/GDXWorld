package com.blastedstudios.gdxworld.plugin.quest.trigger.time;

import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class TimeTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final TimeTrigger DEFAULT = new TimeTrigger(0); 
	private long time;
	private transient long initiated = -1L;
	
	public TimeTrigger(){}
	public TimeTrigger(long time){
		this.time = time;
	}

	@Override public boolean activate() {
		if(System.currentTimeMillis() - initiated > time)
			return true;
		return false;
	}
	
	@Override public AbstractQuestTrigger clone(){
		return new TimeTrigger(time);
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
	
	@Override public void initialize() {
		initiated = System.currentTimeMillis();
	}
}