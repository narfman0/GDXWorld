package com.blastedstudios.gdxworld.plugin.mode.npc;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.world.GDXNPC;

class NPCWindow extends AbstractWindow {
	private final NPCTable table;
	
	public NPCWindow(final Skin skin, final NPCMode mode, final GDXNPC npc) {
		super("NPC Editor", skin);
		table = new NPCTable(skin, npc);
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				table.apply();
				mode.addNPC(npc);
				mode.clean();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.clean();
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.removeNPC(npc);
				mode.clean();
			}
		});
		add(table).colspan(3);
		row();
		add(acceptButton);
		add(cancelButton);
		add(deleteButton);
		setMovable(false);
		pack();
	}

	public void setCoordinates(Vector2 coordinates) {
		table.setCoordinates(coordinates);
	}
	
	public Vector2 getCoordinates(){
		return table.getCoordinates();
	}
}
