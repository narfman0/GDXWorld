package com.blastedstudios.gdxworld.util.panner;

import java.util.ArrayList;
import java.util.Collections;

import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.util.panner.ScreenLevelPanner.ITransitionListener;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class PannerManager implements ITransitionListener{
	private final ArrayList<GDXLevel> levelRotation;
	private final GDXRenderer gdxRenderer;
	private ScreenLevelPanner activePanner, loadingPanner;
	private int currentLevel = 0;
	
	public PannerManager(GDXWorld gdxWorld, GDXRenderer gdxRenderer){
		this.gdxRenderer = gdxRenderer;
		levelRotation = new ArrayList<>(gdxWorld.getLevels());
		Collections.shuffle(levelRotation);
		loadingPanner = new ScreenLevelPanner(getNextLevel(), gdxRenderer, this);
	}
	
	public void updatePanners(float dt){
		if(activePanner != null)
			activePanner.render(dt);
		if(loadingPanner != null)
			if(loadingPanner.update()){
				if(activePanner != null)
					activePanner.dispose();
				activePanner = loadingPanner;
				loadingPanner = null;
			}
	}

	@Override public void transition() {
		loadingPanner = new ScreenLevelPanner(getNextLevel(), gdxRenderer, this);
	}
	
	private GDXLevel getNextLevel(){
		currentLevel++;
		currentLevel %= levelRotation.size();
		return levelRotation.get(currentLevel);
	}

	public boolean ready() {
		return activePanner != null;
	}
}
