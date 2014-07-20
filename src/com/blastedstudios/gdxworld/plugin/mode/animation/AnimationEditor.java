package com.blastedstudios.gdxworld.plugin.mode.animation;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
	
	public AnimationEditor(final Skin skin, final GDXAnimation animation, final AnimationsEditor animationsEditor){
		super(skin);
		this.animation = animation;
		nameField = new TextField(animation.getName(), skin);
		nameField.setMessageText("<name>");
		totalTimeField = new TextField(animation.getTotalTime()+"", skin);
		totalTimeField.setMessageText("<total time>");
		repeatCheckbox = new CheckBox("Repeat", skin);
		repeatCheckbox.setChecked(animation.isRepeat());
		add("Name: ");
		add(nameField);
		row();
		add("Time: ");
		add(totalTimeField);
		row();
		add(repeatCheckbox);
		row();
		add(new Label("Manifestation", skin), new Label("Time", skin));
		TextButton addButton = new TextButton("Add", skin);
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				animation.getAnimations().add(new AnimationStruct());
				animationsEditor.updateAnimationTable(skin);
			}
		});
		if(animation != null){
			add(addButton);
			row();
			for(final Iterator<AnimationStruct> i = animation.getAnimations().iterator(); i.hasNext();){
				final AnimationStruct struct = i.next();
				IAnimationRowListener listener = new IAnimationRowListener() {
					@Override public void removed(AnimationStructRow row) {
						i.remove();
						animationStructsRows.remove(row);
						animationsEditor.updateAnimationTable(skin);
					}
				};
				animationStructsRows.add(new AnimationStructRow(skin, listener, struct));
				add(animationStructsRows.getLast());
				row();
			}
		}
	}

	public GDXAnimation applyCurrentAnimationTable(){
		if(animation != null){
			LinkedList<AnimationStruct> newStructs = new LinkedList<>();
			for(AnimationStructRow row : animationStructsRows)
				newStructs.add(row.apply());
			animation.setAnimations(newStructs);
			animation.setName(nameField.getText());
			animation.setRepeat(repeatCheckbox.isChecked());
			animation.setTotalTime(Long.parseLong(totalTimeField.getText()));
		}
		return animation;
	}
}
