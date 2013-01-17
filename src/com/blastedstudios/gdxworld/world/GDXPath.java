package com.blastedstudios.gdxworld.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class GDXPath {
	private String name = "";
	private List<Vector2> nodes = new ArrayList<Vector2>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Vector2> getNodes() {
		return nodes;
	}

	public void setNodes(List<Vector2> nodes) {
		this.nodes = nodes;
	}
	
	@Override public String toString(){
		return "[GDXPath name:" + getName() + "]";
	}
}
