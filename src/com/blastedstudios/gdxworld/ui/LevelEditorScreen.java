package com.blastedstudios.gdxworld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelEditorScreen extends AbstractScreen<GDXWorldEditor> {
	public LevelEditorScreen(final GDXWorldEditor game, final GDXWorld world, final GDXLevel gdxLevel){
		super(game);
		final Button clearButton = new TextButton("Clear", skin);
		final Button backButton = new TextButton("Back", skin);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxLevel.clear();
			}
		});
		backButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new WorldEditorScreen(game, world));
			}
		});
		Window window = new Window("Level Editor", skin);
		window.add(clearButton);
		window.row();
		window.add(backButton);
		window.pack();
		window.setY(Gdx.graphics.getHeight() - window.getHeight());
		stage.addActor(window);
	}
}