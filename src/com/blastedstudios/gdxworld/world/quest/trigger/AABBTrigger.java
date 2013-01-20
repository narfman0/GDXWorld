package com.blastedstudios.gdxworld.world.quest.trigger;

public class AABBTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	private final int llx, lly, urx, ury;
	
	public AABBTrigger(int llx, int lly, int urx, int ury){
		this.llx = llx;
		this.lly = lly;
		this.urx = urx;
		this.ury = ury;
	}
	@Override public boolean activate() {
		return getProvider().getPlayerPosition().x < urx && getProvider().getPlayerPosition().y < ury &&
				getProvider().getPlayerPosition().x > llx && getProvider().getPlayerPosition().y > lly;
	}
	
	@Override public Object clone(){
		return new AABBTrigger(llx, lly, urx, ury);
	}
}