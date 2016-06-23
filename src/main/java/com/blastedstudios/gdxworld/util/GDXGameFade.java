package com.blastedstudios.gdxworld.util;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.blastedstudios.gdxworld.ui.AbstractScreen;

public abstract class GDXGameFade {
	private static final float FADE_DURATION = Properties.getFloat("screen.fade.duration", 1f);
	private static final HashMap<AbstractScreen, Table> screenFadeTableMap = new HashMap<>(); 

	public static AbstractScreen fadeInScreen(GDXGame game, AbstractScreen screen){
		Table table = buildTable(Color.BLACK.cpy());
		table.addAction(Actions.fadeOut(FADE_DURATION));
		screen.getStage().addActor(table);
		if(screenFadeTableMap.containsKey(screen))
			screenFadeTableMap.get(screen).remove();
		screenFadeTableMap.put(screen, table);
		return screen;
	}
	
	public static AbstractScreen fadeInPushScreen(GDXGame game, AbstractScreen screen){
		game.pushScreen(fadeInScreen(game, screen));
		return screen;
	}
	
	public static AbstractScreen fadeOutScreen(final GDXGame game, AbstractScreen screen){
		Table table = buildTable(Color.CLEAR.cpy());
		table.addAction(Actions.fadeIn(FADE_DURATION));
		screen.getStage().addActor(table);
		if(screenFadeTableMap.containsKey(screen))
			screenFadeTableMap.get(screen).remove();
		screenFadeTableMap.put(screen, table);
		return screen;
	}
	
	public static AbstractScreen fadeOutPopScreen(final GDXGame game, final IPopListener listener){
		final AbstractScreen screen = fadeOutScreen(game, game.peekScreen());
		final AtomicInteger timeToPop = new AtomicInteger(Float.floatToIntBits(FADE_DURATION));
		screen.addRenderListener(new IScreenListener() {
			@Override public boolean render(float dt) {
				float timeRemaining = Float.intBitsToFloat(timeToPop.get()) - dt;
				if(timeRemaining <= 0){
					if(game.peekScreen() == screen)//ensure there aren't double pops for whatever reason
						game.popScreen();
					if(listener != null)
						listener.screenPopped();
					return true;
				}else
					timeToPop.set(Float.floatToIntBits(timeRemaining));
				return false;
			}
		});
		return screen;
	}
	
	public static Table buildTable(Color color){
		return buildTable(color, Color.BLACK);
	}
	
	public static Table buildTable(Color tableColor, Color pixmapColor){
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(pixmapColor);
		pixmap.fill();
		Table table = new Table();
		table.setWidth(Gdx.graphics.getWidth());
		table.setHeight(Gdx.graphics.getHeight());
		Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		table.setBackground(drawable);
		table.setColor(tableColor);
		return table;
	}
	
	public interface IScreenListener{
		/**
		 * @return true if time to remove listener
		 */
		boolean render(float dt);
	}
	
	public interface IPopListener{
		void screenPopped();
	}
}
