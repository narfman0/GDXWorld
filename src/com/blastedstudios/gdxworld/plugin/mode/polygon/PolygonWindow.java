package com.blastedstudios.gdxworld.plugin.mode.polygon;

import java.util.ArrayList;
import java.util.List;

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
import com.blastedstudios.gdxworld.math.PolygonUtils;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable.VertexRemoveListener;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

class PolygonWindow extends AbstractWindow implements VertexRemoveListener {
	private final Table vertexTables;
	private final Skin skin;
	private List<Vector2> vertices;
	private final ShapeTable shapeTable;

	public PolygonWindow(final Skin skin, final PolygonMode mode, 
			final GDXPolygon polygon) {
		super("Polygon Editor", skin);
		this.skin = skin;
		this.vertices = new ArrayList<Vector2>(polygon.getVerticesAbsolute());
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
		shapeTable = new ShapeTable(skin, polygon);
		final Button clearButton = new TextButton("Clear vertices", skin);
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		final ScrollPane scrollPane = new ScrollPane(vertexTables);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				vertices.clear();
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
				polygon.setCenter(PolygonUtils.getCenter(vertices));
				polygon.setVerticesAbsolute(vertices);
				polygon.setBodyType(bodyType);
				shapeTable.apply(polygon);
				if(mode.addPolygon(polygon))
					mode.clean();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.clean();
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.removePolygon(polygon);
				mode.clean();
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
		add(shapeTable).colspan(2);
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
		vertices.add(vertex);
		addToVertexTables(vertex);
		Gdx.app.log("PolygonWindow.add", " vector: " + vertex + " size: " + vertices.size());
	}

	public void remove(Vector2 vertex) {
		vertices.remove(vertex);
		repopulate();
		Gdx.app.log("PolygonWindow.remove", " vector: " + vertex + " size: " + vertices.size());
	}
	
	public List<Vector2> getVertices(){
		return vertices;
	}

	private void populateVertexTable(){
		for(Vector2 vertex : vertices)
			addToVertexTables(vertex);
	}
	
	private void addToVertexTables(Vector2 vertex){
		VertexTable table = new VertexTable(vertex, skin, this); 
		vertexTables.add(table);
		vertexTables.row();
		table.setDisabled(true);
	}

	public void repopulate() {
		vertexTables.clear();
		populateVertexTable();
	}
}
