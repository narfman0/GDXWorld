package com.blastedstudios.gdxworld.plugin.mode.quest;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.plugin.mode.quest.QuestMode;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

class QuestWindow extends AbstractWindow {
	private final Skin skin;
	private final Table questTable;
	private final List<GDXQuest> quests;
	private final LevelEditorScreen screen;
	private QuestEditor editor;
	
	public QuestWindow(final Skin skin, final List<GDXQuest> quests, 
			final QuestMode mode, LevelEditorScreen screen) {
		super("Quest Window", skin);
		this.skin = skin;
		this.quests = quests;
		this.screen = screen;
		questTable = new Table(skin);
		ScrollPane scrollPane = new ScrollPane(questTable);
		Button clearButton = new TextButton("Clear", skin);
		Button addButton = new TextButton("Add", skin);
		for(GDXQuest quest : quests){
			questTable.add(createQuestTable(quest));
			questTable.row();
		}
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				quests.clear();
				questTable.clear();
				editor.remove();
			}
		});
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				GDXQuest quest = new GDXQuest();
				quests.add(quest);
				questTable.add(createQuestTable(quest));
			}
		});
		add(scrollPane).colspan(3);
		row();
		add(addButton);
		add(clearButton);
		setMovable(false);
		setHeight(400);
		setWidth(400);
	}
	
	private QuestTable createQuestTable(GDXQuest quest){
		return new QuestTable(skin, quest.getName(), quest, new QuestTable.QuestControlListener() {
			@Override public void remove(GDXQuest quest) {
				quests.remove(quest);
				questTable.clear();
				for(GDXQuest addQuest : quests){
					questTable.add(createQuestTable(addQuest));
					questTable.row();
				}
			}
			@Override public void edit(GDXQuest quest) {
				if(editor != null)
					editor.remove();
				screen.getStage().addActor(editor = new QuestEditor(quest, skin));
			}
		});
	}
	
	@Override public boolean remove(){
		return super.remove() && (editor == null || editor.remove());
	}
	
	@Override public boolean contains(float x, float y){
		return super.contains(x, y) || (editor != null && editor.contains(x, y));
	}
}
