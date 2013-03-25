package com.blastedstudios.gdxworld.ui.leveleditor;

import java.io.File;

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
import com.blastedstudios.gdxworld.ui.leveleditor.mode.background.BackgroundMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.circle.CircleMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.joint.JointMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.live.LiveMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.npc.NPCMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.path.PathMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.polygon.PolygonMode;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.quest.QuestMode;
import com.blastedstudios.gdxworld.ui.worldeditor.WorldEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelWindow extends AbstractWindow{
	private final CheckBox polygonBox, circleBox, backgroundBox, npcBox, 
		pathBox, jointBox, liveBox, questBox;
	private final LevelEditorScreen levelEditorScreen;
	
	public LevelWindow(final GDXWorldEditor game, final Skin skin, 
			final GDXWorld world, final GDXLevel gdxLevel, 
			final LevelEditorScreen levelEditorScreen, final File lastSaveFile){
		super("Level Editor", skin);
		this.levelEditorScreen = levelEditorScreen;
		final Button clearButton = new TextButton("Clear", skin);
		final Button backButton = new TextButton("Back", skin);
		polygonBox = new CheckBox("Polygon", skin);
		circleBox = new CheckBox("Circle", skin);
		backgroundBox = new CheckBox("Background", skin);
		npcBox = new CheckBox("NPC", skin);
		pathBox = new CheckBox("Path", skin);
		jointBox = new CheckBox("Joint", skin);
		questBox = new CheckBox("Quest", skin);
		liveBox = new CheckBox("Live", skin);
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
		polygonBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				polygonBox.setChecked(true);
				levelEditorScreen.setMouseMode(new PolygonMode(levelEditorScreen));
			}
		});
		circleBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				circleBox.setChecked(true);
				levelEditorScreen.setMouseMode(new CircleMode(levelEditorScreen));
			}
		});
		backgroundBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				backgroundBox.setChecked(true);
				levelEditorScreen.setMouseMode(new BackgroundMode(levelEditorScreen));
			}
		});
		npcBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				npcBox.setChecked(true);
				levelEditorScreen.setMouseMode(new NPCMode(levelEditorScreen));
			}
		});
		pathBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				pathBox.setChecked(true);
				levelEditorScreen.setMouseMode(new PathMode(levelEditorScreen));
			}
		});
		jointBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				jointBox.setChecked(true);
				levelEditorScreen.setMouseMode(new JointMode(levelEditorScreen));
			}
		});
		questBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				questBox.setChecked(true);
				levelEditorScreen.setMouseMode(new QuestMode(levelEditorScreen));
			}
		});
		liveBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				resetState();
				liveBox.setChecked(true);
				levelEditorScreen.setLive(true);
				levelEditorScreen.setMouseMode(new LiveMode(levelEditorScreen));
			}
		});
		polygonBox.setChecked(true);
		add(new Label("Mode:", skin));
		add(polygonBox);
		add(circleBox);
		add(backgroundBox);
		add(npcBox);
		add(pathBox);
		add(jointBox);
		add(questBox);
		add(liveBox);
		add(clearButton);
		add(backButton);
		pack();
		setY(Gdx.graphics.getHeight() - getHeight());
	}
	
	private void resetState(){
		levelEditorScreen.setLive(false);
		polygonBox.setChecked(false);
		circleBox.setChecked(false);
		backgroundBox.setChecked(false);
		npcBox.setChecked(false);
		pathBox.setChecked(false);
		jointBox.setChecked(false);
		questBox.setChecked(false);
		liveBox.setChecked(false);
	}
}
