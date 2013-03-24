package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.GearJoint;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class GDXLevel implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private final List<GDXShape> shapes = new ArrayList<GDXShape>();
	private String name = "";
	private Vector2 coordinates = new Vector2();
	/**
	 * Contains list of level names this level depends on before being playable
	 */
	private List<String> prerequisites = new ArrayList<String>();
	private List<GDXNPC> npcs = new ArrayList<GDXNPC>();
	private List<GDXPath> paths = new ArrayList<GDXPath>();
	private List<GDXJoint> joints = new ArrayList<GDXJoint>();
	private List<GDXQuest> quests = new ArrayList<GDXQuest>();
	private List<GDXBackground> backgrounds = new ArrayList<GDXBackground>();

	public List<GDXShape> getShapes() {
		return shapes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector2 getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Vector2 coords) {
		this.coordinates = coords;
	}

	public List<String> getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(List<String> prerequisites) {
		this.prerequisites = prerequisites;
	}

	public void setPrerequisitesString(String prerequisites) {
		this.prerequisites = new ArrayList<String>();
		for(String prereq : prerequisites.split(","))
			if(!prereq.equals(""))
				this.prerequisites.add(prereq);
	}

	public String getPrerequisitesString() {
		if(prerequisites.isEmpty())
			return "";
		String prereqs = "";
		for(String prereq : prerequisites)
			prereqs += prereq + ",";
		return prereqs.substring(0,prereqs.length()-1);
	}
	
	/**
	 * Iterate over all npcs and if matched, return one with the given name
	 */
	public GDXNPC getNPC(String name){
		for(GDXNPC npc : npcs)
			if(npc.getName().equals(name))
				return npc;
		return null;
	}

	public List<GDXNPC> getNpcs() {
		return npcs;
	}

	public void setNpcs(List<GDXNPC> npcs) {
		this.npcs = npcs;
	}

	public List<GDXPath> getPaths() {
		return paths;
	}
	
	/**
	 * Iterate over all paths and if matched, return path with the given name
	 */
	public GDXPath getPath(String name){
		for(GDXPath path : paths)
			if(path.getName().equals(name))
				return path;
		return null;
	}

	public void setPaths(List<GDXPath> paths) {
		this.paths = paths;
	}

	public List<GDXJoint> getJoints() {
		return joints;
	}

	public void setJoints(List<GDXJoint> joints) {
		this.joints = joints;
	}

	public List<GDXQuest> getQuests() {
		return quests;
	}

	public void setQuests(List<GDXQuest> quests) {
		this.quests = quests;
	}

	public List<GDXBackground> getBackgrounds() {
		if(backgrounds == null)
			backgrounds = new ArrayList<GDXBackground>();
		return backgrounds;
	}

	public void setBackgrounds(List<GDXBackground> backgrounds) {
		this.backgrounds = backgrounds;
	}

	public GDXShape getClosestShape(float x, float y) {
		return getClosestShape(x,y,null);
	}

	public GDXCircle getClosestCircle(float x, float y) {
		return (GDXCircle) getClosestShape(x,y,GDXCircle.class);
	}

	public GDXPolygon getClosestPolygon(float x, float y) {
		return (GDXPolygon) getClosestShape(x,y,GDXPolygon.class);
	}

	private GDXShape getClosestShape(float x, float y, Class<? extends GDXShape> theClass) {
		GDXShape closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXShape shape : shapes){
			float distance = shape.getDistance(x, y);
			if((closest == null || closestDistance > distance) && 
					(theClass == null || shape.getClass() == theClass)){
				closest = shape;
				closestDistance = distance;
			}
		}
		return closest;
	}

	public GDXNPC getClosestNPC(float x, float y) {
		GDXNPC closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXNPC npc : getNpcs()){
			float distance = npc.getCoordinates().dst2(x, y);
			if(closest == null || closestDistance > distance){
				closest = npc;
				closestDistance = distance;
			}
		}
		return closest;
	}

	public GDXPath getClosestPath(float x, float y) {
		GDXPath closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXPath path : getPaths()){
			for(Vector2 node : path.getNodes()){
				float distance = node.dst2(x, y);
				if(closest == null || closestDistance > distance){
					closest = path;
					closestDistance = distance;
				}
			}
		}
		return closest;
	}

	public GDXJoint getClosestJoint(float x, float y, World world) {
		GDXJoint closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXJoint path : getJoints()){
			float distance = path.getDistance(x, y, world);
			if(closest == null || closestDistance > distance){
				closest = path;
				closestDistance = distance;
			}
		}
		return closest;
	}
	
	/**
	 * Populates the given world with physics data from this GDXLevel
	 */
	public void createLevel(World world){
		for(GDXShape shape : shapes)
			shape.createFixture(world, false);
		Map<String,Joint> jointMap = new HashMap<String, Joint>();
		for(GDXJoint joint : joints)
			if(!(joint instanceof GearJoint))
				jointMap.put(joint.getName(), joint.attach(world));
		for(GDXJoint joint : joints)
			if(joint instanceof GearJoint){
				GearJoint gearJoint = (GearJoint) joint;
				gearJoint.initialize(jointMap.get(gearJoint.getJoint1()), jointMap.get(gearJoint.getJoint2()));
				joint.attach(world);
			}
	}

	@Override public String toString(){
		return "[GDXLevel name:" + name + " coords:" + coordinates + "]";
	}
	
	@Override public Object clone(){
		GDXLevel level = new GDXLevel();
		level.setCoordinates(coordinates.cpy());
		for(GDXJoint joint : joints)
			level.getJoints().add((GDXJoint) joint.clone());
		for(GDXShape shape : shapes)
			level.getShapes().add((GDXShape) shape.clone());
		for(String prereq : prerequisites)
			level.getPrerequisites().add(prereq);
		for(GDXNPC npc : npcs)
			level.getNpcs().add((GDXNPC) npc.clone());
		for(GDXPath path : paths)
			level.getPaths().add((GDXPath) path.clone());
		for(GDXQuest quest : quests)
			level.getQuests().add((GDXQuest) quest.clone());
		for(GDXBackground background : backgrounds)
			level.getBackgrounds().add((GDXBackground) background.clone());
		return level;
	}

	/**
	 * Clear all objects from a level
	 */
	public void clear() {
		shapes.clear();
		prerequisites.clear();
		npcs.clear();
		paths.clear();
		joints.clear();
		quests.clear();
		backgrounds.clear();
	}
}
