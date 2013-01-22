package com.blastedstudios.gdxworld.ui.leveleditor.windows;

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
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.CircleMouseMode;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;

public class CircleWindow extends GDXWindow {
	private final VertexTable centerTable;
	
	public CircleWindow(final Skin skin, final CircleMouseMode mouseMode, final GDXCircle circle) {
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
		final TextField radiusField = new TextField("", skin);
		radiusField.setMessageText("<radius>");
		radiusField.setText(circle.getRadius()+"");
		final TextField densityField = new TextField("", skin);
		densityField.setMessageText("<density, calculates mass>");
		densityField.setText(circle.getDensity()+"");
		final TextField frictionField = new TextField("", skin);
		frictionField.setMessageText("<friction>");
		frictionField.setText(circle.getFriction()+"");
		final TextField restitutionField = new TextField("", skin);
		restitutionField.setMessageText("<restitution>");
		restitutionField.setText(circle.getRestitution()+"");
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		centerTable = new VertexTable(circle.getCenter(), skin, null);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				circle.setName(nameField.getText());
				BodyType bodyType = BodyType.StaticBody;
				if(kinematicBox.isChecked())
					bodyType = BodyType.KinematicBody;
				else if(dynamicBox.isChecked())
					bodyType = BodyType.DynamicBody;
				circle.setCenter(centerTable.getVertex());
				circle.setRadius(Float.parseFloat(radiusField.getText()));
				circle.setBodyType(bodyType);
				circle.setDensity(Float.parseFloat(densityField.getText()));
				circle.setFriction(Float.parseFloat(frictionField.getText()));
				circle.setRestitution(Float.parseFloat(restitutionField.getText()));
				mouseMode.addCircle(circle);
				mouseMode.clean();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mouseMode.clean();
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mouseMode.removeCircle(circle);
				mouseMode.clean();
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
		add(new Label("Radius: ", skin));
		add(radiusField);
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
		controlTable.add(deleteButton);
		controlTable.add(cancelButton);
		add(controlTable).colspan(2);
		setMovable(false);
		pack();
	}

	public void setCenter(Vector2 center) {
		centerTable.setVertex(center.x, center.y);
	}
}
