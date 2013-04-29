package com.blastedstudios.gdxworld.ui;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.world.GDXWorld;

/**
 * This class includes all functionality surrounding temporary world saving/
 * loading. This will recover from jvm crashes and other similar fatal errors.
 */
public class TempWorldScreen extends AbstractScreen{
	private static Timer tempSaveTimer;
	private static GDXWorld gdxWorld;

	public TempWorldScreen(final Game game){
		super(game);
		final Button yesButton = new TextButton("Yes", skin);
		yesButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new WorldEditorScreen(game, GDXWorld.load(getTempSaveFile()), null));
			}
		});
		final Button noButton = new TextButton("No", skin);
		noButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				clean();
				game.setScreen(new MainScreen(game));
			}
		});
		Window window = new Window("Temporary File Present", skin);
		window.add(new Label("It appears GDXWorld did not gracefully exit " +
				"last run, and a temporary file has been found. Do you wish " +
				"to recover from it?", skin));
		window.row();
		Table controlTable = new Table();
		controlTable.add(yesButton);
		controlTable.add(noButton);
		window.add(controlTable);
		window.row();
		window.pack();
		window.setX(Gdx.graphics.getWidth()/2 - window.getWidth()/2);
		window.setY(Gdx.graphics.getHeight()/2 - window.getHeight()/2);
		stage.addActor(window);
	}
	
	@Override public void render(float delta){
		super.render(delta);
		stage.draw();
	}
	
	public static boolean isTempFilePresent(){
		return getTempSaveFile().exists();
	}
	
	public static void start(GDXWorld gdxWorld){
		TempWorldScreen.gdxWorld = gdxWorld;
		if(tempSaveTimer != null)
			clean();
		tempSaveTimer = new Timer("Temp save timer", true);
		tempSaveTimer.schedule(new TimerTask() {
			@Override public void run() {
				save();
			}
		}, 5000, 5000);
	}
	
	private static void save(){
		gdxWorld.save(getTempSaveFile());
	}
	
	private static File getTempSaveFile(){
		return new File(System.getProperty("java.io.tmpdir")+"/gdxWorld.xml");
	}

	public static void clean() {
		if(tempSaveTimer != null)
			tempSaveTimer.cancel();
		getTempSaveFile().delete();
	}
}
