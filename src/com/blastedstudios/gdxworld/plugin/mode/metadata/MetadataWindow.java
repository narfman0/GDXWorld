package com.blastedstudios.gdxworld.plugin.mode.metadata;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.metadata.CameraShot;
import com.blastedstudios.gdxworld.world.metadata.GDXMetadata;

class MetadataWindow extends AbstractWindow {
	private final Skin skin;
	private final Table cameraShotsTable;
	private final LevelEditorScreen screen;
	private final GDXMetadata metadata;
	private MetadataEditor editor;

	public MetadataWindow(final Skin skin, final GDXMetadata metadata, 
			final MetadataMode mode, final LevelEditorScreen screen) {
		super("Metadata Window", skin);
		this.skin = skin;
		this.screen = screen;
		this.metadata = metadata;
		cameraShotsTable = new Table(skin);
		ScrollPane scrollPane = new ScrollPane(cameraShotsTable);
		Button clearButton = new TextButton("Clear", skin);
		Button addButton = new TextButton("Add", skin);
		Button importButton = new TextButton("Import", skin);
		for(CameraShot shot : metadata.getCameraShots()){
			cameraShotsTable.add(createCameraShotTable(shot));
			cameraShotsTable.row();
		}
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				metadata.getCameraShots().clear();
				cameraShotsTable.clear();
			}
		});
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				CameraShot cameraShot = new CameraShot();
				metadata.getCameraShots().add(cameraShot);
				cameraShotsTable.add(createCameraShotTable(cameraShot));
			}
		});
		add(scrollPane).colspan(3);
		row();
		add(addButton);
		add(clearButton);
		add(importButton);
		setMovable(false);
		setHeight(400);
		setWidth(300);
	}

	private Table createCameraShotTable(final CameraShot shot) {
		final Table groupTable = new Table();
		final TextButton groupButton = new TextButton(shot.getName(), skin);
		groupButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(editor != null)
					editor.remove();
				ClickListener acceptListener = new ClickListener(){
					@Override public void clicked(InputEvent event, float x, float y) {
						groupButton.setText(shot.getName());
						editor.remove();
						editor = null;
					}
				};
				ClickListener deleteListener = new ClickListener(){
					@Override public void clicked(InputEvent event, float x, float y) {
						metadata.getCameraShots().remove(shot);
						groupTable.remove();
						editor.remove();
						editor = null;
					}
				};
				screen.getStage().addActor(editor = new MetadataEditor(shot, skin, screen, acceptListener, deleteListener));
			}
		});
		groupTable.add(groupButton);
		return groupTable;
	}

	@Override public boolean remove(){
		return super.remove() && (editor == null || editor.remove());
	}

	@Override public boolean contains(float x, float y){
		return super.contains(x, y) || (editor != null && editor.contains(x, y));
	}

	public void touched(float x, float y) {
		if(editor != null)
			editor.touched(x,y);
	}
}