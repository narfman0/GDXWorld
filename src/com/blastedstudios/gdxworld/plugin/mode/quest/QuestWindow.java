package com.blastedstudios.gdxworld.plugin.mode.quest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

class QuestWindow extends AbstractWindow {
	private final Skin skin;
	private final Tree questTree;
	private final List<GDXQuest> quests;
	private final LevelEditorScreen screen;
	private QuestEditor editor;
	
	public QuestWindow(final Skin skin, final List<GDXQuest> quests, 
			final QuestMode mode, LevelEditorScreen screen) {
		super("Quest Window", skin);
		this.skin = skin;
		this.quests = quests;
		this.screen = screen;
		questTree = new Tree(skin);
		ScrollPane scrollPane = new ScrollPane(questTree);
		//scrollPane.setFlickScroll(false);
		Button clearButton = new TextButton("Clear", skin);
		Button addButton = new TextButton("Add", skin);
		populateQuestTable();
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				quests.clear();
				questTree.clear();
				if(editor != null)
					editor.remove();
			}
		});
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				addQuest(new GDXQuest());
			}
		});
		add(scrollPane).colspan(3);
		row();
		add(addButton);
		add(clearButton);
		setMovable(false);
		setHeight(700);
		setWidth(500);
	}
	
	private void populateQuestTable(){
		questTree.clear();
		HashMap<String, Node> questRootMap = new HashMap<>();
		LinkedList<GDXQuest> unpopulated = new LinkedList<>(quests);
		boolean changed = true;
		while(changed){
			changed = false;
			for(Iterator<GDXQuest> questIter = unpopulated.iterator(); questIter.hasNext(); questIter.hasNext()){
				GDXQuest quest = questIter.next();
				boolean prereqsSatisfied = true;
				String prereqQuest = null;
				for(String prereq : quest.getPrerequisites().split(","))
					if(!prereq.isEmpty()){
						if(!questRootMap.containsKey(prereq))
							prereqsSatisfied = false;
						else
							prereqQuest = prereq;
					}
				if(prereqsSatisfied){
					questIter.remove();
					Node toInsert;
					if(prereqQuest == null)
						questTree.add(toInsert = new Node(createQuestTable(quest)));
					else
						questRootMap.get(prereqQuest).add(toInsert = new Node(createQuestTable(quest)));
					toInsert.setExpanded(true);
					questRootMap.put(quest.getName(), toInsert);
					changed = true;
				}
			}
		}
		for(GDXQuest quest : unpopulated)
			questTree.add(new Node(createQuestTable(quest)));
	}
	
	private QuestTable createQuestTable(GDXQuest quest){
		return new QuestTable(skin, quest, this);
	}
	
	public void addQuest(GDXQuest quest){
		quests.add(quest);
		questTree.add(new Node(createQuestTable(quest)));
	}
	
	public void removeQuest(GDXQuest quest) {
		quests.remove(quest);
		populateQuestTable();
	}
	
	public void editQuest(GDXQuest quest, QuestTable table) {
		if(editor != null)
			editor.remove();
		screen.getStage().addActor(editor = new QuestEditor(quest, skin, table));
	}
	
	@Override public boolean remove(){
		return super.remove() && (editor == null || editor.remove());
	}
	
	@Override public boolean contains(float x, float y){
		return super.contains(x, y) || (editor != null && editor.contains(x, y));
	}

	public void touched(Vector2 coordinates) {
		if(editor != null)
			editor.touched(coordinates);
	}
}
