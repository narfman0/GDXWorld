package com.blastedstudios.gdxworld.world.animation;

import java.util.Collections;
import java.util.LinkedList;

import com.blastedstudios.gdxworld.world.quest.manifestation.IQuestManifestationExecutor;

/**
 * Handler for a single GDXAnimation, loops and such
 */
public class GDXAnimationHandler {
	private final GDXAnimations animations;
	private final IQuestManifestationExecutor executor;
	private GDXAnimation currentAnimation = null;
	private LinkedList<AnimationStruct> currentAnimations;
	private float currentTime;
	private boolean active;
	
	public GDXAnimationHandler(GDXAnimations animations, IQuestManifestationExecutor executor){
		this.animations = animations;
		this.executor = executor;
		for(GDXAnimation animation : animations.getAnimations())
			if(animation.getName().equals(animations.getDefaultAnimation()))
				applyCurrentAnimation(animations.getAnimation(animations.getDefaultAnimation()), 0f);
	}
	
	public void applyCurrentAnimation(GDXAnimation currentAnimation, float offset) {
		this.currentAnimation = currentAnimation;
		currentAnimations = new LinkedList<>(currentAnimation.getAnimations());
		Collections.sort(currentAnimations, new AnimationStructTimeSorter());
		currentTime = offset;
		for(AnimationStruct struct : currentAnimations)
			struct.manifestation.setExecutor(executor);
		active = true;
	}

	public void render(float dt){
		if(active){
			currentTime += dt;
			if(currentAnimation != null){
				//since this is sorted, first will always be next to go
				while(currentAnimations.getFirst().time <= currentTime){
					AnimationStruct current = currentAnimations.poll();
					current.manifestation.execute();
				}
				if(currentTime >= currentAnimation.getTotalTime()){
					if(currentAnimation.isRepeat())
						applyCurrentAnimation(currentAnimation, currentTime - currentAnimation.getTotalTime());
					else
						applyCurrentAnimation(animations.getAnimation(animations.getDefaultAnimation()), 0f);
				}
			}
		}
	}

	public GDXAnimations getAnimations() {
		return animations;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public GDXAnimation getCurrentAnimation() {
		return currentAnimation;
	}
}
