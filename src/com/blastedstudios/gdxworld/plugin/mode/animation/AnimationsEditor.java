package com.blastedstudios.gdxworld.plugin.mode.animation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.plugin.mode.animation.AnimationStructRow.IAnimationRowListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.ui.FileChooserWrapper;
import com.blastedstudios.gdxworld.util.ui.FileChooserWrapper.IFileChooserHandler;
import com.blastedstudios.gdxworld.world.animation.AnimationStruct;
import com.blastedstudios.gdxworld.world.animation.GDXAnimation;
import com.blastedstudios.gdxworld.world.animation.GDXAnimations;

public class AnimationsEditor extends AbstractWindow {
	private final TextField nameField, defaultAnimationField;
	private final SelectBox<GDXAnimation> animationSelectBox;
	private final Array<GDXAnimation> animationArray;
	private final Table animationTable;
	private final LevelEditorScreen screen;
	private final GDXAnimations animations;
	private LinkedList<AnimationStructRow> currentAnimationStructs;

	public AnimationsEditor(final GDXAnimations animations, final Skin skin,
			final LevelEditorScreen screen, final TextButton button) {
		super("Animations Editor", skin);
		this.screen = screen;
		this.animations = animations;
		animationTable = new Table(skin);
		nameField = new TextField(animations.getName(), skin);
		nameField.setMessageText("<name>");
		defaultAnimationField = new TextField(animations.getDefaultAnimation(), skin);
		defaultAnimationField.setMessageText("<default animation>");
		animationSelectBox = new SelectBox<>(skin);
		animationSelectBox.setItems(animationArray = new Array<GDXAnimation>(animations.getAnimations().toArray(
				new GDXAnimation[animations.getAnimations().size()])));
		final Button animationDeleteButton = new TextButton("X", skin);
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
				animationArray.add(new GDXAnimation());
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
	
	private void applyCurrentAnimationTable(){
		final GDXAnimation selected = animationSelectBox.getSelected();
		if(selected != null){
			LinkedList<AnimationStruct> newStructs = new LinkedList<>();
			for(AnimationStructRow row : currentAnimationStructs)
				newStructs.add(row.apply());
			selected.setAnimations(newStructs);
			selected.setDefaultAnimation(defaultAnimation);
			selected.setGroupName(groupName);
			selected.setName(name);
			selected.setRepeat(repeat);
			selected.setTotalTime(totalTime);
		}
	}
	
	private void updateAnimationTable(final Skin skin){
		applyCurrentAnimationTable();
		currentAnimationStructs = null;
		animationTable.clear();
		
		final GDXAnimation selected = animationSelectBox.getSelected();
		animationTable.add(new Label("Manifestation", skin), new Label("Time", skin));
		TextButton addButton = new TextButton("Add", skin);
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selected.getAnimations().add(new AnimationStruct());
				updateAnimationTable(skin);
			}
		});
		if(selected != null){
			currentAnimationStructs = new LinkedList<>();
			animationTable.add(addButton);
			animationTable.row();
			for(final Iterator<AnimationStruct> i = selected.getAnimations().iterator(); i.hasNext();){
				final AnimationStruct struct = i.next();
				IAnimationRowListener listener = new IAnimationRowListener() {
					@Override public void removed(AnimationStructRow row) {
						i.remove();
						currentAnimationStructs.remove(row);
						AnimationsEditor.this.updateAnimationTable(skin);
					}
				};
				currentAnimationStructs.add(new AnimationStructRow(skin, listener, struct));
				animationTable.add(currentAnimationStructs.getLast());
				animationTable.row();
			}
		}
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
	
	private void apply(GDXAnimations animations){
		animations.setName(nameField.getText());
		animations.setDefaultAnimation(defaultAnimationField.getText());
		//libGDX's array can have nulls, skipping them
		LinkedList<GDXAnimation> animationList = new LinkedList<>();
		for(GDXAnimation animation : animationSelectBox.getItems())
			if(animation != null)
				animationList.add(animation);
		animations.setAnimations(animationList);
	}
}
