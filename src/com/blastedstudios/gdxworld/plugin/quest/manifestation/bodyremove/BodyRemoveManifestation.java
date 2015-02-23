package com.blastedstudios.gdxworld.plugin.quest.manifestation.bodyremove;

import com.badlogic.gdx.physics.box2d.Body;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class BodyRemoveManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	public static final BodyRemoveManifestation DEFAULT = new BodyRemoveManifestation();
	private String name = "";
	
	public BodyRemoveManifestation(){}
	
	public BodyRemoveManifestation(String name){
		this.name = name;
	}

	@Override public CompletionEnum execute(float dt) {
		Body object = executor.getPhysicsObject(name);
		if(object != null)
			executor.getWorld().destroyBody(object);
		else
			Log.error("BodyRemoveManifestation.execute", "Box2d body null with name: " + name);
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new BodyRemoveManifestation(name);
	}

	@Override public String toString() {
		return "[BodyRemoveManifestation name:" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
