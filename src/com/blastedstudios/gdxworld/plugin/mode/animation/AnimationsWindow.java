package com.blastedstudios.gdxworld.plugin.mode.animation;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.ui.FileChooserWrapper;
import com.blastedstudios.gdxworld.util.ui.FileChooserWrapper.IFileChooserHandler;
import com.blastedstudios.gdxworld.world.animation.GDXAnimations;

public class AnimationsWindow extends AbstractWindow{
	private final Skin skin;
	private final Table table;
	private final LevelEditorScreen screen;
	private AnimationsEditor editor;

	public AnimationsWindow(final Skin skin, final List<GDXAnimations> animations, 
			final AnimationMode mode, final LevelEditorScreen screen) {
		super("Animations", skin);
		this.skin = skin;
		this.screen = screen;
		table = new Table(skin);
		ScrollPane scrollPane = new ScrollPane(table);
		Button clearButton = new TextButton("Clear", skin);
		Button addButton = new TextButton("Add", skin);
		Button importButton = new TextButton("Import", skin);
		for(GDXAnimations animation : animations){
			table.add(createAnimationTable(animation));
			table.row();
		}
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				animations.clear();
				table.clear();
			}
		});
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				GDXAnimations animation = new GDXAnimations();
				animations.add(animation);
				table.add(createAnimationTable(animation));
			}
		});
		importButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				try {
					IFileChooserHandler handler = new IFileChooserHandler() {
						@Override public void handle(FileHandle handle) {
							try{
								GDXAnimations struct = (GDXAnimations) FileUtil.getSerializer(handle).load(handle);
								screen.getLevel().getAnimations().add(struct);
								table.add(createAnimationTable(struct));
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					};
					FileChooserWrapper.createFileChooser(getStage(), skin, handler);
				} catch (Exception e) {
					Gdx.app.error("AnimationsWindow.importButton Listener", 
							"Import animations failed: " + e.getClass() + ": " + e.getMessage());
				}
			}
		});
		add(scrollPane).colspan(3);
		row();
		add(addButton);
		add(clearButton);
		add(importButton);
		setMovable(false);
		setHeight(400);
		setWidth(400);
	}

	private Table createAnimationTable(final GDXAnimations animation) {
		Table table = new Table();
		final TextButton button = new TextButton(animation.getName(), skin);
		button.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(editor != null)
					editor.remove();
				screen.getStage().addActor(editor = new AnimationsEditor(animation, skin, screen, button));
			}
		});
		table.add(button);
		return table;
	}

	@Override public boolean remove(){
		return super.remove() && (editor == null || editor.remove());
	}

	@Override public boolean contains(float x, float y){
		return super.contains(x, y) || (editor != null && editor.contains(x, y));
	}
}
