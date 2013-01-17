package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable.VertexRemoveListener;
import com.blastedstudios.gdxworld.world.GDXPolygon;

public class PolygonWindow extends GDXWindow implements VertexRemoveListener {
	private final Table vertexTables;
	private final Skin skin;
	private final GDXPolygon polygon;

	public PolygonWindow(final Skin skin, final LevelEditorScreen screen, 
			final GDXPolygon polygon) {
		super("Polygon Editor", skin);
		this.skin = skin;
		this.polygon = polygon;
		vertexTables = new Table(skin);
		final CheckBox staticBox = new CheckBox("Static", skin), 
				kinematicBox = new CheckBox("Kinematic", skin), 
				dynamicBox = new CheckBox("Dynamic", skin);
		switch(polygon.getBodyType()){
		case DynamicBody:
			dynamicBox.setChecked(true);
			break;
		case KinematicBody:
			kinematicBox.setChecked(true);
			break;
		case StaticBody:
			staticBox.setChecked(true);
			break;
		}
		dynamicBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				kinematicBox.setChecked(false);
				staticBox.setChecked(false);
			}
		});
		kinematicBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				dynamicBox.setChecked(false);
				staticBox.setChecked(false);
			}
		});
		staticBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				kinematicBox.setChecked(false);
				dynamicBox.setChecked(false);
			}
		});
		final TextField nameField = new TextField("", skin);
		nameField.setMessageText("<polygon name>");
		nameField.setText(polygon.getName());
		final TextField densityField = new TextField("", skin);
		densityField.setMessageText("<density, calculates mass>");
		densityField.setText(polygon.getDensity()+"");
		final TextField frictionField = new TextField("", skin);
		frictionField.setMessageText("<friction>");
		frictionField.setText(polygon.getFriction()+"");
		final TextField restitutionField = new TextField("", skin);
		restitutionField.setMessageText("<restitution>");
		restitutionField.setText(polygon.getRestitution()+"");
		final Button clearButton = new TextButton("Clear vertices", skin);
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		final ScrollPane scrollPane = new ScrollPane(vertexTables);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				polygon.getVertices().clear();
				vertexTables.clear();
			}
		});
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				polygon.setName(nameField.getText());
				BodyType bodyType = BodyType.StaticBody;
				if(kinematicBox.isChecked())
					bodyType = BodyType.KinematicBody;
				else if(dynamicBox.isChecked())
					bodyType = BodyType.DynamicBody;
				polygon.setBodyType(bodyType);
				polygon.setDensity(Float.parseFloat(densityField.getText()));
				polygon.setFriction(Float.parseFloat(frictionField.getText()));
				polygon.setRestitution(Float.parseFloat(restitutionField.getText()));
				screen.addPolygon(polygon);
				screen.removePolygonWindow();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.removePolygonWindow();
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.removePolygon(polygon);
				screen.removePolygonWindow();
			}
		});
		populateVertexTable();
		add(scrollPane).colspan(2);
		row();
		Table bodyTypeTable = new Table();
		bodyTypeTable.add(new Label("BodyType: ", skin));
		bodyTypeTable.add(staticBox);
		bodyTypeTable.add(kinematicBox);
		bodyTypeTable.add(dynamicBox);
		add(bodyTypeTable).colspan(2);
		row();
		add(new Label("Name: ", skin));
		add(nameField);
		row();
		add(new Label("Friction: ", skin));
		add(frictionField);
		row();
		add(new Label("Density: ", skin));
		add(densityField);
		row();
		add(new Label("Restitution: ", skin));
		add(restitutionField);
		row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(clearButton);
		controlTable.add(deleteButton);
		controlTable.add(cancelButton);
		add(controlTable).colspan(2);
		setMovable(false);
		setHeight(400);
		setWidth(400);
	}

	public void add(Vector2 vertex) {
		polygon.getVertices().add(vertex);
		vertexTables.add(new VertexTable(vertex, skin, this));
		vertexTables.row();
		Gdx.app.log("PolygonWindow.add", " vector: " + vertex + " size: " + polygon.getVertices().size());
	}

	public void remove(Vector2 vertex) {
		polygon.getVertices().remove(vertex);
		vertexTables.clear();
		populateVertexTable();
		Gdx.app.log("PolygonWindow.remove", " vector: " + vertex + " size: " + polygon.getVertices().size());
	}

	private void populateVertexTable(){
		for(Vector2 vertex : polygon.getVertices()){
			vertexTables.add(new VertexTable(vertex, skin, this));
			vertexTables.row();
		}
	}
}
