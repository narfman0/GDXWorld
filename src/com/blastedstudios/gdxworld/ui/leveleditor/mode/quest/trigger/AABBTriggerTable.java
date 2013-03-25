package com.blastedstudios.gdxworld.ui.leveleditor.mode.quest.trigger;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AABBTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class AABBTriggerTable extends TriggerTable{
	private final VertexTable llTable,urTable;
	private final AABBTrigger trigger;
	
	public AABBTriggerTable(Skin skin, AABBTrigger trigger) {
		super(skin);
		this.trigger = trigger;
		llTable = new VertexTable(trigger.getLowerLeft(), skin, null);
		urTable = new VertexTable(trigger.getUpperRight(), skin, null);
		add(new Label("Lower left: ", skin));
		add(llTable);
		row();
		add(new Label("Upper right: ", skin));
		add(urTable);
	}

	@Override public AbstractQuestTrigger apply() {
		trigger.setLowerLeft(llTable.getVertex());
		trigger.setUpperRight(urTable.getVertex());
		return trigger;
	}
}
