package com.blastedstudios.gdxworld.plugin.quest.manifestation.groupcreate;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class GroupCreateManifestationTable extends ManifestationTable{
	private final TextField pathText;
	private final VertexTable positionTable;
	
	public GroupCreateManifestationTable(Skin skin, GroupCreateManifestation manifestation) {
		super(skin);
		pathText = new TextField(manifestation.getPath(), skin);
		pathText.setMessageText("<path>");
		positionTable = new VertexTable(manifestation.getPosition(), skin);
		add(new Label("Path", skin));
		add(pathText);
		row();
		add(positionTable);
	}

	@Override public AbstractQuestManifestation apply() {
		return new GroupCreateManifestation(pathText.getText(), positionTable.getVertex());
	}
	
	@Override
	public void touched(Vector2 coordinates){
		positionTable.setVertex(coordinates.x, coordinates.y);
	}
}
