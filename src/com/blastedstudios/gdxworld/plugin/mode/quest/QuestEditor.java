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
import com.blastedstudios.gdxworld.plugin.mode.quest.manifestation.ManifestationTable;
import com.blastedstudios.gdxworld.plugin.mode.quest.trigger.TriggerTable;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.BeingSpawnManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.DialogManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.EndLevelManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.PhysicsManifestation;
import com.blastedstudios.gdxworld.world.quest.trigger.AABBTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.ActivateTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.KillTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.PersonTrigger;

class QuestEditor extends AbstractWindow {
	private ManifestationTable manifestationTable;
	private TriggerTable triggerTable;
	private final Table parentManifestationTable, parentTriggerTable;
	private final CheckBox aabbBox, activateBox, killBox, personBox,//triggers
		beingSpawnBox, dialogBox, endLevelBox, physicsBox;//manifestions
	
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
		//Triggers
		aabbBox = new CheckBox("AABB", skin);
		activateBox = new CheckBox("Activate", skin);
		killBox = new CheckBox("Kill", skin); 
		personBox = new CheckBox("Person", skin);
		//Manifestations
		beingSpawnBox = new CheckBox("BeingSpawn", skin);
		dialogBox = new CheckBox("Dialog", skin);
		endLevelBox = new CheckBox("End level", skin);
		physicsBox = new CheckBox("Physics", skin);
		parentManifestationTable = new Table();
		parentTriggerTable = new Table();
		createManifestationTable(skin, quest.getManifestation());
		createTriggerTable(skin, quest.getTrigger());
		if(quest.getTrigger() instanceof AABBTrigger)
			aabbBox.setChecked(true);
		if(quest.getTrigger() instanceof ActivateTrigger)
			activateBox.setChecked(true);
		else if(quest.getTrigger() instanceof KillTrigger)
			killBox.setChecked(true);
		else if(quest.getTrigger() instanceof PersonTrigger)
			personBox.setChecked(true);
		if(quest.getManifestation() instanceof BeingSpawnManifestation)
			beingSpawnBox.setChecked(true);
		if(quest.getManifestation() instanceof DialogManifestation)
			dialogBox.setChecked(true);
		else if(quest.getManifestation() instanceof PhysicsManifestation)
			physicsBox.setChecked(true);
		else if(quest.getManifestation() instanceof EndLevelManifestation)
			endLevelBox.setChecked(true);
		aabbBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				setTriggerBoxesChecked(false);
				aabbBox.setChecked(true);
				createTriggerTable(skin, (AbstractQuestTrigger)AABBTrigger.DEFAULT.clone());
				pack();
			}
		});
		activateBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				setTriggerBoxesChecked(false);
				activateBox.setChecked(true);
				createTriggerTable(skin, ActivateTrigger.DEFAULT);
				pack();
			}
		});
		killBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				setTriggerBoxesChecked(false);
				killBox.setChecked(true);
				createTriggerTable(skin, (AbstractQuestTrigger)KillTrigger.DEFAULT.clone());
				pack();
			}
		});
		personBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				setTriggerBoxesChecked(false);
				personBox.setChecked(true);
				createTriggerTable(skin, (AbstractQuestTrigger)PersonTrigger.DEFAULT.clone());
				pack();
			}
		});
		beingSpawnBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				setManifestationBoxesChecked(false);
				beingSpawnBox.setChecked(true);
				createManifestationTable(skin, (AbstractQuestManifestation) BeingSpawnManifestation.DEFAULT.clone());
				pack();
			}
		});
		dialogBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				setManifestationBoxesChecked(false);
				dialogBox.setChecked(true);
				createManifestationTable(skin, (AbstractQuestManifestation) DialogManifestation.DEFAULT.clone());
				pack();
			}
		});
		endLevelBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				setManifestationBoxesChecked(false);
				endLevelBox.setChecked(true);
				createManifestationTable(skin, (AbstractQuestManifestation) EndLevelManifestation.DEFAULT.clone());
				pack();
			}
		});
		physicsBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				setManifestationBoxesChecked(false);
				physicsBox.setChecked(true);
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
		add(activateBox);
		add(killBox);
		add(personBox);
		row();
		add(parentTriggerTable).colspan(3);
		row();
		add(new Label("Manifestation Type: ", skin));
		add(beingSpawnBox);
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
	
	private void setTriggerBoxesChecked(boolean checked){
		aabbBox.setChecked(checked);
		activateBox.setChecked(checked);
		killBox.setChecked(checked);
		personBox.setChecked(checked);
	}
	
	private void setManifestationBoxesChecked(boolean checked){
		beingSpawnBox.setChecked(checked);
		dialogBox.setChecked(checked);
		endLevelBox.setChecked(checked);
		physicsBox.setChecked(checked);
	}
	
	private ManifestationTable createManifestationTable(Skin skin, AbstractQuestManifestation manifestation){
		if(manifestationTable != null)
			manifestationTable.remove();
		manifestationTable = (ManifestationTable) createTable(ManifestationTable.class, manifestation, skin);
		parentManifestationTable.clear();
		parentManifestationTable.add(manifestationTable);
		return manifestationTable;
	}
	
	private TriggerTable createTriggerTable(Skin skin, AbstractQuestTrigger trigger){
		if(triggerTable != null)
			triggerTable.remove();
		triggerTable = (TriggerTable) createTable(TriggerTable.class, trigger, skin);
		parentTriggerTable.clear();
		parentTriggerTable.add(triggerTable);
		return triggerTable;
	}
	
	/**
	 * @return Trigger or Manifestation table, needs to be named correctly
	 */
	private Table createTable(Class<?> tableClass, Object object, Skin skin){
		try {
			Class<?> clazz = Class.forName(tableClass.getPackage().getName() + "." + object.getClass().getSimpleName() + "Table");
			return (Table) clazz.getConstructor(Skin.class, object.getClass()).newInstance(skin, object);
		} catch (Exception e) {
			Gdx.app.error("QuestEditor.createTable", "Exception making table, ensure class names are valid");
			e.printStackTrace();
		}
		return null;
	}
}
