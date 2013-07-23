package com.blastedstudios.gdxworld.world.quest;

import java.io.Serializable;

import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.DialogManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.IQuestManifestationExecutor;
import com.blastedstudios.gdxworld.world.quest.trigger.AABBTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.IQuestTriggerInformationProvider;

public class GDXQuest implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Quest-"+count++, prerequisites = "";
	private boolean repeatable;
	private AbstractQuestTrigger trigger = new AABBTrigger(0, 0, 1, 1);
	private AbstractQuestManifestation manifestation = (AbstractQuestManifestation) DialogManifestation.DEFAULT.clone();
	
	public GDXQuest initialize(IQuestTriggerInformationProvider provider,
			IQuestManifestationExecutor executor){
		trigger.setProvider(provider);
		manifestation.setExecutor(executor);
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public boolean isRepeatable() {
		return repeatable;
	}

	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}

	public AbstractQuestTrigger getTrigger() {
		return trigger;
	}

	public void setTrigger(AbstractQuestTrigger trigger) {
		this.trigger = trigger;
	}

	public AbstractQuestManifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(AbstractQuestManifestation manifestation) {
		this.manifestation = manifestation;
	}
	
	@Override public Object clone(){
		GDXQuest quest = new GDXQuest();
		quest.setName(name);
		quest.setManifestation(manifestation.clone());
		quest.setPrerequisites(prerequisites);
		quest.setTrigger(trigger.clone());
		quest.setRepeatable(repeatable);
		return quest;
	}
	
	@Override public String toString(){
		return "[GDXQuest name:" + name + " prereq:" + prerequisites + 
				" manifestation:" + manifestation + " trigger:" + trigger + "]";
	}
}
