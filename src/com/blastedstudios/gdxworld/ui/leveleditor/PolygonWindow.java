package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.util.GDXWindow;
import com.blastedstudios.gdxworld.world.GDXPolygon;

public class PolygonWindow extends GDXWindow {
	private final Table vertexTables;
	private final Skin skin;
	private final GDXPolygon polygon;

	public PolygonWindow(final Skin skin, final LevelEditorScreen screen, 
			final GDXPolygon polygon) {
		super("Polygon Editor", skin);
		this.skin = skin;
		this.polygon = polygon;
		vertexTables = new Table(skin);
		final Button clearButton = new TextButton("Clear", skin);
		final Button backButton = new TextButton("Back", skin);
		final ScrollPane scrollPane = new ScrollPane(vertexTables);
		scrollPane.setSize(200, 100);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				polygon.getVertices().clear();
				vertexTables.clear();
			}
		});
		backButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.removePolygonWindow();
			}
		});
		populateVertexTable();
		add(scrollPane).colspan(2);
		row();
		add(clearButton);
		add(backButton);
		setMovable(false);
	}

	public void add(Vector2 vertex) {
		polygon.getVertices().add(vertex);
		vertexTables.add(new VertexTable(vertex, skin, this));
		vertexTables.row();
		Gdx.app.debug("PolygonWindow.add", " vector: " + vertex + " size: " + polygon.getVertices().size());
	}

	public void remove(Vector2 vertex) {
		polygon.getVertices().remove(vertex);
		vertexTables.clear();
		populateVertexTable();
		Gdx.app.debug("PolygonWindow.remove", " vector: " + vertex + " size: " + polygon.getVertices().size());
	}

	private void populateVertexTable(){
		for(Vector2 vertex : polygon.getVertices()){
			vertexTables.add(new VertexTable(vertex, skin, this));
			vertexTables.row();
		}
	}
}
