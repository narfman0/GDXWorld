package com.blastedstudios.gdxworld.plugin.mode.animation;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.animation.AnimationStructRow.IAnimationRowListener;
import com.blastedstudios.gdxworld.world.animation.AnimationStruct;
import com.blastedstudios.gdxworld.world.animation.GDXAnimation;

public class AnimationEditor extends Table{
	private final LinkedList<AnimationStructRow> animationStructsRows = new LinkedList<>();
	private final TextField nameField, totalTimeField;
	private final CheckBox repeatCheckbox;
	private final GDXAnimation animation;
	private final AnimationsEditor animationsEditor;
	private final Stage stage;
	
	public AnimationEditor(final Skin skin, Stage stage, final GDXAnimation animation, final AnimationsEditor animationsEditor){
		super(skin);
		this.animation = animation;
		this.animationsEditor = animationsEditor;
		this.stage = stage;
		nameField = new TextField(animation.getName(), skin);
		nameField.setMessageText("<name>");
		totalTimeField = new TextField(animation.getTotalTime()+"", skin);
		totalTimeField.setMessageText("<total time>");
		repeatCheckbox = new CheckBox("Repeat", skin);
		repeatCheckbox.setChecked(animation.isRepeat());
		add("Name: ");
		add(nameField);
		row();
		add("Time (s): ");
		add(totalTimeField);
		row();
		add(repeatCheckbox);
		row();
		add("Manifestation"); add("Time");
		if(animation != null){
			TextButton addButton = new TextButton("Add Row", skin);
			addButton.addListener(new ClickListener() {
				@Override public void clicked(InputEvent event, float x, float y) {
					animation.getAnimations().add(new AnimationStruct());
					addAnimationRow(skin, animation.getAnimations().getLast());
					animationsEditor.updateAnimationTable(skin);
				}
			});
			add(addButton).colspan(2);
			row();
			for(final Iterator<AnimationStruct> i = animation.getAnimations().iterator(); i.hasNext();){
				addAnimationRow(skin, i.next());
				row();
			}
		}
	}
	
	private void addAnimationRow(final Skin skin, AnimationStruct struct){
		IAnimationRowListener listener = new IAnimationRowListener() {
			@Override public void removed(AnimationStructRow row) {
				animation.getAnimations().remove(row);
				animationStructsRows.remove(row);
				animationsEditor.updateAnimationTable(skin);
				row.remove();
			}
			@Override public void createEditWindow() {
				removeManifestationEditorWindows();
			}
		};
		AnimationStructRow row = new AnimationStructRow(skin, stage, listener, struct);
		animationStructsRows.add(row);
		row.addSelfToTable(this);
	}

	@Override public boolean remove(){
		return super.remove() && removeManifestationEditorWindows();
	}
	
	public boolean removeManifestationEditorWindows(){
		for(AnimationStructRow row : animationStructsRows)
			row.remove();
		return true;
	}

	public GDXAnimation applyCurrentAnimationTable(){
		if(animation != null){
			LinkedList<AnimationStruct> newStructs = new LinkedList<>();
			for(AnimationStructRow row : animationStructsRows)
				newStructs.add(row.apply());
			animation.setAnimations(newStructs);
			animation.setName(nameField.getText());
			animation.setRepeat(repeatCheckbox.isChecked());
			animation.setTotalTime(Float.parseFloat(totalTimeField.getText()));
		}
		return animation;
	}
}
