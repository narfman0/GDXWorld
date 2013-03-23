package com.blastedstudios.gdxworld.ui.leveleditor.mousemode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.BackgroundWindow;
import com.blastedstudios.gdxworld.world.GDXBackground;

public class BackgroundMouseMode extends AbstractMouseMode {
	private BackgroundWindow backgroundWindow;
	private LevelEditorScreen screen;
	
	public BackgroundMouseMode(LevelEditorScreen screen){
		super(screen.getCamera());
		this.screen = screen;
	}

	public void addBackground(GDXBackground background) {
		Gdx.app.log("BackgroundMouseMode.addBackground", background.toString());
		if(!screen.getLevel().getBackgrounds().contains(background))
			screen.getLevel().getBackgrounds().add(background);
	}

	public void removeBackground(GDXBackground background) {
		Gdx.app.log("BackgroundMouseMode.removeBackground", background.toString());
		screen.getLevel().getBackgrounds().remove(background);
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("BackgroundMouseMode.touchDown", "x="+x+ " y="+y);
		GDXBackground background = screen.getLevel().getClosestBackground(coordinates.x, coordinates.y);
		if(background == null || background.getCoordinates().dst(coordinates.x, coordinates.y) > LevelEditorScreen.NODE_RADIUS)
			background = new GDXBackground();
		if(backgroundWindow == null)
			screen.getStage().addActor(backgroundWindow = new BackgroundWindow(screen.getSkin(), this, background));
		backgroundWindow.setCenter(new Vector2(coordinates.x, coordinates.y));
		return false;
	}
	
	@Override public boolean contains(float x, float y) {
		return backgroundWindow != null && backgroundWindow.contains(x, y);
	}

	@Override public void clean() {
		if(backgroundWindow != null)
			backgroundWindow.remove();
		backgroundWindow = null;
	}
}
