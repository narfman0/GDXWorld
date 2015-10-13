package com.blastedstudios.gdxworld.plugin.quest.trigger.aabb;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

/**
 * Trigger for when player enters a certain rectangle, identified by the
 * lower left and the upper right coordinates of the rectangle
 */
public class AABBTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final AABBTrigger DEFAULT = new AABBTrigger(0,0,1,1);
	private Vector2 lowerLeft = new Vector2(), upperRight = new Vector2();

	public AABBTrigger(){}
	
	public AABBTrigger(float llx, float lly, float urx, float ury){
		this(new Vector2(llx,lly),new Vector2(urx, ury));
	}
	
	public AABBTrigger(Vector2 lowerLeft, Vector2 upperRight){
		this.lowerLeft = lowerLeft;
		this.upperRight = upperRight;
	}

	public Vector2 getLowerLeft() {
		return lowerLeft;
	}

	public void setLowerLeft(Vector2 lowerLeft) {
		this.lowerLeft = lowerLeft;
	}

	public Vector2 getUpperRight() {
		return upperRight;
	}

	public void setUpperRight(Vector2 upperRight) {
		this.upperRight = upperRight;
	}
	
	@Override public boolean activate() {
		if(getProvider().getPlayerPosition() == null){
			Log.log("AABBTrigger.activate", "Player position null");
			return false;
		}
		return getProvider().getPlayerPosition().x < upperRight.x && getProvider().getPlayerPosition().y < upperRight.y &&
				getProvider().getPlayerPosition().x > lowerLeft.x && getProvider().getPlayerPosition().y > lowerLeft.y;
	}
	
	@Override public AbstractQuestTrigger clone(){
		return super.clone(new AABBTrigger(lowerLeft.cpy(), upperRight.cpy()));
	}
	
	@Override public String toString() {
		return "[AABBTrigger: lowerLeft:"+lowerLeft+" upperRight:"+upperRight+"]";
	}
}