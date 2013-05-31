package com.blastedstudios.gdxworld.plugin.mode.circle;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;

public class CircleWindow extends AbstractWindow {
	private final VertexTable centerTable;
	private final CircleTable table;
	
	public CircleWindow(final Skin skin, final CircleMode mode, final GDXCircle circle) {
		super("Circle Editor", skin);
		final CheckBox staticBox = new CheckBox("Static", skin), 
				kinematicBox = new CheckBox("Kinematic", skin), 
				dynamicBox = new CheckBox("Dynamic", skin);
		switch(circle.getBodyType()){
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
		nameField.setMessageText("<circle name>");
		nameField.setText(circle.getName());
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		centerTable = new VertexTable(circle.getCenter().cpy(), skin, null);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				circle.setName(nameField.getText());
				BodyType bodyType = BodyType.StaticBody;
				if(kinematicBox.isChecked())
					bodyType = BodyType.KinematicBody;
				else if(dynamicBox.isChecked())
					bodyType = BodyType.DynamicBody;
				circle.setCenter(centerTable.getVertex());
				circle.setName(nameField.getText());
				circle.setBodyType(bodyType);
				table.apply(circle);
				mode.addCircle(circle);
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
				mode.removeCircle(circle);
				mode.clean();
			}
		});
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
		add(new Label("Center: ", skin));
		add(centerTable);
		row();
		add(table = new CircleTable(skin, circle)).colspan(2);
		row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(deleteButton);
		controlTable.add(cancelButton);
		add(controlTable).colspan(2);
		setMovable(false);
		pack();
	}

	public void setCenter(Vector2 center) {
		centerTable.setVertex(center.x, center.y);
	}
	
	public void render(float delta, Camera camera, ShapeRenderer renderer){

                // This appears to be about the minimum size the radius can be
	        // where you'll still get a closed figure (triangle).  Any
	        // smaller and it's a line, too small and it throws an
	        // exception.
	        final float MIN_RADIUS = 0.13f;

		renderer.setColor(new Color(.1f, 1, .1f, 1));
		if(table.getRadius() >= MIN_RADIUS) {
		    renderer.circle(centerTable.getVertex().x, centerTable.getVertex().y, table.getRadius());
		}
	}
}
