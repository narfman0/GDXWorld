package com.blastedstudios.gdxworld.plugin.mode.quest;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public class TriggersWindow extends AbstractWindow{
	private final LinkedList<TriggerTable> tables = new LinkedList<>();
	private final ArrayList<IQuestComponent> triggerPlugins;
	private final SelectBox<String> triggerBoxes;
	private final Table contents;
	
	public TriggersWindow(Skin skin, GDXQuest quest) {
		super("Triggers", skin);
		contents = new Table(skin);
		triggerPlugins = new ArrayList<IQuestComponent>(PluginUtil.getPlugins(IQuestComponentTrigger.class));
		triggerBoxes = new SelectBox<String>(skin);
		triggerBoxes.setItems(QuestEditor.extractList(triggerPlugins));
		final Button addButton = new TextButton("Add", skin);
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				for(IQuestComponent component : triggerPlugins)
					if(component.getBoxText().equals(triggerBoxes.getSelected()))
						addTable(skin, (AbstractQuestTrigger) component.getDefault().clone());
			}
		});
		add("Trigger: ");
		add(triggerBoxes);
		add(addButton);
		row();
		for(AbstractQuestTrigger trigger : quest.getTriggers())
			addTable(skin, trigger); 
		add(new ScrollPane(contents)).colspan(3);
		setX(Gdx.graphics.getWidth());
		setY(Gdx.graphics.getHeight());
		setHeight(700);
		setWidth(460);
		setMovable(false);
	}
	
	private void addTable(Skin skin, AbstractQuestTrigger trigger){
		TriggerTable table = createTable(trigger);
		Table parentTable = new Table(skin);
		tables.add(table);
		parentTable.add(table);
		final Button deleteButton = new TextButton("Delete", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				contents.removeActor(parentTable);
				tables.remove(table);
				contents.pack();
			}
		});
		parentTable.add(deleteButton);
		contents.add(parentTable);
		contents.row();
	}
	
	private TriggerTable createTable(AbstractQuestTrigger trigger){
		for(IQuestComponent component : triggerPlugins)
			if(component.getDefault().getClass() == trigger.getClass()){
				return (TriggerTable) component.createTable(getSkin(), trigger);
			}
		return null;
	}

	public LinkedList<AbstractQuestTrigger> apply() {
		LinkedList<AbstractQuestTrigger> triggers = new LinkedList<>();
		for(TriggerTable table : tables)
			triggers.add(table.apply());
		return triggers;
	}

	public void touched(Vector2 coordinates) {
		for(TriggerTable table : tables)
			table.touched(coordinates);
	}
}
