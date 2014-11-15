package com.blastedstudios.gdxworld.plugin.mode.quest;

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
	private final Label nameLabel;
	
	public QuestTable(final Skin skin, final GDXQuest quest, final QuestWindow questWindow){
		this.quest = quest;
		final QuestTable questTable = this;
		final Button addChildButton = new TextButton("Add Child", skin);
		final Button editButton = new TextButton("Edit", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		addChildButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				GDXQuest child = new GDXQuest();
				//want to strip the ending number and replace with our own
				try{
					String baseName = quest.getName().replaceAll("\\d*$", "");
					int index = Integer.parseInt(quest.getName().substring(baseName.length())) + 1;
					child.setName(baseName + index);
				}catch(Exception e){
					child.setName(quest.getName() + "-0");
				}
				child.setPrerequisites(quest.getName());
				questWindow.addQuest(child);
			}
		});
		editButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				questWindow.editQuest(quest, questTable);
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				questWindow.removeQuest(quest);
			}
		});
		add(new Label("Name: ", skin));
		add(nameLabel = new Label(quest.getName(), skin));
		add(addChildButton);
		add(editButton);
		add(deleteButton);
	}
	
	public void setName(String name){
		nameLabel.setText(name);
	}
	
	public GDXQuest getQuest(){
		return quest;
	}
}
