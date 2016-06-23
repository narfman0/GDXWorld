package com.blastedstudios.gdxworld.plugin.mode.sound;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.Node;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.GDXSound;

public class SoundWindow extends AbstractWindow {
	private final Tree soundTree;
	private SoundEditorWindow editor;
	
	public SoundWindow(final Skin skin, final List<GDXSound> sounds, 
			final SoundMode mode, final LevelEditorScreen screen){
		super("Sound Window", skin);
		soundTree = new Tree(skin);
		for(GDXSound sound : sounds)
			soundTree.add(new SoundNode(skin, sound));
		Button addButton = new TextButton("Add", skin);
		Button clearButton = new TextButton("Clear", skin);
		Button deleteButton = new TextButton("Delete", skin);
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				sounds.clear();
				soundTree.clear();
				if(editor != null)
					editor.remove();
			}
		});
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				GDXSound sound = new GDXSound();
				sounds.add(sound);
				soundTree.add(new SoundNode(skin, sound));
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				for(Node node : soundTree.getSelection()){
					soundTree.remove(node);
					sounds.remove(((SoundNode)node).sound);
				}
				if(editor != null)
					editor.remove();
			}
		});
		soundTree.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				if(editor != null)
					editor.remove();
				if(!soundTree.getSelection().isEmpty()){
					editor = new SoundEditorWindow(skin, ((SoundNode)soundTree.getSelection().first()).sound);
					screen.getStage().addActor(editor);
				}
			}
		});
		add(new ScrollPane(soundTree)).colspan(2);
		row();
		add(addButton);
		add(deleteButton);
		add(clearButton);
		setMovable(false);
		setHeight(400);
		setWidth(400);
	}
	
	@Override public boolean contains(float x, float y){
		return super.contains(x, y) && editor != null && editor.contains(x, y);
	}
	
	@Override public boolean remove(){
		return super.remove() && (editor == null || editor.remove());
	}
	
	class SoundNode extends Node{
		public final GDXSound sound;
		public SoundNode(Skin skin, GDXSound sound) {
			super(new Label(sound.getName(), skin));
			this.sound = sound;
		}
	}
}
