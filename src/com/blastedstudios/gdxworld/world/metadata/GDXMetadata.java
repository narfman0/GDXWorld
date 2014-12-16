package com.blastedstudios.gdxworld.world.metadata;

import java.io.Serializable;
import java.util.LinkedList;

public class GDXMetadata implements Cloneable, Serializable{
	private static final long serialVersionUID = 1L;
	private LinkedList<CameraShot> cameraShots = new LinkedList<>();
	
	@Override public Object clone(){
		GDXMetadata clone = new GDXMetadata();
		for(CameraShot shots : cameraShots)
			clone.getCameraShots().add((CameraShot) shots.clone());
		return clone;
	}
	
	@Override public String toString(){
		return "[GDXMetadata cameraShot.size:" + cameraShots.size() + "]";
	}
	
	public LinkedList<CameraShot> getCameraShots() {
		return cameraShots;
	}
	
	public void setCameraShots(LinkedList<CameraShot> cameraShots) {
		this.cameraShots = cameraShots;
	}
}
