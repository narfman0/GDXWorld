package com.blastedstudios.gdxworld.ui.leveleditor;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelWindow extends AbstractWindow{
	private final List<CheckBox> modeCheckBoxes = new ArrayList<>();
	
	public LevelWindow(final GDXWorldEditor game, final Skin skin, 
			final GDXWorld world, final GDXLevel gdxLevel, 
			final LevelEditorScreen levelEditorScreen, final File lastSaveFile){
		super("Level Editor", skin);
		final Button clearButton = new TextButton("Clear", skin);
		final Button backButton = new TextButton("Back", skin);
		add(new Label("Mode:", skin));
		try {
			for(final Class<? extends AbstractMode> childClass : AbstractMode.getChildClasses()){
				String name = childClass.getSimpleName().substring(0, childClass.getSimpleName().length()-4);
				final CheckBox checkBox = new CheckBox(name, skin);
				checkBox.addListener(new ClickListener() {
					@Override public void clicked(InputEvent event, float x, float y) {
						resetState();
						checkBox.setChecked(true);
						changeMode(levelEditorScreen, childClass);
					}
				});
				add(checkBox);
				modeCheckBoxes.add(checkBox);
			}
			if(!modeCheckBoxes.isEmpty()){
				modeCheckBoxes.get(0).setChecked(true);
				Class<? extends AbstractMode> mode = AbstractMode.getChildClasses().iterator().next();
				changeMode(levelEditorScreen, mode);
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
	
	private void changeMode(LevelEditorScreen screen, Class<? extends AbstractMode> child){
		try {
			Constructor<? extends AbstractMode> c = child.getConstructor(LevelEditorScreen.class);
			AbstractMode mode = c.newInstance(screen);
			screen.setMouseMode(mode);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void resetState(){
		for(CheckBox box : modeCheckBoxes)
			box.setChecked(false);
	}
}
