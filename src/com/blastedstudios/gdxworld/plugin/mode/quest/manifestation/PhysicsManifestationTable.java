package com.blastedstudios.gdxworld.plugin.mode.quest.manifestation;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.PhysicsManifestation;

@PluginImplementation
public class PhysicsManifestationTable extends ManifestationTable {
	public static final String BOX_TEXT = "Physics";
	private final TextField torqueText, nameText;
	private final VertexTable impulseTable;
	private final List bodyTypeList;
	private final PhysicsManifestation manifestation;
	
	public PhysicsManifestationTable(Skin skin, PhysicsManifestation manifestation) {
		super(skin);
		this.manifestation = manifestation;
		nameText = new TextField(manifestation.getName(), skin);
		nameText.setMessageText("<name>");
		torqueText = new TextField(manifestation.getTorque()+"", skin);
		torqueText.setMessageText("<torque>");
		impulseTable = new VertexTable(manifestation.getImpulse(), skin, null);
		bodyTypeList = new List(BodyType.values(), skin);
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
		manifestation.setType(BodyType.values()[bodyTypeList.getSelectedIndex()]);
		return manifestation;
	}
}
