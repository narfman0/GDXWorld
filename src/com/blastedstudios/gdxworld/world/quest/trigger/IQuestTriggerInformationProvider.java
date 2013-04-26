package com.blastedstudios.gdxworld.world.quest.trigger;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Interface providing information to the quest system to activate 
 * manifestations
 */
public interface IQuestTriggerInformationProvider{
	/**
	 * @return position of player on local machine
	 */
	public Vector2 getPlayerPosition();
	
	/**
	 * @return true if npc/player representing the given name is dead
	 */
	public boolean isDead(String name);

	/**
	 * @param origin body 
	 * @param target of being to be physically close to
	 * @param distance threshold to being
	 * @return true if within distance of npc with name
	 */
	public boolean isNear(String origin, String target, float distance);
	
	/**
	 * @return Physics body with given name
	 */
	public Body getPhysicsObject(String name);
	
	/**
	 * @return true if action button is pressed
	 */
	public boolean isAction();
}