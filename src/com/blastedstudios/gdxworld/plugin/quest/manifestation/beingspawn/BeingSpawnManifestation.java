package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class BeingSpawnManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final BeingSpawnManifestation DEFAULT = new BeingSpawnManifestation(new Vector2(), "Name");
	private Vector2 coordinates = new Vector2();
	private String being = "";
	
	public BeingSpawnManifestation(){}
	
	public BeingSpawnManifestation(Vector2 coordinates, String being){
		this.coordinates = coordinates;
		this.being = being;
	}

	@Override public CompletionEnum execute() {
		executor.beingSpawn(being, coordinates);
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new BeingSpawnManifestation(coordinates.cpy(), being);
	}

	@Override public String toString() {
		return "[BeingSpawnManifestation coords:" + coordinates.toString() + " being:" + being + "]";
	}

	public Vector2 getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Vector2 coordinates) {
		this.coordinates = coordinates;
	}

	public String getBeing() {
		return being;
	}

	public void setBeing(String being) {
		this.being = being;
	}

}
