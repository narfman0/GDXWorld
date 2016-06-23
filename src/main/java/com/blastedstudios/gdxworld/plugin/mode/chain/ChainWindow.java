package com.blastedstudios.gdxworld.plugin.mode.chain;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.circle.CircleTable;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.RevoluteJoint;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class ChainWindow extends AbstractWindow {
	private static int chainCount = 0;
	private final VertexTable startTable, endTable;
	private final TextField distanceField, nameField;
	private final CircleTable circleTable;
	private final RectangleTable rectangleTable;
	private final Table shapeTable;
	private final CheckBox circleBox, rectangleBox;
	
	public ChainWindow(final Skin skin, final GDXLevel level) {
		super("Chain Editor", skin);
		startTable = new VertexTable(new Vector2(), skin);
		endTable = new VertexTable(new Vector2(), skin);
		nameField = new TextField("Chain-" + chainCount++, skin);
		nameField.setMessageText("<chain name>");
		distanceField = new TextField(Properties.get("level.chain.distance", "1"), skin);
		distanceField.setMessageText("<distance to next shape in chain");
		final Button createButton = new TextButton("Create", skin);
		createButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				createChain(level);
			}
		});
		
		circleBox = new CheckBox("Circle", skin);
		rectangleBox = new CheckBox("Rectangle", skin);
		rectangleBox.setChecked(true);
		circleBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				rectangleBox.setChecked(false);
				circleBox.setChecked(true);
				shapeTable.reset();
				shapeTable.add(circleTable);
			}
		});
		rectangleBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				circleBox.setChecked(false);
				rectangleBox.setChecked(true);
				shapeTable.reset();
				shapeTable.add(rectangleTable);
			}
		});
		shapeTable = new Table(skin);
		circleTable = new CircleTable(skin, new GDXCircle());
		rectangleTable = new RectangleTable(skin, new GDXPolygon());
		shapeTable.add(rectangleTable);
		
		Table extraTable = new Table();
		extraTable.add(new Label("Name: ", skin));
		extraTable.add(nameField);
		extraTable.row();
		extraTable.add(new Label("Start: ", skin));
		extraTable.add(startTable);
		extraTable.row();
		extraTable.add(new Label("End: ", skin));
		extraTable.add(endTable);
		extraTable.row();
		extraTable.add(new Label("Frequency: ", skin));
		extraTable.add(distanceField);
		extraTable.row();
		extraTable.add(circleBox);
		extraTable.add(rectangleBox);
		add(extraTable);
		row();
		add(shapeTable).colspan(2);
		row();
		add(createButton);
		setMovable(false);
		pack();
	}

	public void render(float delta, Camera camera){
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.ORANGE);
		if(circleBox.isChecked()){
			float radius = circleTable.getRadius();
			Vector2 dir = endTable.getVertex().cpy().sub(startTable.getVertex()).nor();
			for(float i=0; i<startTable.getVertex().dst(endTable.getVertex()); i+=parseFrequency()){
				Vector2 coordinates = startTable.getVertex().cpy().add(dir.cpy().scl(i));
				renderer.circle(coordinates.x, coordinates.y, radius);
			}
		}else{
			Vector2 dir = endTable.getVertex().cpy().sub(startTable.getVertex()).nor();
			for(float i=0; i<startTable.getVertex().dst(endTable.getVertex()); i+=parseFrequency()){
				Vector2 coordinates = startTable.getVertex().cpy().add(dir.cpy().scl(i));
				renderer.box(coordinates.x, coordinates.y, 0, rectangleTable.parseWidth(), rectangleTable.parseHeight(), 0);
			}
		}
		renderer.end();
	}
	
	public void touched(float x, float y){
		if(startTable.isCursorActive())
			startTable.setVertex(x, y);
		if(endTable.isCursorActive())
			endTable.setVertex(x, y);
	}
	
	private float parseFrequency(){
		try{
			return Float.parseFloat(distanceField.getText());
		}catch(Exception e){
			return 1f;
		}
	}
	
	private void createChain(GDXLevel level){
		List<GDXShape> shapes = new ArrayList<>();
		Vector2 dir = endTable.getVertex().cpy().sub(startTable.getVertex()).nor();
		for(float i=0; i<startTable.getVertex().dst(endTable.getVertex()); i+=parseFrequency()){
			Vector2 coordinates = startTable.getVertex().cpy().add(dir.cpy().scl(i));
			if(circleBox.isChecked()){
				GDXCircle circle = new GDXCircle();
				circle.setBodyType(BodyType.DynamicBody);
				circle.setCenter(coordinates);
				circle.setRadius(circleTable.getRadius());
				circleTable.apply(circle);
				level.getCircles().add(circle);
				shapes.add(circle);
			}else if(rectangleBox.isChecked()){
				GDXPolygon rectangle = new GDXPolygon();
				rectangle.setBodyType(BodyType.DynamicBody);
				rectangle.setCenter(coordinates);
				rectangleTable.apply(rectangle);
				level.getPolygons().add(rectangle);
				shapes.add(rectangle);
			}else
				Log.log("ChainWindow.createChain", "Neither rectangle nor circle box selected!");
		}
		for(int i=0; i<shapes.size(); i++)
			shapes.get(i).setName(nameField.getText() + "-" + i);
		for(int i=1; i<shapes.size(); i++)
			level.getJoints().add(attach(shapes.get(i-1), shapes.get(i), i-1, nameField.getText()));
	}
	
	private static RevoluteJoint attach(GDXShape lastShape, GDXShape shape, int count, String prefix){
		RevoluteJoint joint = new RevoluteJoint();
		joint.setBodyA(lastShape.getName());
		joint.setBodyB(shape.getName());
		joint.setAnchor(shape.getCenter().cpy().add(lastShape.getCenter()).scl(.5f));
		joint.setName(prefix + "-" + count);
		return joint;
	}
}
