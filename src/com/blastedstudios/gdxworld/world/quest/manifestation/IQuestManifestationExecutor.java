package com.blastedstudios.gdxworld.world.quest.manifestation;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Interface client must implement to be able to execute/run the given
 * manifestation of a quest
 */
public interface IQuestManifestationExecutor {
	/**
	 * @return named physics object, up to client. Could set user data
	 * name string, or use GDXShape user data and map name, or whatever
	 */
	public Body getPhysicsObject(String name);
	
	/**
	 * @return Joint with the given name 
	 */
	public Joint getPhysicsJoint(String name);

	/**
	 * @return Physics world for direct joint/body access/manipulation
	 */
	public World getWorld();
}
