package com.blastedstudios.gdxworld.plugin.mode.animation;

import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class AnimationManifestationStruct {
	public final AbstractQuestManifestation manifestation;
	
	public AnimationManifestationStruct(AbstractQuestManifestation manifestation){
		this.manifestation = manifestation;
	}
	
	@Override public String toString(){
		return manifestation.getClass().getSimpleName();
	}
	
	public static Array<AnimationManifestationStruct> create(List<AbstractQuestManifestation> handlers){
		AnimationManifestationStruct[] structs = new AnimationManifestationStruct[handlers.size()];
		for(int i = 0; i<structs.length; i++)
			structs[i] = new AnimationManifestationStruct(handlers.get(i));
		return new Array<AnimationManifestationStruct>(structs);
	}
}
