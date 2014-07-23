package com.blastedstudios.gdxworld.plugin.mode.animation;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.plugin.mode.animation.live.SelectboxGDXAnimationStruct;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.ui.FileChooserWrapper;
import com.blastedstudios.gdxworld.util.ui.FileChooserWrapper.IFileChooserHandler;
import com.blastedstudios.gdxworld.world.animation.GDXAnimation;
import com.blastedstudios.gdxworld.world.animation.GDXAnimations;

public class AnimationsEditor extends AbstractWindow {
	private final TextField nameField, defaultAnimationField;
	private final SelectBox<SelectboxGDXAnimationStruct> animationSelectBox;
	private final Array<SelectboxGDXAnimationStruct> animationArray;
	private final Table animationTable;
	private final LevelEditorScreen screen;
	private final GDXAnimations animations;
	private final Stage stage;
	private AnimationEditor animationEditor;

	public AnimationsEditor(final GDXAnimations animations, final Skin skin,
			Stage stage, final LevelEditorScreen screen, final TextButton button) {
		super("Animations Editor", skin);
		this.screen = screen;
		this.stage = stage;
		this.animations = animations;
		animationTable = new Table(skin);
		nameField = new TextField(animations.getName(), skin);
		nameField.setMessageText("<name>");
		defaultAnimationField = new TextField(animations.getDefaultAnimation(), skin);
		defaultAnimationField.setMessageText("<default animation>");
		animationSelectBox = new SelectBox<>(skin);
		animationSelectBox.setItems(animationArray = SelectboxGDXAnimationStruct.create(animations.getAnimations()));
		animationSelectBox.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				updateAnimationTable(skin);
			}
		});
		final Button animationDeleteButton = new TextButton("Delete", skin);
		animationDeleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(animationSelectBox.getSelected() != null){
					animationArray.removeIndex(animationSelectBox.getSelectedIndex());
					animationSelectBox.setItems(animationArray);
					updateAnimationTable(skin);
				}
			}
		});
		final Button animationAddButton = new TextButton("Add", skin);
		animationAddButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				animationArray.add(new SelectboxGDXAnimationStruct(new GDXAnimation()));
				animationSelectBox.setItems(animationArray);
				//if first added, make sure to update table
				if(animationArray.size == 1){
					animationSelectBox.setSelectedIndex(0);
					updateAnimationTable(skin);
				}
			}
		});

		add("Name: ");
		add(nameField);
		row();
		add("Default: ");
		add(defaultAnimationField);
		row();
		add(animationSelectBox);
		add(animationDeleteButton);
		add(animationAddButton);
		row();
		updateAnimationTable(skin);
		add(animationTable).colspan(4);
		row();
		add(createControlsTable(skin, button)).colspan(4);
		pack();
		setX(Gdx.graphics.getWidth());
		setMovable(false);
	}
	
	public void updateAnimationTable(final Skin skin){
		if(animationEditor != null)
			animationEditor.applyCurrentAnimationTable();
		animationTable.clear();
		if(animationSelectBox.getSelected() != null)
			animationTable.add(animationEditor = new AnimationEditor(skin, 
					stage, animationSelectBox.getSelected().animation, this));
		pack();
		setX(Gdx.graphics.getWidth());
	}
	
	private Table createControlsTable(final Skin skin, final TextButton button){
		final Button acceptButton = new TextButton("Accept", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				apply(animations);
				button.setText(animations.getName());
				remove();
			}
		});
		final Button deleteButton = new TextButton("Delete", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.getLevel().getAnimations().remove(animations);
				button.remove();
				remove();
			}
		});
		final Button exportButton = new TextButton("Export", skin);
		exportButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				final GDXAnimations animations = new GDXAnimations();
				apply(animations);
				try {
					IFileChooserHandler handler = new IFileChooserHandler() {
						@Override public void handle(FileHandle handle) {
							try {
								FileUtil.getSerializer(handle).save(handle, animations);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					FileChooserWrapper.createFileChooser(getStage(), skin, handler);
				} catch (Exception e) {
					Gdx.app.error("AnimationsEditor.exportButton Listener", 
							"Export group failed: " + e.getMessage());
				}
			}
		});
		Table controlsTable = new Table();
		controlsTable.add(acceptButton);
		controlsTable.add(deleteButton);
		controlsTable.add(exportButton);
		return controlsTable;
	}

	@Override public boolean remove(){
		return super.remove() && (animationEditor == null || animationEditor.remove());
	}
	
	private void apply(GDXAnimations animations){
		if(animationEditor != null)
			animationEditor.applyCurrentAnimationTable();
		
		animations.setName(nameField.getText());
		animations.setDefaultAnimation(defaultAnimationField.getText());
		//libGDX's array can have nulls, skipping them
		LinkedList<GDXAnimation> animationList = new LinkedList<>();
		for(SelectboxGDXAnimationStruct animationStruct : animationSelectBox.getItems())
			if(animationStruct != null)
				animationList.add(animationStruct.animation);
		animations.setAnimations(animationList);
	}
}
