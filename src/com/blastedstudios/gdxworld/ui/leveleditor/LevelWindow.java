package com.blastedstudios.gdxworld.ui.leveleditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.IMode;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelWindow extends AbstractWindow{
	private final List<CheckBox> modeCheckBoxes = new ArrayList<>();
	
	public LevelWindow(final Game game, final Skin skin, 
			final GDXWorld world, final GDXLevel gdxLevel, 
			final LevelEditorScreen levelEditorScreen, final File lastSaveFile){
		super("Level Editor", skin);
		final Button clearButton = new TextButton("Clear", skin);
		final Button backButton = new TextButton("Back", skin);
		add(new Label("Mode:", skin));
		try {
			for(IMode child : levelEditorScreen.getModes()){
				String name = child.getClass().getSimpleName().substring(0, child.getClass().getSimpleName().length()-4);
				final IMode mode = child;
				final CheckBox checkBox = new CheckBox(name, skin);
				checkBox.addListener(new ClickListener() {
					@Override public void clicked(InputEvent event, float x, float y) {
						resetState();
						checkBox.setChecked(true);
						levelEditorScreen.setMode(mode);
					}
				});
				add(checkBox);
				modeCheckBoxes.add(checkBox);
			}
			if(!modeCheckBoxes.isEmpty()){
				modeCheckBoxes.get(0).setChecked(true);
				levelEditorScreen.setMode(levelEditorScreen.getModes().iterator().next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxLevel.clear();
				levelEditorScreen.clear();
			}
		});
		backButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new WorldEditorScreen(game, world, lastSaveFile));
			}
		});
		add(clearButton);
		add(backButton);
		pack();
		setY(Gdx.graphics.getHeight() - getHeight());
	}
	
	private void resetState(){
		for(CheckBox box : modeCheckBoxes)
			box.setChecked(false);
	}
}
