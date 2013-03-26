package com.blastedstudios.gdxworld.plugin.mode.light;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.plugin.mode.light.typetable.AbstractLightTable;
import com.blastedstudios.gdxworld.plugin.mode.light.typetable.ConeLightTable;
import com.blastedstudios.gdxworld.plugin.mode.light.typetable.DirectionalLightTable;
import com.blastedstudios.gdxworld.plugin.mode.light.typetable.PointLightTable;
import com.blastedstudios.gdxworld.world.light.ConeLight;
import com.blastedstudios.gdxworld.world.light.DirectionalLight;
import com.blastedstudios.gdxworld.world.light.GDXLight;
import com.blastedstudios.gdxworld.world.light.PointLight;

class LightWindow extends AbstractWindow {
	private ColorTable colorTable;
	private final Map<AbstractLightTable,Button> lightTables;
	private Table lightTable = new Table();
	private Skin skin;
	
	public LightWindow(Skin skin, java.util.List<GDXLight> lights, Color ambient, 
			final LightMode lightMode, final LevelEditorScreen screen) {
		super("Light Editor", skin);
		this.skin = skin;
		lightTables = new HashMap<>();
		final List lightTypes = new List(LightType.values(), skin);
		final Button newButton = new TextButton("New", skin);
		newButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				addLightTable(LightType.values()[lightTypes.getSelectedIndex()], null);
			}
		});
		for(GDXLight light : screen.getLevel().getLights()){
			addLightTable(LightType.convert(light), light);
		}
		final Button applyButton = new TextButton("Apply", skin);
		applyButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.getLevel().setLightAmbient(GDXLight.convert(colorTable.parseColor()));
				screen.getLevel().getLights().clear();
				Gdx.app.debug("LightWindow.applyButton.ClickListener", "Cleared lights");
				for(GDXLight light : getLights())
					lightMode.addLight(light);
			}
		});
		Table ambientTable = new Table();
		ambientTable.add(new Label("Ambient: ", skin));
		ambientTable.add(colorTable = new ColorTable(ambient, skin));
		add(ambientTable);
		row();
		Table controlTable = new Table();
		controlTable.add(new Label("Type: ", skin));
		controlTable.add(lightTypes);
		controlTable.add(newButton);
		add(controlTable);
		row();
		final ScrollPane scrollPane = new ScrollPane(lightTable);
		add(scrollPane);
		row();
		add(applyButton);
		setMovable(false);
		setSize(Gdx.graphics.getWidth(), 500);
	}
	
	private void addLightTable(LightType lightType, GDXLight light){
		final AbstractLightTable table = createTable(lightType, light);
		final Button deleteButton = new TextButton("Delete", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				deleteButton.remove();
				table.remove();
				lightTables.remove(table).remove();
				lightTable.reset();
				for(Entry<AbstractLightTable, Button> entry : lightTables.entrySet()){
					lightTable.add(entry.getKey());
					lightTable.add(entry.getValue());
					lightTable.row();
				}
			}
		});
		lightTable.add(table);
		lightTable.add(deleteButton);
		lightTable.row();
		lightTables.put(table,deleteButton);
	}
	
	private AbstractLightTable createTable(LightType lightType, GDXLight light){
		switch(lightType){
		case Cone:
			ConeLight coneLight = light == null ? new ConeLight() : (ConeLight) light;
			return new ConeLightTable(skin, GDXLight.convert(coneLight.getColor()), coneLight.getRays(), 
					coneLight.getDistance(), coneLight.getCoordinates(), coneLight.getConeDegree(),
					coneLight.getDirectionDegree());
		case Directional:
			DirectionalLight directionalLight = light == null ? new DirectionalLight() : (DirectionalLight) light;
			return new DirectionalLightTable(skin, GDXLight.convert(directionalLight.getColor()), directionalLight.getRays(), 
					directionalLight.getDirection());
		case Point:
		default:
			PointLight pointLight = light == null ? new PointLight() : (PointLight) light;
			return new PointLightTable(skin, GDXLight.convert(pointLight.getColor()), pointLight.getRays(), 
					pointLight.getDistance(), pointLight.getCoordinates());
		}
	}

	private ArrayList<GDXLight> getLights() {
		ArrayList<GDXLight> lights = new ArrayList<>();
		for(AbstractLightTable table : lightTables.keySet())
			lights.add(table.create());
		return lights;
	}
	
	private enum LightType{
		Directional, Cone, Point;
		
		public static LightType convert(GDXLight light){
			String name = light.getClass().getSimpleName();
			return Enum.valueOf(LightType.class, name.substring(0,name.length()-5));
		}
	}
}
