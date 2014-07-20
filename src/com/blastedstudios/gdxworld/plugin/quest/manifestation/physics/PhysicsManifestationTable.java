package com.blastedstudios.gdxworld.plugin.quest.manifestation.physics;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PhysicsManifestationTable extends ManifestationTable {
	private final TextField torqueText, nameText, angleText;
	private final VertexTable impulseVertexTable, positionVertexTable, velocityVertexTable;
	private final CheckBox hasPositionCheckbox, hasVelocityCheckbox, hasAngleCheckbox;
	private final List<BodyType> bodyTypeList;
	private final PhysicsManifestation manifestation;
	
	public PhysicsManifestationTable(Skin skin, PhysicsManifestation manifestation) {
		super(skin);
		this.manifestation = manifestation;
		nameText = new TextField(manifestation.getName(), skin);
		nameText.setMessageText("<body name>");
		torqueText = new TextField(manifestation.getTorque()+"", skin);
		torqueText.setMessageText("<torque>");
		impulseVertexTable = new VertexTable(manifestation.getImpulse(), skin, null);
		bodyTypeList = new List<BodyType>(skin);
		bodyTypeList.setItems(BodyType.values());
		bodyTypeList.setSelectedIndex(manifestation.getType().getValue());
		Table positionTable = new Table(), velocityTable = new Table(), angleTable = new Table();
		hasPositionCheckbox = new CheckBox("Has Position", skin);
		hasPositionCheckbox.setChecked(manifestation.isHasPosition());
		hasVelocityCheckbox = new CheckBox("Has Velocity", skin);
		hasVelocityCheckbox.setChecked(manifestation.isHasVelocity());
		hasAngleCheckbox = new CheckBox("Has Angle", skin);
		hasAngleCheckbox.setChecked(manifestation.isHasAngle());
		positionVertexTable = new VertexTable(manifestation.getPosition(), skin);
		velocityVertexTable = new VertexTable(manifestation.getVelocity(), skin);
		angleText = new TextField(manifestation.getAngle()+"", skin);
		
		add(new Label("Body Name", skin));
		add(nameText);
		row();
		add(new Label("Torque: ", skin));
		add(torqueText);
		row();
		add(new Label("Impulse: ", skin));
		add(impulseVertexTable);
		row();
		positionTable.add(hasPositionCheckbox);
		positionTable.add(new Label(" Position: ", skin));
		positionTable.add(positionVertexTable);
		add(positionTable).colspan(2);
		row();
		velocityTable.add(hasVelocityCheckbox);
		velocityTable.add(new Label(" Velocity: ", skin));
		velocityTable.add(velocityVertexTable);
		add(velocityTable).colspan(2);
		row();
		angleTable.add(hasAngleCheckbox);
		angleTable.add(new Label(" Angle: ", skin));
		angleTable.add(angleText);
		add(angleTable).colspan(2);
		row();
		add(new Label("Body Type: ", skin));
		add(bodyTypeList);
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setTorque(Float.parseFloat(torqueText.getText()));
		manifestation.setName(nameText.getText());
		manifestation.setImpulse(impulseVertexTable.getVertex());
		manifestation.setType(bodyTypeList.getSelected());
		manifestation.setHasAngle(hasAngleCheckbox.isChecked());
		manifestation.setHasPosition(hasPositionCheckbox.isChecked());
		manifestation.setHasVelocity(hasVelocityCheckbox.isChecked());
		manifestation.setPosition(positionVertexTable.getVertex());
		manifestation.setVelocity(velocityVertexTable.getVertex());
		manifestation.setAngle(Float.parseFloat(angleText.getText()));
		return manifestation;
	}
}
