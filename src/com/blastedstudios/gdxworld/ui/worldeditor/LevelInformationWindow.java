package com.blastedstudios.gdxworld.ui.worldeditor;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXWorld;

public class LevelInformationWindow extends AbstractWindow{
	private final TextField coordXLabel, coordYLabel, levelNameLabel, prereqLabel;
	private final WorldEditorScreen worldEditorScreen;
	private final GDXWorld gdxWorld;
	private final GDXLevel gdxLevel;
	
	public LevelInformationWindow(final Game game, final WorldEditorScreen worldEditorScreen, 
			final Skin skin, final GDXWorld gdxWorld, final GDXLevel gdxLevel, final File lastSavedFile){
		super("Level Info", skin);
		this.gdxWorld = gdxWorld;
		this.gdxLevel = gdxLevel;
		this.worldEditorScreen = worldEditorScreen;
		levelNameLabel = new TextField(gdxLevel.getName(), skin);
		coordXLabel = new TextField(gdxLevel.getCoordinates().x+"", skin);
		coordYLabel = new TextField(gdxLevel.getCoordinates().y+"", skin);
		prereqLabel = new TextField(gdxLevel.getPrerequisitesString(), skin);
		prereqLabel.setMessageText("<level prerequisites>");
		levelNameLabel.setMessageText("<new level name>");
		final Button acceptButton = new TextButton("Accept", skin);
		final Button editButton = new TextButton("Edit", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				addLevel();
			}
		});
		editButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				addLevel();
				game.setScreen(new LevelEditorScreen(game, gdxWorld, gdxLevel, lastSavedFile));
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				gdxWorld.remove(gdxLevel);
				worldEditorScreen.removeLevelInformationWindow();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				worldEditorScreen.removeLevelInformationWindow();
			}
		});
		add(new Label("Name: ", skin));
		add(levelNameLabel);
		row();
		add(new Label("Coordinates: ", skin));
		add(coordXLabel);
		add(coordYLabel);
		row();
		add(new Label("Prerequisites: ", skin));
		add(prereqLabel);
		row();
		add(acceptButton);
		add(editButton);
		add(deleteButton);
		add(cancelButton);
		pack();
		setMovable(false);
	}
	
	/**
	 * Adds level with current ui parameters
	 */
	private void addLevel(){
		gdxLevel.setCoordinates(new Vector2(Float.parseFloat(coordXLabel.getText()), Float.parseFloat(coordYLabel.getText())));
		gdxLevel.setName(levelNameLabel.getText());
		gdxLevel.setPrerequisitesString(prereqLabel.getText());
		if(!gdxWorld.contains(gdxLevel))
			gdxWorld.add(gdxLevel);
		worldEditorScreen.removeLevelInformationWindow();
	}
	
	public GDXLevel getLevel(){
		return gdxLevel;
	}
	
	public void setCoordinates(float x, float y){
		coordXLabel.setText(x+"");
		coordYLabel.setText(y+"");
	}
}
