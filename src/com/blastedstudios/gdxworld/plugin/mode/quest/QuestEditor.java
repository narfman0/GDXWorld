package com.blastedstudios.gdxworld.plugin.mode.quest;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;

class QuestEditor extends AbstractWindow {
	private ManifestationTable manifestationTable;
	private TriggersWindow triggersWindow = null;
	private final Table parentManifestationTable;
	private final SelectBox<String> manifestationBoxes;
	
	public QuestEditor(LevelEditorScreen screen, final GDXQuest quest, final Skin skin, final QuestTable questTable) {
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
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				remove();
			}
		});
		final Button triggersButton = new TextButton("Triggers", skin);
		triggersButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(triggersWindow != null)
					triggersWindow.remove();
				triggersWindow = new TriggersWindow(skin, quest);
				screen.getStage().addActor(triggersWindow);
			}
		});
		parentManifestationTable = new Table();
		createManifestationTable(skin, quest.getManifestation());

		final List<IQuestComponent> manifestationPlugins = new ArrayList<IQuestComponent>(
				PluginUtil.getPlugins(IQuestComponentManifestation.class));
		manifestationBoxes = new SelectBox<String>(skin);
		manifestationBoxes.setItems(extractList(manifestationPlugins));
		for(IQuestComponent component : manifestationPlugins)
			if(component.getDefault().getClass() == quest.getManifestation().getClass())
				manifestationBoxes.setSelected(component.getBoxText());
		manifestationBoxes.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				createManifestationTable(skin, extractFromSelection(manifestationBoxes.getSelection().first(), manifestationPlugins).getDefault().clone());
				pack();
			}
		});
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				quest.setName(nameField.getText());
				quest.setPrerequisites(prerequisiteField.getText());
				quest.setManifestation(manifestationTable.apply());
				quest.setRepeatable(repeatableBox.isChecked());
				if(triggersWindow != null)
					quest.setTriggers(triggersWindow.apply());
				questTable.setName(quest.getName());
				remove();
			}
		});
		Table contents = new Table(skin);
		contents.add("Name: ");
		contents.add(nameField);
		contents.add(repeatableBox);
		contents.row();
		contents.add("Prerequisites: ");
		contents.add(prerequisiteField);
		contents.row();
		contents.add(triggersButton).colspan(3);
		contents.row();
		contents.add("Manifestation Type: ");
		contents.add(manifestationBoxes);
		contents.row();
		contents.add(parentManifestationTable).colspan(3);
		contents.row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(cancelButton);
		contents.add(controlTable).colspan(3);
		add(new ScrollPane(contents));
		pack();
		setX(Gdx.graphics.getWidth());
		setMovable(false);
	}
	
	static String[] extractList(List<IQuestComponent> plugins){
		String[] list = new String[plugins.size()];
		for(int i=0; i<list.length; i++)
			list[i] = plugins.get(i).getBoxText();
		return list;
	}
	
	/**
	 * @return plugin from the name in the combo box
	 */
	static IQuestComponent extractFromSelection(String selection, List<IQuestComponent> plugins){
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

	public void touched(Vector2 coordinates) {
		if(manifestationTable != null)
			manifestationTable.touched(coordinates);
		if(triggersWindow != null)
			triggersWindow.touched(coordinates);
	}
	
	@Override public boolean remove(){
		return super.remove() && (triggersWindow == null || triggersWindow.remove());
	}
	
	@Override public boolean contains(float x, float y){
		return super.contains(x, y) || (triggersWindow != null && triggersWindow.contains(x, y));
	}
}
