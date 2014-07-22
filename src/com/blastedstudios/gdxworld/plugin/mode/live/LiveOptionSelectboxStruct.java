package com.blastedstudios.gdxworld.plugin.mode.live;

import java.util.List;

import com.badlogic.gdx.utils.Array;

class LiveOptionSelectboxStruct {
	public final ILiveOptionProvider object;

	public LiveOptionSelectboxStruct(ILiveOptionProvider mode){
		this.object = mode;
	}

	@Override public String toString(){
		return object.getClass().getSimpleName();
	}
	
	public static Array<LiveOptionSelectboxStruct> create(List<ILiveOptionProvider> handlers){
		LiveOptionSelectboxStruct[] structs = new LiveOptionSelectboxStruct[handlers.size()];
		for(int i = 0; i<structs.length; i++)
			structs[i] = new LiveOptionSelectboxStruct(handlers.get(i));
		return new Array<LiveOptionSelectboxStruct>(structs);
	}
}
