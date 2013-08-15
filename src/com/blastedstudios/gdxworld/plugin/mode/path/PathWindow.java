package com.blastedstudios.gdxworld.plugin.mode.path;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable.VertexRemoveListener;
import com.blastedstudios.gdxworld.world.GDXPath;

class PathWindow extends AbstractWindow implements VertexRemoveListener {
	private final Table vertexTables;
	private final Skin skin;
	private final List<Vector2> nodes;

	public PathWindow(final Skin skin, final PathMode mode, 
			final GDXPath path) {
		super("Path Editor", skin);
		this.skin = skin;
		nodes = new ArrayList<>();
		vertexTables = new Table(skin);
		final TextField nameField = new TextField("", skin);
		nameField.setMessageText("<path name>");
		nameField.setText(path.getName());
		final Button clearButton = new TextButton("Clear vertices", skin);
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		final ScrollPane scrollPane = new ScrollPane(vertexTables);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				vertexTables.clear();
				nodes.clear();
			}
		});
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				path.setName(nameField.getText());
				path.setNodes(nodes);
				mode.addPath(path);
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
				mode.removePath(path);
				mode.clean();
			}
		});
		for(Vector2 node : path.getNodes())
			nodes.add(node.cpy());
		populateVertexTable();
		add(scrollPane).colspan(3);
		row();
		add(new Label("Name: ", skin));
		add(nameField);
		add(clearButton);
		row();
		add(acceptButton);
		add(cancelButton);
		add(deleteButton);
		setMovable(false);
		setHeight(400);
		setWidth(400);
	}

	public void add(Vector2 vertex) {
		nodes.add(vertex);
		vertexTables.add(new VertexTable(vertex, skin, this));
		vertexTables.row();
		Gdx.app.log("PathWindow.add", " vector: " + vertex);
	}

	public void remove(Vector2 vertex) {
		nodes.remove(vertex);
		vertexTables.clear();
		populateVertexTable();
		Gdx.app.log("PathWindow.remove", " vector: " + vertex);
	}

	private void populateVertexTable(){
		for(Vector2 vertex : nodes){
			vertexTables.add(new VertexTable(vertex, skin, this));
			vertexTables.row();
		}
	}
	
	public List<Vector2> getNodes(){
		return nodes;
	}
}
