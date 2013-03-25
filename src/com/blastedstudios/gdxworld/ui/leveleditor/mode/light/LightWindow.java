package com.blastedstudios.gdxworld.ui.leveleditor.mode.light;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.light.typetable.AbstractLightTable;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.light.typetable.ConeLightTable;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.light.typetable.DirectionalLightTable;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.light.typetable.PointLightTable;
import com.blastedstudios.gdxworld.world.folder.ConeLight;
import com.blastedstudios.gdxworld.world.folder.DirectionalLight;
import com.blastedstudios.gdxworld.world.folder.GDXLight;
import com.blastedstudios.gdxworld.world.folder.PointLight;

class LightWindow extends AbstractWindow {
	private ColorTable colorTable;
	private ArrayList<AbstractLightTable> lightTables;
	private Table lightTable = new Table();
	private Skin skin;
	
	public LightWindow(Skin skin, java.util.List<GDXLight> lights, Color ambient, 
			final LightMode lightMode, final LevelEditorScreen screen) {
		super("Light Editor", skin);
		this.skin = skin;
		lightTables = new ArrayList<>();
		final List lightTypes = new List(LightType.values(), skin);
		final Button newButton = new TextButton("New", skin);
		newButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				addLightTable(LightType.values()[lightTypes.getSelectedIndex()], null);
			}
		});
		for(GDXLight light : screen.getLevel().getLights()){
			String name = light.getClass().getSimpleName();
			addLightTable(Enum.valueOf(LightType.class, name.substring(0,name.length()-5)), light);
		}
		final Button applyButton = new TextButton("Apply", skin);
		applyButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.getLevel().setLightAmbient(colorTable.getColor().cpy());
				screen.getLevel().getLights().clear();
				for(GDXLight light : getLights())
					screen.getLevel().getLights().add(light);
			}
		});
		add(new Label("Ambient: ", skin));
		add(colorTable = new ColorTable(ambient, skin)).colspan(2);
		row();
		add(new Label("Type: ", skin));
		add(lightTypes);
		add(newButton);
		row();
		add(lightTable);
		setMovable(false);
		//pack();
		setSize(900, 500);
	}
	
	private void addLightTable(LightType lightType, GDXLight light){
		AbstractLightTable table = null;
		switch(lightType){
		case Cone:
			ConeLight coneLight = light == null ? new ConeLight() : (ConeLight) light;
			table = new ConeLightTable(skin, coneLight.getColor(), coneLight.getRays(), 
					coneLight.getDistance(), coneLight.getCoordinates(), coneLight.getConeDegree(),
					coneLight.getDirectionDegree());
			break;
		case Directional:
			DirectionalLight directionalLight = light == null ? new DirectionalLight() : (DirectionalLight) light;
			table = new DirectionalLightTable(skin, directionalLight.getColor(), directionalLight.getRays(), 
					directionalLight.getDirection());
			break;
		case Point:
			PointLight pointLight = light == null ? new PointLight() : (PointLight) light;
			table = new PointLightTable(skin, pointLight.getColor(), pointLight.getRays(), 
					pointLight.getDistance(), pointLight.getCoordinates());
			break;
		}
		lightTable.add(table).colspan(4);
		lightTables.add(table);
		lightTable.row();
	}

	private ArrayList<GDXLight> getLights() {
		ArrayList<GDXLight> lights = new ArrayList<>();
		for(AbstractLightTable table : lightTables)
			lights.add(table.create());
		return lights;
	}
	
	private enum LightType{
		Directional, Cone, Point
	}
}
