package com.blastedstudios.gdxworld.world.animation;

import java.util.Comparator;

class AnimationStructTimeSorter implements Comparator<AnimationStruct>{
	@Override public int compare(AnimationStruct first, AnimationStruct second) {
		return ((Float)first.time).compareTo(second.time);
	}
}