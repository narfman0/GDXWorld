package com.blastedstudios.gdxworld.plugin.mode.animation;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.animation.GDXAnimations;

public class AnimationsWindow extends AbstractWindow{
	public AnimationsWindow(Skin skin, List<GDXAnimations> list, AnimationMode animationMode, LevelEditorScreen screen) {
		super("Animations Editor", skin);
		//make window, most similar to group window (similar with group editor as well)
		pack();
	}
}
