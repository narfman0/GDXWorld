package com.blastedstudios.gdxworld.plugin.quest.manifestation.particle;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;

public class ParticleManifestation extends AbstractQuestManifestation {
	private static final long serialVersionUID = 1L;
	public static ParticleManifestation DEFAULT = new ParticleManifestation();
	private String name = "Particle", effectFile = "data/particles/particle.p", 
			imagesDir = "data/particles", emitterName = "", attachedBody = "";
	private int duration = -1;
	private Vector2 position = new Vector2();
	private ParticleManifestationTypeEnum modificationType = ParticleManifestationTypeEnum.CREATE;
	
	public ParticleManifestation(){}
	
	public ParticleManifestation(String name, String effectFile, String imagesDir,
			int duration, Vector2 position, ParticleManifestationTypeEnum modificationType,
			String emitterName, String attachedBody){
		this.name = name;
		this.effectFile = effectFile;
		this.imagesDir = imagesDir;
		this.duration = duration;
		this.position = position;
		this.modificationType = modificationType;
		this.emitterName = emitterName;
		this.attachedBody = attachedBody;
	}
	
	@Override public CompletionEnum execute() {
		executor.particle(name, effectFile, imagesDir, duration, position, modificationType, 
				emitterName, attachedBody);
		return CompletionEnum.COMPLETED;
	}

	@Override public AbstractQuestManifestation clone() {
		return new ParticleManifestation(name, effectFile, imagesDir, duration,
				position.cpy(), modificationType, emitterName, attachedBody);
	}

	@Override public String toString() {
		return "[ParticleManifestation name:" + name+"]";
	}

	public String getEffectFile() {
		return effectFile;
	}

	public void setEffectFile(String effectFile) {
		this.effectFile = effectFile;
	}

	public String getImagesDir() {
		return imagesDir;
	}

	public void setImagesDir(String imagesDir) {
		this.imagesDir = imagesDir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public ParticleManifestationTypeEnum getModificationType() {
		return modificationType;
	}

	public void setModificationType(ParticleManifestationTypeEnum modificationType) {
		this.modificationType = modificationType;
	}

	public String getEmitterName() {
		return emitterName;
	}

	public void setEmitterName(String emitterName) {
		this.emitterName = emitterName;
	}

	public String getAttachedBody() {
		return attachedBody;
	}

	public void setAttachedBody(String attachedBody) {
		this.attachedBody = attachedBody;
	}
}
