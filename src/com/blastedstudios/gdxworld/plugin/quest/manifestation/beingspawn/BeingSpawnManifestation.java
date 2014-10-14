package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class BeingSpawnManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final BeingSpawnManifestation DEFAULT = new BeingSpawnManifestation(new Vector2(), "player", "", "");
	private Vector2 coordinates = new Vector2();
	private String being = "", npcData = "", path = "";
	
	public BeingSpawnManifestation(){}
	
	public BeingSpawnManifestation(Vector2 coordinates, String being, String path, String npcData){
		this.coordinates = coordinates;
		this.being = being;
		this.npcData = npcData;
	}

	@Override public CompletionEnum execute() {
		for(IBeingSpawnHandler handler : PluginUtil.getPlugins(IBeingSpawnHandler.class))
			if(handler.beingSpawn(being, coordinates, path, getNpcData()) == CompletionEnum.COMPLETED)
				return CompletionEnum.COMPLETED;
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new BeingSpawnManifestation(coordinates.cpy(), being, path, npcData);
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

	public String getNpcData() {
		if(npcData == null)//backwards compatibility tweak
			return npcData = being;
		return npcData;
	}

	public void setNpcData(String npcData) {
		this.npcData = npcData;
	}

}
