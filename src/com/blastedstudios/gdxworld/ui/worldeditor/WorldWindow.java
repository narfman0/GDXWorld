package com.blastedstudios.gdxworld.ui.worldeditor;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.ui.MainScreen;
import com.blastedstudios.gdxworld.util.GDXWindow;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class WorldWindow extends GDXWindow{
	public WorldWindow(final GDXWorldEditor game, final Skin skin, final GDXWorld gdxWorld) {
		super("World Editor", skin);
		final Button clearButton = new TextButton("Clear", skin);
		final Button saveButton = new TextButton("Save", skin);
		final Button backButton = new TextButton("Back", skin);
		final Button exitButton = new TextButton("Exit", skin);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxWorld.clear();
			}
		});
		saveButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				final JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(null);
				gdxWorld.save(fc.getSelectedFile());
			}
		});
		backButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new MainScreen(game));
			}
		});
		exitButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		add(clearButton);
		row();
		add(saveButton);
		row();
		add(backButton);
		row();
		add(exitButton);
		pack();
		setY(Gdx.graphics.getHeight() - getHeight());
		setMovable(false);
	}
}
