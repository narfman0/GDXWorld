package com.blastedstudios.gdxworld.world.quest.manifestation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.blastedstudios.gdxworld.plugin.quest.manifestation.particle.ParticleManifestationTypeEnum;
import com.blastedstudios.gdxworld.plugin.quest.manifestation.sound.SoundManifestationEnum;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

/**
 * Interface client must implement to be able to execute/run the given
 * manifestation of a quest
 */
public interface IQuestManifestationExecutor {
	/**
	 * Add dialog to related quest
	 */
	public CompletionEnum addDialog(String dialog, String origin, String type);
	
	/**
	 * @return named physics object, up to client. Could set user data
	 * name string, or use GDXShape user data and map name, or whatever
	 */
	public Body getPhysicsObject(String name);
	
	/**
	 * @param success true if level complete, false if beating criteria failed
	 */
	public void endLevel(boolean success);

	/**
	 * @return Joint with the given name 
	 */
	public Joint getPhysicsJoint(String name);

	/**
	 * Being spawn manifestation spawns a being at a particular location
	 */
	public void beingSpawn(String being, Vector2 coordinates, String path);

	/**
	 * Create, modify, or remove a particle effect
	 * @param attachedBody physics body to which the particle will attach
	 */
	public void particle(String name, String effectFile, String imagesDir,
			int duration, Vector2 position,
			ParticleManifestationTypeEnum modificationType, String emitterName, 
			String attachedBody);

	/**
	 * Play, pause, etc a sound according to manifestationType
	 */
	public void sound(SoundManifestationEnum manifestationType, String name,
			String filename, float volume, float pan, float pitch);
}
