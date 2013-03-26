package com.blastedstudios.gdxworld.plugin.mode.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.plugin.mode.quest.manifestation.DialogManifestationTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.manifestation.EndLevelManifestationTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.manifestation.ManifestationTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.manifestation.PhysicsManifestationTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.trigger.AABBTriggerTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.trigger.KillTriggerTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.trigger.PersonTriggerTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.trigger.TriggerTable;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.DialogManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.EndLevelManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.PhysicsManifestation;
import com.blastedstudios.gdxworld.world.quest.trigger.AABBTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.KillTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.PersonTrigger;

class QuestEditor extends AbstractWindow {
	private ManifestationTable manifestationTable;
	private TriggerTable triggerTable;
	private final Table parentManifestationTable, parentTriggerTable;
	
	public QuestEditor(final GDXQuest quest, final Skin skin) {
		super("Quest Editor", skin);
		final TextField nameField = new TextField("", skin);
		nameField.setMessageText("<quest name>");
		nameField.setText(quest.getName());
		final TextField prerequisiteField = new TextField("", skin);
		prerequisiteField.setMessageText("<prerequisites>");
		prerequisiteField.setText(quest.getPrerequisites());
		final Button acceptButton = new TextButton("Accept", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				quest.setName(nameField.getText());
				quest.setPrerequisites(prerequisiteField.getText());
				quest.setTrigger(triggerTable.apply());
				quest.setManifestation(manifestationTable.apply());
				remove();
			}
		});
		final CheckBox aabbBox = new CheckBox("AABB", skin), 
				killBox = new CheckBox("Kill", skin), 
				personBox = new CheckBox("Person", skin), 
				dialogBox = new CheckBox("Dialog", skin), 
				endLevelBox = new CheckBox("End level", skin), 
				physicsBox = new CheckBox("Physics", skin);
		parentManifestationTable = new Table();
		parentTriggerTable = new Table();
		createManifestationTable(skin, quest.getManifestation());
		createTriggerTable(skin, quest.getTrigger());
		if(quest.getTrigger() instanceof AABBTrigger)
			aabbBox.setChecked(true);
		else if(quest.getTrigger() instanceof KillTrigger)
			killBox.setChecked(true);
		else if(quest.getTrigger() instanceof PersonTrigger)
			personBox.setChecked(true);
		if(quest.getManifestation() instanceof DialogManifestation)
			dialogBox.setChecked(true);
		else if(quest.getManifestation() instanceof PhysicsManifestation)
			physicsBox.setChecked(true);
		else if(quest.getManifestation() instanceof EndLevelManifestation)
			endLevelBox.setChecked(true);
		aabbBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				killBox.setChecked(false);
				personBox.setChecked(false);
				createTriggerTable(skin, (AbstractQuestTrigger)AABBTrigger.DEFAULT.clone());
				pack();
			}
		});
		killBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				aabbBox.setChecked(false);
				personBox.setChecked(false);
				createTriggerTable(skin, (AbstractQuestTrigger)KillTrigger.DEFAULT.clone());
				pack();
			}
		});
		personBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				aabbBox.setChecked(false);
				killBox.setChecked(false);
				createTriggerTable(skin, (AbstractQuestTrigger)PersonTrigger.DEFAULT.clone());
				pack();
			}
		});
		dialogBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				physicsBox.setChecked(false);
				endLevelBox.setChecked(false);
				createManifestationTable(skin, (AbstractQuestManifestation) DialogManifestation.DEFAULT.clone());
				pack();
			}
		});
		endLevelBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				dialogBox.setChecked(false);
				physicsBox.setChecked(false);
				createManifestationTable(skin, (AbstractQuestManifestation) EndLevelManifestation.DEFAULT.clone());
				pack();
			}
		});
		physicsBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				dialogBox.setChecked(false);
				endLevelBox.setChecked(false);
				createManifestationTable(skin, (AbstractQuestManifestation) PhysicsManifestation.DEFAULT.clone());
				pack();
			}
		});
		add(new Label("Name: ", skin));
		add(nameField);
		row();
		add(new Label("Prerequisites: ", skin));
		add(prerequisiteField);
		row();
		add(new Label("Trigger Type: ", skin));
		add(aabbBox);
		add(killBox);
		add(personBox);
		row();
		add(parentTriggerTable).colspan(3);
		row();
		add(new Label("Manifestation Type: ", skin));
		add(dialogBox);
		add(endLevelBox);
		add(physicsBox);
		row();
		add(parentManifestationTable).colspan(3);
		row();
		add(acceptButton).colspan(3);
		setMovable(false);
		pack();
		setX(Gdx.graphics.getWidth());
		setMovable(false);
	}
	
	private ManifestationTable createManifestationTable(Skin skin, AbstractQuestManifestation manifestation){
		if(manifestationTable != null)
			manifestationTable.remove();
		if(manifestation instanceof DialogManifestation)
			manifestationTable = new DialogManifestationTable(skin, (DialogManifestation) manifestation);
		else if(manifestation instanceof PhysicsManifestation)
			manifestationTable = new PhysicsManifestationTable(skin, (PhysicsManifestation) manifestation);
		else if(manifestation instanceof EndLevelManifestation)
			manifestationTable = new EndLevelManifestationTable(skin, (EndLevelManifestation) manifestation);
		parentManifestationTable.clear();
		parentManifestationTable.add(manifestationTable);
		return manifestationTable;
	}
	
	private TriggerTable createTriggerTable(Skin skin, AbstractQuestTrigger trigger){
		if(triggerTable != null)
			triggerTable.remove();
		if(trigger instanceof AABBTrigger)
			triggerTable = new AABBTriggerTable(skin, (AABBTrigger) trigger);
		else if(trigger instanceof KillTrigger)
			triggerTable = new KillTriggerTable(skin, (KillTrigger) trigger);
		else if(trigger instanceof PersonTrigger)
			triggerTable = new PersonTriggerTable(skin, (PersonTrigger) trigger);
		parentTriggerTable.clear();
		parentTriggerTable.add(triggerTable);
		return triggerTable;
	}
}
