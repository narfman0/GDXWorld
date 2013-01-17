package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelWindow extends GDXWindow{
	private final CheckBox polygonBox, npcBox, pathBox;
	
	public LevelWindow(final GDXWorldEditor game, final Skin skin, 
			final GDXWorld world, final GDXLevel gdxLevel){
		super("Level Editor", skin);
		final Button clearButton = new TextButton("Clear", skin);
		final Button backButton = new TextButton("Back", skin);
		polygonBox = new CheckBox("Polygon", skin);
		npcBox = new CheckBox("NPC", skin);
		pathBox = new CheckBox("Path", skin);
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
		polygonBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				npcBox.setChecked(false);
				pathBox.setChecked(false);
			}
		});
		npcBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				polygonBox.setChecked(false);
				pathBox.setChecked(false);
			}
		});
		pathBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				npcBox.setChecked(false);
				polygonBox.setChecked(false);
			}
		});
		polygonBox.setChecked(true);
		add(clearButton);
		add(backButton);
		row();
		add(new Label("Mode:", skin));
		add(polygonBox);
		add(npcBox);
		add(pathBox);
		pack();
		setY(Gdx.graphics.getHeight() - getHeight());
	}
	
	public boolean isPolygonMode(){
		return polygonBox.isChecked();
	}
	
	public boolean isNPCMode(){
		return npcBox.isChecked();
	}
	
	public boolean isPathMode(){
		return pathBox.isChecked();
	}
}
