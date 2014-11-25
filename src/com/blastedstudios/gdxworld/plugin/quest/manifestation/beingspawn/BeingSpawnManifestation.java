package com.blastedstudios.gdxworld.plugin.quest.manifestation.beingspawn;

import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.GDXNPC;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class BeingSpawnManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static final BeingSpawnManifestation DEFAULT = new BeingSpawnManifestation();
	private GDXNPC npc = new GDXNPC();
	
	public BeingSpawnManifestation(){}
	
	public BeingSpawnManifestation(GDXNPC npc){
		this.npc = npc;
	}

	@Override public CompletionEnum execute() {
		for(IBeingSpawnHandler handler : PluginUtil.getPlugins(IBeingSpawnHandler.class))
			if(handler.beingSpawn(npc) == CompletionEnum.COMPLETED)
				return CompletionEnum.COMPLETED;
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new BeingSpawnManifestation(npc);
	}

	@Override public String toString() {
		return "[BeingSpawnManifestation npc: " + npc.toString() + "]";
	}

	public GDXNPC getNpc() {
		return npc;
	}

	public void setNpc(GDXNPC npc) {
		this.npc = npc;
	}
}
