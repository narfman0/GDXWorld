package com.blastedstudios.gdxworld.plugin.quest.manifestation.platform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PlatformManifestationTable extends ManifestationTable{
	private final PlatformManifestation manifestation;
	private final TextField nameField, waitDurationField, jointField, 
		maxMotorForceAField, maxMotorForceBField, motorSpeedAField, motorSpeedBField;
	private final VertexTable pointATable, pointBTable;
	private Object clickTarget;
	
	public PlatformManifestationTable(Skin skin, PlatformManifestation manifestation) {
		super(skin);
		this.manifestation = manifestation;
		nameField = new TextField(manifestation.getName(), skin);
		nameField.setMessageText("<name>");
		waitDurationField = new TextField(manifestation.getWaitDuration()+"", skin);
		waitDurationField.setMessageText("<wait duration>");
		jointField = new TextField(manifestation.getJoint(), skin);
		jointField.setMessageText("<joint>");
		maxMotorForceAField = new TextField(manifestation.getMaxMotorForceA()+"", skin);
		maxMotorForceAField.setMessageText("<max motor force towards a>");
		motorSpeedAField = new TextField(manifestation.getMotorSpeedA()+"", skin);
		motorSpeedAField.setMessageText("<motor speed towards a>");
		maxMotorForceBField = new TextField(manifestation.getMaxMotorForceB()+"", skin);
		maxMotorForceBField.setMessageText("<max motor force towards b>");
		motorSpeedBField = new TextField(manifestation.getMotorSpeedB()+"", skin);
		motorSpeedBField.setMessageText("<motor speed towards b>");
		pointATable = new VertexTable(manifestation.getPointA(), skin, null);
		pointBTable = new VertexTable(manifestation.getPointB(), skin, null);

		TextButton nameButton = new TextButton("+", skin);
		nameButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				clickTarget = nameField;
			}
		});
		TextButton jointButton = new TextButton("+", skin);
		jointButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				clickTarget = jointField;
			}
		});
		TextButton pointAButton = new TextButton("+", skin);
		pointAButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				clickTarget = pointATable;
			}
		});
		TextButton pointBButton = new TextButton("+", skin);
		pointBButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				clickTarget = pointBTable;
			}
		});

		add(new Label("Body Name", skin));
		add(nameField);
		add(nameButton);
		row();
		add(new Label("Wait Duration", skin));
		add(waitDurationField);
		row();
		add(new Label("Joint Name", skin));
		add(jointField);
		add(jointButton);
		row();
		add(new Label("Max Motor Force A", skin));
		add(maxMotorForceAField);
		row();
		add(new Label("Motor Speed A", skin));
		add(motorSpeedAField);
		row();
		add(new Label("Max Motor Force B", skin));
		add(maxMotorForceBField);
		row();
		add(new Label("Motor Speed B", skin));
		add(motorSpeedBField);
		row();
		add(new Label("Point A", skin));
		add(pointATable);
		add(pointAButton);
		row();
		add(new Label("Point B", skin));
		add(pointBTable);
		add(pointBButton);
		row();
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setName(nameField.getText());
		manifestation.setPointA(pointATable.getVertex());
		manifestation.setPointB(pointBTable.getVertex());
		manifestation.setWaitDuration(Long.parseLong(waitDurationField.getText()));
		manifestation.setJoint(jointField.getText());
		manifestation.setMaxMotorForceA(Float.parseFloat(maxMotorForceAField.getText()));
		manifestation.setMaxMotorForceB(Float.parseFloat(maxMotorForceBField.getText()));
		manifestation.setMotorSpeedA(Float.parseFloat(motorSpeedAField.getText()));
		manifestation.setMotorSpeedB(Float.parseFloat(motorSpeedBField.getText()));
		return manifestation;
	}
	
	public void touched(Vector2 coordinates){
		if(clickTarget == nameField)
			Gdx.app.log("PlatformManifestationTable.touched", "Not implemented, need to pass GDXLevel in");
		else if(clickTarget == jointField)
			Gdx.app.log("PlatformManifestationTable.touched", "Not implemented, need to pass GDXLevel in");
		else if(clickTarget == pointATable)
			pointATable.setVertex(coordinates.x, coordinates.y);
		else if(clickTarget == pointBTable)
			pointBTable.setVertex(coordinates.x, coordinates.y);
	}
}
