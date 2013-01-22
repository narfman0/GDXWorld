package com.blastedstudios.gdxworld.ui.leveleditor.windows;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.mousemode.QuestMouseMode;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.QuestTable.QuestRemoveListener;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.esotericsoftware.tablelayout.Cell;

public class QuestWindow extends GDXWindow {
	private final QuestMouseMode mouseMode;
	private final Skin skin;
	private final Table questTable;
	private final List<GDXQuest> quests;
	
	public QuestWindow(final Skin skin, final List<GDXQuest> quests, 
			final QuestMouseMode mouseMode) {
		super("Quest Editor", skin);
		this.mouseMode = mouseMode;
		this.skin = skin;
		this.quests = quests;
		questTable = new Table(skin);
		ScrollPane scrollPane = new ScrollPane(questTable);
		Button clearButton = new TextButton("Clear", skin);
		Button acceptButton = new TextButton("Accept", skin);
		Button addButton = new TextButton("Add", skin);
		for(GDXQuest quest : quests){
			questTable.add(createQuestTable(quest));
			questTable.row();
		}
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				questTable.clear();
			}
		});
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				 accept();
			}
		});
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				GDXQuest quest = new GDXQuest();
				quest.setName("newQuest");
				questTable.add(createQuestTable(quest));
			}
		});
		add(scrollPane).colspan(3);
		row();
		add(addButton);
		add(acceptButton);
		add(clearButton);
		setMovable(false);
		setHeight(400);
		setWidth(400);
	}
	
	private void accept(){
		for(Cell<QuestTable> cell : questTable.getCells())
			mouseMode.addQuest(cell.getWidget().getQuest());
	}
	
	private QuestTable createQuestTable(GDXQuest quest){
		return new QuestTable(skin, quest.getName(), quest, new QuestRemoveListener() {
			@Override public void remove(GDXQuest quest) {
				mouseMode.clearQuests();
				accept();
				questTable.clear();
				for(GDXQuest addQuest : quests){
					if(addQuest.equals(quest))
						continue;
					questTable.add(createQuestTable(addQuest));
					questTable.row();
				}
			}
		});
	}
}
