package com.blastedstudios.gdxworld.ui.leveleditor.windows.quest;

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
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.quest.manifestation.DialogManifestationTable;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.quest.manifestation.ManifestationTable;
import com.blastedstudios.gdxworld.ui.leveleditor.windows.quest.manifestation.PhysicsManifestationTable;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.DialogManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.PhysicsManifestation;

public class QuestEditor extends GDXWindow {
	private ManifestationTable table;
	private Table parentManifestationTable;
	
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
				quest.setManifestation(table.apply());
				remove();
			}
		});
		final CheckBox dialogBox = new CheckBox("Dialog", skin), 
				physicsBox = new CheckBox("Physics", skin);
		parentManifestationTable = new Table();
		createManifestationTable(skin, quest.getManifestation());
		if(quest.getManifestation() instanceof DialogManifestation)
			dialogBox.setChecked(true);
		else if(quest.getManifestation() instanceof PhysicsManifestation)
			physicsBox.setChecked(true);
		dialogBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				physicsBox.setChecked(false);
				createManifestationTable(skin, (AbstractQuestManifestation) DialogManifestation.DEFAULT.clone());
				pack();
			}
		});
		physicsBox.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				dialogBox.setChecked(false);
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
		add(new Label("Manifestation Type: ", skin));
		add(dialogBox);
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
		if(table != null)
			table.remove();
		if(manifestation instanceof DialogManifestation)
			table = new DialogManifestationTable(skin, (DialogManifestation) manifestation);
		else// if(manifestation instanceof PhysicsManifestation)
			table = new PhysicsManifestationTable(skin, (PhysicsManifestation) manifestation);
		parentManifestationTable.clear();
		parentManifestationTable.add(table);
		return table;
	}
}
