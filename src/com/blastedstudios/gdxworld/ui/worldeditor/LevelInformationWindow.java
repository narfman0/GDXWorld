package com.blastedstudios.gdxworld.ui.worldeditor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.blastedstudios.gdxworld.util.GDXGame;
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
	private final TextField coordXLabel, coordYLabel, levelNameLabel;
	private final Map<String,TextField> propertiesFields = new HashMap<>();
	private final WorldEditorScreen worldEditorScreen;
	private final GDXWorld gdxWorld;
	private final GDXLevel gdxLevel;
	
	public LevelInformationWindow(final GDXGame game, final WorldEditorScreen worldEditorScreen, 
			final Skin skin, final GDXWorld gdxWorld, final GDXLevel gdxLevel){
		super("Level Info", skin);
		this.gdxWorld = gdxWorld;
		this.gdxLevel = gdxLevel;
		this.worldEditorScreen = worldEditorScreen;
		levelNameLabel = new TextField(gdxLevel.getName(), skin);
		coordXLabel = new TextField(gdxLevel.getCoordinates().x+"", skin);
		coordYLabel = new TextField(gdxLevel.getCoordinates().y+"", skin);
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
				game.pushScreen(new LevelEditorScreen(game, gdxWorld, gdxLevel));
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
		for(Entry<String,String> entry : gdxLevel.getProperties().entrySet()){
			add(new Label(entry.getKey() + ": ", skin));
			TextField field = new TextField(entry.getValue(), skin);
			field.setMessageText("<" + entry.getKey().toLowerCase() + ">");
			propertiesFields.put(entry.getKey(), field);
			add(field);
			row();
		}
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
		gdxLevel.setCoordinates(getCoordinates());
		gdxLevel.setName(levelNameLabel.getText());
		for(String key : gdxLevel.getProperties().keySet())
			gdxLevel.getProperties().put(key, propertiesFields.get(key).getText());
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
	
	public Vector2 getCoordinates(){
		return new Vector2(
				Float.parseFloat(coordXLabel.getText()), 
				Float.parseFloat(coordYLabel.getText()));
	}
}
