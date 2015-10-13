package com.blastedstudios.gdxworld.world.quest;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.blastedstudios.gdxworld.plugin.quest.manifestation.dialog.DialogManifestation;
import com.blastedstudios.gdxworld.plugin.quest.trigger.activate.ActivateTrigger;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.IQuestManifestationExecutor;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.IQuestTriggerInformationProvider;

public class GDXQuest implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Quest-"+count++, prerequisites = "";
	/**
	 * Quest may be repeatable within the course of gameplay. This may manifest
	 * in several ways. One example:
	 * A level designer wishes the player to go up an elevator. This may be 
	 * accomplished by a when the player is within a certain distance of a
	 * switch in the elevator (AABB trigger) and hits the action button (action
	 * required checkbox). If he wants to go back down, there may be another 
	 * quest doing the reverse. Both would be repeatable, as the player can go
	 * up or down ad infinitum. 
	 */
	private boolean repeatable;
	/**
	 * If a quest is repeatable, then we will want to be able to reset some of
	 * the triggers. e.g. if a quest is triggered from a time trigger of 5 min,
	 * we can enable "repeatable", then add the time trigger's name to this
	 * list to make that logic have to execute again before triggering the
	 * quest.
	 */
	private List<String> repeatableTriggerReset = new LinkedList<>();
	/**
	 * A trigger is what may activate a quest. After triggering, the quest
	 * manifestation should execute. A trigger might be a distance from an
	 * object, if the player hits a button, a being dies, etc.
	 */ 
	private LinkedList<AbstractQuestTrigger> triggers = null;
	/**
	 * A manifestation defines what happens as a result of a trigger. A 
	 * manifestation may be that an enemy is spawned, a door is unlocked,
	 * an impulse is applied on an object, etc.
	 */
	private AbstractQuestManifestation manifestation = (AbstractQuestManifestation) DialogManifestation.DEFAULT.clone();
	
	public GDXQuest initialize(IQuestTriggerInformationProvider provider,
			IQuestManifestationExecutor executor){
		for(AbstractQuestTrigger trigger : triggers)
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

	public LinkedList<AbstractQuestTrigger> getTriggers() {
		if(triggers == null){
			triggers = new LinkedList<>();
			triggers.add(new ActivateTrigger());
		}
		return triggers;
	}

	public void setTriggers(LinkedList<AbstractQuestTrigger> triggers) {
		this.triggers = triggers;
	}

	public AbstractQuestManifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(AbstractQuestManifestation manifestation) {
		this.manifestation = manifestation;
	}

	public List<String> getRepeatableTriggerReset() {
		if(repeatableTriggerReset == null)
			repeatableTriggerReset = new LinkedList<>();
		return repeatableTriggerReset;
	}

	public void setRepeatableTriggerReset(List<String> repeatableTriggerReset) {
		this.repeatableTriggerReset = repeatableTriggerReset;
	}

	@Override public Object clone(){
		LinkedList<AbstractQuestTrigger> triggers = new LinkedList<>();
		for(AbstractQuestTrigger trigger : this.triggers)
			triggers.add(trigger.clone());
		GDXQuest quest = new GDXQuest();
		quest.setName(name);
		quest.setManifestation(manifestation.clone());
		quest.setPrerequisites(prerequisites);
		quest.setTriggers(triggers);
		quest.setRepeatable(repeatable);
		quest.setRepeatableTriggerReset(new LinkedList<>(getRepeatableTriggerReset()));
		return quest;
	}
	
	@Override public String toString(){
		return "[GDXQuest name:" + name + " prereq:" + prerequisites + 
				" manifestation:" + manifestation + "]";
	}
}
