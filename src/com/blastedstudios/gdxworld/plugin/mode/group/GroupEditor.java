package com.blastedstudios.gdxworld.plugin.mode.group;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.ui.FileChooserWrapper;
import com.blastedstudios.gdxworld.util.ui.FileChooserWrapper.IFileChooserHandler;
import com.blastedstudios.gdxworld.world.group.GDXGroup;
import com.blastedstudios.gdxworld.world.group.GDXGroupExportStruct;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

class GroupEditor extends AbstractWindow {
	private final TextField nameField, circlesField, jointsField, polygonsField;
	private final VertexTable centerTable;
	private final GDXGroup group;
	private final LevelEditorScreen screen;
	
	public GroupEditor(final GDXGroup group, final Skin skin, final LevelEditorScreen screen, final TextButton groupButton) {
		super("Group Editor", skin);
		this.group = group;
		this.screen = screen;
		nameField = new TextField("", skin);
		nameField.setMessageText("<group name>");
		nameField.setText(group.getName());
		circlesField = new TextField("", skin);
		circlesField.setMessageText("<circles list>");
		circlesField.setText(toField(group.getCircles()));
		jointsField = new TextField("", skin);
		jointsField.setMessageText("<joints list>");
		jointsField.setText(toField(group.getJoints()));
		polygonsField = new TextField("", skin);
		polygonsField.setMessageText("<polygons list>");
		polygonsField.setText(toField(group.getPolygons()));
		centerTable = new VertexTable(group.getCenter(), skin);
		final Button acceptButton = new TextButton("Accept", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				apply(group);
				groupButton.setText(group.getName());
				remove();
			}
		});
		final Button deleteButton = new TextButton("Delete", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				screen.getLevel().getGroups().remove(group);
				remove();
			}
		});
		final Button exportButton = new TextButton("Export", skin);
		exportButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				final GDXGroup group = new GDXGroup();
				apply(group);
				try {
					IFileChooserHandler handler = new IFileChooserHandler() {
						@Override public void handle(FileHandle handle) {
							GDXGroupExportStruct struct = group.exportGroup(screen.getLevel());
							try {
								FileUtil.getSerializer(handle).save(handle, struct);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					FileChooserWrapper.createFileChooser(getStage(), skin, handler);
				} catch (Exception e) {
					Gdx.app.error("GroupEditor.exportButton Listener", 
							"Export group failed: " + e.getMessage());
				}
			}
		});

		add(new Label("Name: ", skin));
		add(nameField);
		row();
		add(new Label("Center: ", skin));
		add(centerTable);
		row();
		add(new Label("Circles: ", skin));
		add(circlesField);
		row();
		add(new Label("Joints: ", skin));
		add(jointsField);
		row();
		add(new Label("Polygons: ", skin));
		add(polygonsField);
		row();
		add(acceptButton);
		add(deleteButton);
		add(exportButton);
		pack();
		setX(Gdx.graphics.getWidth());
		setMovable(false);
	}
	
	private void apply(GDXGroup group){
		group.setName(nameField.getText());
		group.setCenter(centerTable.getVertex());
		group.setCircles(parseField(circlesField));
		group.setJoints(parseField(jointsField));
		group.setPolygons(parseField(polygonsField));
	}
	
	private List<String> parseField(TextField field){
		return Arrays.asList(field.getText().replaceAll("\\s", "").split(","));
	}
	
	private String toField(List<String> values){
		StringBuilder field = new StringBuilder();
		for(String value : values){
			field.append(value);
			field.append(",");
		}
		return field.substring(0, values.isEmpty() ? 0 : field.length()-1);
	}

	public void touched(float x, float y) {
		Vector2 offset = centerTable.getVertex().sub(x, y);
		centerTable.setVertex(x,y);
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
			for(String objectName : group.getCircles())
				for(GDXCircle object : screen.getLevel().getCircles())
					if(object.getName().equals(objectName))
						object.setCenter(object.getCenter().sub(offset));
			for(String objectName : group.getPolygons())
				for(GDXPolygon object : screen.getLevel().getPolygons())
					if(object.getName().equals(objectName))
						object.setCenter(object.getCenter().sub(offset));
			for(String objectName : group.getJoints())
				for(GDXJoint object : screen.getLevel().getJoints())
					if(object.getName().equals(objectName))
						object.translate(offset.cpy().scl(-1f));
		}
		screen.loadLevel();
	}
}
