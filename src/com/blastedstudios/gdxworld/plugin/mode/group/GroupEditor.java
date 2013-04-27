package com.blastedstudios.gdxworld.plugin.mode.group;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.group.GDXGroup;
import com.blastedstudios.gdxworld.world.group.GDXGroupExportStruct;

class GroupEditor extends AbstractWindow {
	private final GDXGroup group;
	private final TextField nameField, circlesField, jointsField, polygonsField;
	private final VertexTable centerTable;
	
	public GroupEditor(final GDXGroup group, final Skin skin, final GDXLevel level) {
		super("Group Editor", skin);
		this.group = group;
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
		centerTable = new VertexTable(group.getCenter(), skin, null);
		final Button acceptButton = new TextButton("Accept", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				apply();
				remove();
			}
		});
		final Button deleteButton = new TextButton("Delete", skin);
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				level.getGroups().remove(group);
				remove();
			}
		});
		final Button exportButton = new TextButton("Export", skin);
		exportButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				apply();
				try {
					File file = FileUtil.fileChooser(false, true);
					if(file != null){
						GDXGroupExportStruct struct = group.exportGroup(level);
						FileUtil.getSerializer(file).save(file, struct);
					}
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
	
	private void apply(){
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
		centerTable.setVertex(x,y);
	}
}
