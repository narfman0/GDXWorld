package com.blastedstudios.gdxworld.plugin.mode.quest;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentTrigger;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

class QuestEditor extends AbstractWindow {
	private ManifestationTable manifestationTable;
	private TriggerTable triggerTable;
	private final Table parentManifestationTable, parentTriggerTable;
	private final SelectBox manifestationBoxes, triggerBoxes;
	
	public QuestEditor(final GDXQuest quest, final Skin skin, final QuestTable questTable) {
		super("Quest Editor", skin);
		final TextField nameField = new TextField("", skin);
		nameField.setMessageText("<quest name>");
		nameField.setText(quest.getName());
		final TextField prerequisiteField = new TextField("", skin);
		prerequisiteField.setMessageText("<prerequisites>");
		prerequisiteField.setText(quest.getPrerequisites());
		final CheckBox repeatableBox = new CheckBox("Repeatable", skin);
		repeatableBox.setChecked(quest.isRepeatable());
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				quest.setName(nameField.getText());
				quest.setPrerequisites(prerequisiteField.getText());
				quest.setTrigger(triggerTable.apply());
				quest.setManifestation(manifestationTable.apply());
				quest.setRepeatable(repeatableBox.isChecked());
				questTable.setName(quest.getName());
				remove();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				remove();
			}
		});
		parentManifestationTable = new Table();
		parentTriggerTable = new Table();
		createManifestationTable(skin, quest.getManifestation());
		createTriggerTable(skin, quest.getTrigger());

		final List<IQuestComponent> manifestationPlugins = new ArrayList<IQuestComponent>(
				PluginUtil.getPlugins(IQuestComponentManifestation.class));
		final List<IQuestComponent> triggerPlugins = new ArrayList<IQuestComponent>(
				PluginUtil.getPlugins(IQuestComponentTrigger.class));
		manifestationBoxes = new SelectBox(extractList(manifestationPlugins), skin);
		for(IQuestComponent component : manifestationPlugins)
			if(component.getDefault().getClass() == quest.getManifestation().getClass())
				manifestationBoxes.setSelection(component.getBoxText());
		triggerBoxes = new SelectBox(extractList(triggerPlugins), skin);
		for(IQuestComponent component : triggerPlugins)
			if(component.getDefault().getClass() == quest.getTrigger().getClass())
				triggerBoxes.setSelection(component.getBoxText());
		manifestationBoxes.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				createManifestationTable(skin, extractFromSelection(manifestationBoxes.getSelection(), manifestationPlugins).getDefault().clone());
				pack();
			}
		});
		triggerBoxes.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				createTriggerTable(skin, extractFromSelection(triggerBoxes.getSelection(), triggerPlugins).getDefault().clone());
				pack();
			}
		});
		
		add(new Label("Name: ", skin));
		add(nameField);
		add(repeatableBox);
		row();
		add(new Label("Prerequisites: ", skin));
		add(prerequisiteField);
		row();
		add(new Label("Trigger Type: ", skin));
		add(triggerBoxes);
		row();
		add(parentTriggerTable).colspan(3);
		row();
		add(new Label("Manifestation Type: ", skin));
		add(manifestationBoxes);
		row();
		add(parentManifestationTable).colspan(3);
		row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(cancelButton);
		add(controlTable).colspan(3);
		pack();
		setX(Gdx.graphics.getWidth());
		setMovable(false);
	}
	
	private String[] extractList(List<IQuestComponent> plugins){
		String[] list = new String[plugins.size()];
		for(int i=0; i<list.length; i++)
			list[i] = plugins.get(i).getBoxText();
		return list;
	}
	
	/**
	 * @return plugin from the name in the combo box
	 */
	private IQuestComponent extractFromSelection(String selection, List<IQuestComponent> plugins){
		for(IQuestComponent component : plugins)
			if(component.getBoxText().equals(selection))
				return component;
		return null;
	}
	
	private ManifestationTable createManifestationTable(Skin skin, Object manifestation){
		if(manifestationTable != null)
			manifestationTable.remove();
		for(IQuestComponentManifestation plugin : PluginUtil.getPlugins(IQuestComponentManifestation.class))
			if(manifestation.getClass() == plugin.getDefault().getClass())
				manifestationTable = (ManifestationTable) plugin.createTable(skin, manifestation);
		parentManifestationTable.clear();
		parentManifestationTable.add(manifestationTable);
		return manifestationTable;
	}
	
	private TriggerTable createTriggerTable(Skin skin, Object trigger){
		if(triggerTable != null)
			triggerTable.remove();
		for(IQuestComponentTrigger plugin : PluginUtil.getPlugins(IQuestComponentTrigger.class))
			if(trigger.getClass() == plugin.getDefault().getClass())
				triggerTable = (TriggerTable) plugin.createTable(skin, trigger);
		parentTriggerTable.clear();
		parentTriggerTable.add(triggerTable);
		return triggerTable;
	}

	public void touched(Vector2 coordinates) {
		if(manifestationTable != null)
			manifestationTable.touched(coordinates);
		if(triggerTable != null)
			triggerTable.touched(coordinates);
	}
}
