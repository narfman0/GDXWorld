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
import com.blastedstudios.gdxworld.world.animation.GDXAnimationHandler;
import com.blastedstudios.gdxworld.world.animation.GDXAnimations;

public class AnimationLiveOptionTable extends Table{
	private final LinkedList<GDXAnimationHandler> handlers;
	private final Table animationsTable;
	
	public AnimationLiveOptionTable(final Skin skin, AnimationMode mode, final AbstractWindow window) {
		super(skin);
		animationsTable = new Table(skin);
		List<GDXAnimations> animations = mode.getScreen().getLevel().getAnimations();
		final SelectBox<SelectboxGDXAnimationHandlerStruct> animationSelectbox = new SelectBox<>(skin);
		handlers = new LinkedList<>();
		AnimationLiveQuestExecutor executor = new AnimationLiveQuestExecutor(mode.getScreen());
		for(GDXAnimations gdxAnimations : animations)
			handlers.add(new GDXAnimationHandler(gdxAnimations, executor));
		animationSelectbox.setItems(SelectboxGDXAnimationHandlerStruct.create(handlers));
		animationSelectbox.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				handleGDXAnimationHandlerSelected(skin, animationSelectbox.getSelected().handler, window);
			}
		});
		if(!animations.isEmpty())
			animationSelectbox.setSelectedIndex(0);
		add(animationSelectbox);
		row();
		add(animationsTable);
	}
	
	private void handleGDXAnimationHandlerSelected(final Skin skin, final GDXAnimationHandler handler, final AbstractWindow window){
		final SelectBox<SelectboxGDXAnimationStruct> animationSelectbox = new SelectBox<>(skin);
		animationSelectbox.setItems(SelectboxGDXAnimationStruct.create(handler.getAnimations().getAnimations()));
		animationSelectbox.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				handler.applyCurrentAnimation(animationSelectbox.getSelected().animation, 0);
				window.pack();
			}
		});
		animationsTable.clear();
		animationsTable.add("Current animation: ");
		animationsTable.add(animationSelectbox);
		if(!handler.getAnimations().getAnimations().isEmpty())
			animationSelectbox.setSelectedIndex(0);
		window.pack();
	}
	
	public void render(float delta) {
		for(GDXAnimationHandler handler : handlers)
			handler.render(delta);
	}
}
