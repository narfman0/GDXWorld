package com.blastedstudios.gdxworld.plugin.quest.manifestation.sound;

import com.blastedstudios.gdxworld.util.PluginUtil;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class SoundManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	public static SoundManifestation DEFAULT = new SoundManifestation();
	private SoundManifestationEnum manifestationType = SoundManifestationEnum.PLAY;
	private String name = "Sound-"+count++, filename = "";
	private float volume = 1f, pan, pitch;
	
	public SoundManifestation(){}
	public SoundManifestation(SoundManifestationEnum manifestationType, String name,
			String filename, float volume, float pan, float pitch){
		this.manifestationType = manifestationType;
		this.name = name;
		this.filename = filename;
		this.volume = volume;
		this.pan = pan;
		this.pitch = pitch;
	}
	
	@Override public CompletionEnum execute() {
		for(ISoundHandler handler : PluginUtil.getPlugins(ISoundHandler.class))
			if(handler.sound(manifestationType, name, filename, volume, pan, pitch) == CompletionEnum.COMPLETED)
				return CompletionEnum.COMPLETED;
		return CompletionEnum.EXECUTING;
	}

	@Override public SoundManifestation clone() {
		return new SoundManifestation(manifestationType, name, filename, volume, pan, pitch);
	}

	@Override public String toString() {
		return "[SoundManifestation name:" + name+"]";
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public float getPan() {
		return pan;
	}

	public void setPan(float pan) {
		this.pan = pan;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public SoundManifestationEnum getManifestationType() {
		return manifestationType;
	}
	
	public void setManifestationType(SoundManifestationEnum manifestationType) {
		this.manifestationType = manifestationType;
	}
}
