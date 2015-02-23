package com.blastedstudios.gdxworld.plugin.quest.manifestation.wheeljoint;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.joint.window.wheel.WheelJointTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.world.joint.WheelJoint;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class WheelJointManifestationTable extends ManifestationTable {
	private final WheelJointTable table;
	
	public WheelJointManifestationTable(Skin skin, WheelJointManifestation manifestation) {
		super(skin);
		add(table = new WheelJointTable(skin, manifestation.getJoint()));
	}

	@Override public AbstractQuestManifestation apply() {
		WheelJoint joint = new WheelJoint();
		table.apply(joint);
		return new WheelJointManifestation(joint);
	}
}
