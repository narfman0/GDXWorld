package com.blastedstudios.gdxworld.plugin.quest.trigger.aabb;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.plugin.mode.quest.TriggerTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class AABBTriggerTable extends TriggerTable{
	private final VertexTable llTable,urTable;
	private final AABBTrigger trigger;
	
	public AABBTriggerTable(Skin skin, AABBTrigger trigger) {
		super(skin, trigger);
		this.trigger = trigger;
		llTable = new VertexTable(trigger.getLowerLeft(), skin, null);
		urTable = new VertexTable(trigger.getUpperRight(), skin, null);
		add("Lower left: ");
		add(llTable);
		row();
		add("Upper right: ");
		add(urTable);
	}

	@Override public AbstractQuestTrigger apply() {
		super.apply(trigger);
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
