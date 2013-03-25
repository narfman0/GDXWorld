package com.blastedstudios.gdxworld.ui.leveleditor.mode.quest;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

public class QuestTable extends Table{
	private final GDXQuest quest;
	
	public QuestTable(final Skin skin, String name, final GDXQuest quest, 
			final QuestControlListener listener){
		this.quest = quest;
		final Button editButton = new TextButton("Edit", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		editButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.edit(quest);
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.remove(quest);
			}
		});
		add(new Label("Name: " + name, skin));
		add(editButton);
		add(deleteButton);
	}
	
	public GDXQuest getQuest(){
		return quest;
	}

	public interface QuestControlListener{
		public void remove(GDXQuest quest);
		public void edit(GDXQuest quest);
	}
}
