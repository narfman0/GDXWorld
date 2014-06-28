package com.blastedstudios.gdxworld.plugin.quest.manifestation.physics;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class PhysicsManifestationTable extends ManifestationTable {
	private final TextField torqueText, nameText;
	private final VertexTable impulseTable;
	private final List<BodyType> bodyTypeList;
	private final PhysicsManifestation manifestation;
	
	public PhysicsManifestationTable(Skin skin, PhysicsManifestation manifestation) {
		super(skin);
		this.manifestation = manifestation;
		nameText = new TextField(manifestation.getName(), skin);
		nameText.setMessageText("<name>");
		torqueText = new TextField(manifestation.getTorque()+"", skin);
		torqueText.setMessageText("<torque>");
		impulseTable = new VertexTable(manifestation.getImpulse(), skin, null);
		bodyTypeList = new List<BodyType>(skin);
		bodyTypeList.setItems(BodyType.values());
		bodyTypeList.setSelectedIndex(manifestation.getType().getValue());
		add(new Label("Name", skin));
		add(nameText);
		row();
		add(new Label("Torque: ", skin));
		add(torqueText);
		row();
		add(new Label("Impulse: ", skin));
		add(impulseTable);
		row();
		add(new Label("Body Type: ", skin));
		add(bodyTypeList);
	}

	@Override public AbstractQuestManifestation apply() {
		manifestation.setTorque(Float.parseFloat(torqueText.getText()));
		manifestation.setName(nameText.getText());
		manifestation.setImpulse(impulseTable.getVertex());
		manifestation.setType(bodyTypeList.getSelected());
		return manifestation;
	}
}
