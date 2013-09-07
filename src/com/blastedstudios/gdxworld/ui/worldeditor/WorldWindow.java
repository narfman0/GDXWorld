package com.blastedstudios.gdxworld.ui.worldeditor;

import java.io.File;

import com.blastedstudios.gdxworld.util.GDXGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.TempWorldScreen;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class WorldWindow extends AbstractWindow{
	private File lastSavedFile;
	
	public WorldWindow(final GDXGame game, final Skin skin, final GDXWorld gdxWorld, 
			File savedFile) {
		super("World Editor", skin);
		this.lastSavedFile = savedFile;
		final Button clearButton = new TextButton("Clear", skin);
		final Button saveButton = new TextButton("Save", skin);
		final Button saveAsButton = new TextButton("Save As...", skin);
		final Button backButton = new TextButton("Back", skin);
		final Button exitButton = new TextButton("Exit", skin);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxWorld.clear();
			}
		});
		saveButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxWorld.save(lastSavedFile);
			}
		});
		saveAsButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxWorld.save(lastSavedFile = FileUtil.fileChooser(false, true));
				saveButton.setDisabled(false);
			}
		});
		backButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				TempWorldScreen.clean();
				game.popScreen();
			}
		});
		exitButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				TempWorldScreen.clean();
				Gdx.app.exit();
			}
		});
		saveButton.setDisabled(lastSavedFile == null || !lastSavedFile.canRead());
		add(clearButton);
		row();
		add(saveButton);
		row();
		add(saveAsButton);
		row();
		add(backButton);
		row();
		add(exitButton);
		pack();
		setY(Gdx.graphics.getHeight() - getHeight());
		setMovable(false);
	}
	
	public File getSavedFile(){
		return lastSavedFile;
	}
}
