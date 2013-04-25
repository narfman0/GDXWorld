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
	private String name = "", prerequisites = "";
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
		return quest;
	}
	
	@Override public String toString(){
		return "[GDXQuest: name:" + name + " prereq:" + prerequisites + 
				" manifestation:" + manifestation + " trigger:" + trigger + "]";
	}
}
