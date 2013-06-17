package com.blastedstudios.gdxworld.plugin.mode.quest.trigger;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AABBTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class AABBTriggerTable extends TriggerTable{
	public static final String BOX_TEXT = "AABB";
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

	@Override public void touched(Vector2 coordinates) {
		if(llTable.isCursorActive())
			llTable.setVertex(coordinates.x, coordinates.y);
		if(urTable.isCursorActive())
			urTable.setVertex(coordinates.x, coordinates.y);
	}
}
