package com.blastedstudios.gdxworld.plugin.mode.animation.live;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.IMode;
import com.blastedstudios.gdxworld.world.quest.manifestation.IQuestManifestationExecutor;

public class AnimationLiveQuestExecutor implements IQuestManifestationExecutor{
	private final LevelEditorScreen screen;
	
	public AnimationLiveQuestExecutor(LevelEditorScreen screen){
		this.screen = screen;
	}

	@Override public Body getPhysicsObject(String name) {
		for(IMode mode : screen.getModes()){
			Body body = mode.getPhysicsBody(name);
			if(body != null)
				return body;
		}
		return null;
	}

	@Override public Joint getPhysicsJoint(String name) {
		for(IMode mode : screen.getModes()){
			Joint joint = mode.getPhysicsJoint(name);
			if(joint != null)
				return joint;
		}
		return null;
	}

	@Override public World getWorld() {
		return screen.getWorld();
	}
	
}