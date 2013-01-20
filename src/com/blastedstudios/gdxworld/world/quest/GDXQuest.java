package com.blastedstudios.gdxworld.world.quest;

import java.io.Serializable;

import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.IQuestManifestationExecutor;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.IQuestTriggerInformationProvider;

public class GDXQuest implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	private String name = "", prerequisites = "";
	private AbstractQuestTrigger trigger;
	private AbstractQuestManifestation manifestation;
	
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
		quest.setManifestation((AbstractQuestManifestation) manifestation.clone());
		quest.setPrerequisites(prerequisites);
		quest.setTrigger((AbstractQuestTrigger) trigger.clone());
		return quest;
	}
}
