package com.blastedstudios.gdxworld.plugin.mode.animation.live;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.blastedstudios.gdxworld.plugin.mode.animation.AnimationMode;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.world.animation.GDXAnimation;
import com.blastedstudios.gdxworld.world.animation.GDXAnimationHandler;
import com.blastedstudios.gdxworld.world.animation.GDXAnimations;

public class AnimationLiveOptionTable extends Table{
	private final Table table;
	
	public AnimationLiveOptionTable(final Skin skin, AnimationMode mode, final AbstractWindow window) {
		super(skin);
		table = new Table(skin);
		List<GDXAnimations> animations = mode.getScreen().getLevel().getAnimations();
		final SelectBox<SelectboxGDXAnimationHandlerStruct> animationSelectbox = new SelectBox<>(skin);
		LinkedList<GDXAnimationHandler> handlers = new LinkedList<>();
		AnimationLiveQuestExecutor executor = new AnimationLiveQuestExecutor(mode.getScreen());
		for(GDXAnimations gdxAnimations : animations)
			handlers.add(new GDXAnimationHandler(gdxAnimations, executor));
		animationSelectbox.setItems(SelectboxGDXAnimationHandlerStruct.create(handlers));
		animationSelectbox.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				update(skin, animationSelectbox.getSelected().handler, window);
			}
		});
		if(!animations.isEmpty())
			animationSelectbox.setSelectedIndex(0);
		add(animationSelectbox);
		row();
		add(table);
	}
	
	private void update(final Skin skin, GDXAnimationHandler selected, final AbstractWindow window){
		final SelectBox<SelectboxGDXAnimationStruct> animationSelectbox = new SelectBox<>(skin);
		animationSelectbox.setItems(SelectboxGDXAnimationStruct.create(selected.getAnimations().getAnimations()));
		animationSelectbox.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				updateAnimation(skin, animationSelectbox.getSelected().animation);
				window.pack();
			}
		});
		
		table.clear();
		table.add("Current animation: ");
		table.add(animationSelectbox);
		if(!selected.getAnimations().getAnimations().isEmpty())
			animationSelectbox.setSelectedIndex(0);
		window.pack();
	}
	
	/**
	 * When new animation is selected, update animationTable
	 */
	private void updateAnimation(final Skin skin, GDXAnimation animation){
	}
}
