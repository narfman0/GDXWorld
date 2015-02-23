package com.blastedstudios.gdxworld.plugin.quest.manifestation.groupcreate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.world.group.GDXGroupExportStruct;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class GroupCreateManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	public static final GroupCreateManifestation DEFAULT = new GroupCreateManifestation();
	private String path = "";
	private Vector2 position = new Vector2();
	
	public GroupCreateManifestation(){}
	
	public GroupCreateManifestation(String path, Vector2 position){
		this.path = path;
		this.position = position;
	}

	@Override public CompletionEnum execute(float dt) {
		FileHandle handle = Gdx.files.internal(path);
		GDXGroupExportStruct struct;
		try {
			struct = (GDXGroupExportStruct) FileUtil.getSerializer(handle).load(handle);
			struct.translate(position);
			struct.instantiate(getExecutor().getWorld(), position);
		} catch (Exception e) {
			Log.error("GroupCreateManifestation.execute", "Failed to load group with path: " +
					path + " msg: " + e.getMessage());
		}
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new GroupCreateManifestation(path, position);
	}

	@Override public String toString() {
		return "[GroupCreateManifestation path:" + path + "]";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
