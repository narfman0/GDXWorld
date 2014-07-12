package com.blastedstudios.gdxworld.plugin.mode.group;

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
import com.blastedstudios.gdxworld.world.group.GDXGroup;
import com.blastedstudios.gdxworld.world.group.GDXGroupExportStruct;

class GroupWindow extends AbstractWindow {
	private final Skin skin;
	private final Table groupTable;
	private final LevelEditorScreen screen;
	private GroupEditor editor;

	public GroupWindow(final Skin skin, final List<GDXGroup> groups, 
			final GroupMode mode, final LevelEditorScreen screen) {
		super("Group Window", skin);
		this.skin = skin;
		this.screen = screen;
		groupTable = new Table(skin);
		ScrollPane scrollPane = new ScrollPane(groupTable);
		Button clearButton = new TextButton("Clear", skin);
		Button addButton = new TextButton("Add", skin);
		Button importButton = new TextButton("Import", skin);
		for(GDXGroup group : groups){
			groupTable.add(createGroupTable(group));
			groupTable.row();
		}
		clearButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				groups.clear();
				groupTable.clear();
			}
		});
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				GDXGroup group = new GDXGroup();
				groups.add(group);
				groupTable.add(createGroupTable(group));
			}
		});
		importButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				try {
					IFileChooserHandler handler = new IFileChooserHandler() {
						@Override public void handle(FileHandle handle) {
							try{
								GDXGroupExportStruct struct = (GDXGroupExportStruct) FileUtil.getSerializer(handle.file()).load(handle.file());
								screen.getLevel().getCircles().addAll(struct.circles);
								screen.getLevel().getPolygons().addAll(struct.polygons);
								screen.getLevel().getJoints().addAll(struct.joints);
								GDXGroup group = struct.create();
								groups.add(group);
								groupTable.add(createGroupTable(group));
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					};
					FileChooserWrapper.createFileChooser(getStage(), skin, handler);
				} catch (Exception e) {
					Gdx.app.error("GroupWindow.importButton Listener", 
							"Import group failed: " + e.getClass() + ": " + e.getMessage());
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

	private Table createGroupTable(final GDXGroup group) {
		Table groupTable = new Table();
		Button groupButton = new TextButton(group.getName(), skin);
		groupButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(editor != null)
					editor.remove();
				screen.getStage().addActor(editor = new GroupEditor(group, skin, screen));
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