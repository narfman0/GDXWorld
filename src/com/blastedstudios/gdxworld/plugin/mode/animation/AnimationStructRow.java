package com.blastedstudios.gdxworld.plugin.mode.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.world.animation.AnimationStruct;

public class AnimationStructRow extends Table {
	private final AnimationStruct struct;
	private final TextField timeField;
	
	public AnimationStructRow(final Skin skin, final IAnimationRowListener listener, AnimationStruct struct){
		super(skin);
		this.struct = struct;
		timeField = new TextField(struct.time+"", skin);
		timeField.setMessageText("<time>");
		add(struct.manifestation.getClass().getSimpleName());
		add(timeField);
		TextButton removeButton = new TextButton("X", skin);
		removeButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				listener.removed(AnimationStructRow.this);
			}
		});
		add(removeButton);
		TextButton editButton = new TextButton("Edit", skin);
		editButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Gdx.app.log("AnimationsEditor.updateAnimationTable::edit handler", "Add handling here");
				//TODO add edit window here
			}
		});
		add(editButton);
	}
	
	/**
	 * Apply current ui and grab struct
	 */
	public AnimationStruct apply(){
		struct.time = Long.parseLong(timeField.getText());
		return struct;
	}
	
	interface IAnimationRowListener{
		void removed(AnimationStructRow row);
	}
}
