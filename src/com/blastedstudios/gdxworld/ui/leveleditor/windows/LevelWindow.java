package com.blastedstudios.gdxworld.ui.leveleditor.windows;

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
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.CircleMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.JointMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.LiveMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.NPCMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.PathMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.PolygonMouseMode;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelWindow extends GDXWindow{
	private final CheckBox polygonBox, npcBox, pathBox, jointBox, liveBox, circleBox;
	private final LevelEditorScreen levelEditorScreen;
	
	public LevelWindow(final GDXWorldEditor game, final Skin skin, 
			final GDXWorld world, final GDXLevel gdxLevel, 
			final LevelEditorScreen levelEditorScreen){
		super("Level Editor", skin);
		this.levelEditorScreen = levelEditorScreen;
		final Button clearButton = new TextButton("Clear", skin);
		final Button backButton = new TextButton("Back", skin);
		polygonBox = new CheckBox("Polygon", skin);
		circleBox = new CheckBox("Circle", skin);
		npcBox = new CheckBox("NPC", skin);
		pathBox = new CheckBox("Path", skin);
		jointBox = new CheckBox("Joint", skin);
		liveBox = new CheckBox("Live", skin);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxLevel.getShapes().clear();
			}
		});
		backButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new WorldEditorScreen(game, world));
			}
		});
		polygonBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				polygonBox.setChecked(true);
				levelEditorScreen.setMouseMode(new PolygonMouseMode(levelEditorScreen.getCamera(), levelEditorScreen));
			}
		});
		circleBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				circleBox.setChecked(true);
				levelEditorScreen.setMouseMode(new CircleMouseMode(levelEditorScreen.getCamera(), levelEditorScreen));
			}
		});
		npcBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				npcBox.setChecked(true);
				levelEditorScreen.setMouseMode(new NPCMouseMode(levelEditorScreen.getCamera(), levelEditorScreen));
			}
		});
		pathBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				pathBox.setChecked(true);
				levelEditorScreen.setMouseMode(new PathMouseMode(levelEditorScreen.getCamera(), levelEditorScreen));
			}
		});
		jointBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				jointBox.setChecked(true);
				levelEditorScreen.setMouseMode(new JointMouseMode(levelEditorScreen.getCamera(), levelEditorScreen));
			}
		});
		liveBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				liveBox.setChecked(true);
				levelEditorScreen.setLive(true);
				levelEditorScreen.setMouseMode(new LiveMouseMode(levelEditorScreen.getCamera(), levelEditorScreen));
			}
		});
		polygonBox.setChecked(true);
		add(clearButton);
		add(backButton);
		row();
		add(new Label("Mode:", skin));
		add(polygonBox);
		add(circleBox);
		add(npcBox);
		add(pathBox);
		add(jointBox);
		add(liveBox);
		pack();
		setY(Gdx.graphics.getHeight() - getHeight());
	}
	
	private void resetState(){
		levelEditorScreen.setLive(false);
		polygonBox.setChecked(false);
		circleBox.setChecked(false);
		npcBox.setChecked(false);
		pathBox.setChecked(false);
		jointBox.setChecked(false);
		liveBox.setChecked(false);
	}
}
