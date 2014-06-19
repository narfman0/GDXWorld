package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class BeingSpawnManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final BeingSpawnManifestation DEFAULT = new BeingSpawnManifestation(new Vector2(), "player", "");
	private Vector2 coordinates = new Vector2();
	private String being = "", path = "";
	
	public BeingSpawnManifestation(){}
	
	public BeingSpawnManifestation(Vector2 coordinates, String being, String path){
		this.coordinates = coordinates;
		this.being = being;
	}

	@Override public CompletionEnum execute() {
		for(IBeingSpawnHandler handler : PluginUtil.getPlugins(IBeingSpawnHandler.class))
			if(handler.beingSpawn(being, coordinates, path) == CompletionEnum.COMPLETED)
				return CompletionEnum.COMPLETED;
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new BeingSpawnManifestation(coordinates.cpy(), being, path);
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

	public String getPath() {
		return path == null ? "" : path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
