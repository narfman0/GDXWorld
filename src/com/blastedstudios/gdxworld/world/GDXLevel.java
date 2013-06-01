package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.light.GDXLight;
import com.blastedstudios.gdxworld.world.group.GDXGroup;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.GearJoint;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class GDXLevel implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Level-" + count++;
	private Vector2 coordinates = new Vector2();
	private final List<GDXCircle> circles = new ArrayList<>();
	private final List<GDXPolygon> polygons = new ArrayList<>();
	private final List<GDXNPC> npcs = new ArrayList<>();
	private final List<GDXPath> paths = new ArrayList<>();
	private final List<GDXJoint> joints = new ArrayList<>();
	private final List<GDXQuest> quests = new ArrayList<>();
	private final List<GDXBackground> backgrounds = new ArrayList<>();
	private final List<GDXLight> lights = new ArrayList<>();
	private final List<GDXGroup> groups = new ArrayList<>();
	private Map<String,String> properties;
	private int lightAmbient = GDXLight.convert(GDXLight.DEFAULT_COLOR);
	
	public GDXLevel(){
		properties = createProperties();
	}
	
	public List<GDXCircle> getCircles() {
		return circles;
	}

	public List<GDXPolygon> getPolygons() {
		return polygons;
	}

	public Collection<GDXShape> getShapes() {
		List<GDXShape> shapes = new ArrayList<>();
		shapes.addAll(circles);
		shapes.addAll(polygons);
		return Collections.unmodifiableList(shapes);
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

	public List<GDXJoint> getJoints() {
		return joints;
	}

	public List<GDXQuest> getQuests() {
		return quests;
	}

	public List<GDXBackground> getBackgrounds() {
		return backgrounds;
	}

	public List<GDXLight> getLights() {
		Collections.sort(lights);
		return lights;
	}

	public int getLightAmbient() {
		return lightAmbient;
	}

	public void setLightAmbient(int lightAmbient) {
		this.lightAmbient = lightAmbient;
	}

	public List<GDXGroup> getGroups() {
		return groups;
	}

	public Map<String,String> getProperties() {
		if(properties == null)
			properties = createProperties();
		return properties;
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
		for(GDXShape shape : getShapes()){
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
			float distance = path.getCenter().dst2(x, y);
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
	public CreateLevelReturnStruct createLevel(World world){
		HashMap<GDXShape,Body> bodies = createBodies(world);
		HashMap<GDXJoint,Joint> joints = createJoints(world);
		CreateLevelLightReturn lights = createLights(world);
		return new CreateLevelReturnStruct(bodies, joints, lights);
	}
	
	public HashMap<GDXShape,Body> createBodies(World world){
		HashMap<GDXShape,Body> returnBodies = new HashMap<>();
		for(GDXShape shape : getShapes()){
			Body body = shape.createFixture(world, false);
			returnBodies.put(shape, body); 
		}
		return returnBodies;
	}

	public HashMap<GDXJoint,Joint> createJoints(World world){
		HashMap<GDXJoint,Joint> returnJoints = new HashMap<>();
		Map<String,Joint> jointMap = new HashMap<String, Joint>();
		for(GDXJoint joint : joints)
			if(!(joint instanceof GearJoint)){
				Joint physicsJoint = joint.attach(world);
				jointMap.put(joint.getName(), physicsJoint);
				returnJoints.put(joint, physicsJoint);
			}
		//Need to initialize other joints first since GearJoint depends on them
		//being done
		for(GDXJoint joint : joints)
			if(joint instanceof GearJoint){
				GearJoint gearJoint = (GearJoint) joint;
				gearJoint.initialize(jointMap.get(gearJoint.getJoint1()), 
						jointMap.get(gearJoint.getJoint2()));
				Joint physicsJoint = joint.attach(world);
				returnJoints.put(joint, physicsJoint);
			}
		return returnJoints;
	}
	
	public CreateLevelLightReturn createLights(World world){
		RayHandler rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(GDXLight.convert(lightAmbient));
		ArrayList<Light> returnLights = new ArrayList<>();
		for(GDXLight light : getLights())
			returnLights.add(light.create(rayHandler));
		return new CreateLevelLightReturn(rayHandler, returnLights);
	}
	
	@Override public String toString(){
		return "[GDXLevel name:" + name + " coords:" + coordinates + "]";
	}
	
	@Override public Object clone(){
		GDXLevel level = new GDXLevel();
		level.setCoordinates(coordinates.cpy());
		for(GDXJoint joint : joints)
			level.getJoints().add((GDXJoint) joint.clone());
		for(GDXCircle circle : circles)
			level.getCircles().add((GDXCircle) circle.clone());
		for(GDXPolygon polygon : polygons)
			level.getPolygons().add((GDXPolygon) polygon.clone());
		for(GDXNPC npc : npcs)
			level.getNpcs().add((GDXNPC) npc.clone());
		for(GDXPath path : paths)
			level.getPaths().add((GDXPath) path.clone());
		for(GDXQuest quest : quests)
			level.getQuests().add((GDXQuest) quest.clone());
		for(GDXBackground background : backgrounds)
			level.getBackgrounds().add((GDXBackground) background.clone());
		for(GDXLight light : lights)
			level.getLights().add((GDXLight) light.clone());
		level.setLightAmbient(lightAmbient);
		for(GDXGroup group : groups)
			level.getGroups().add((GDXGroup) group.clone());
		level.getProperties().putAll(properties);
		return level;
	}

	/**
	 * Clear all objects from a level
	 */
	public void clear() {
		polygons.clear();
		circles.clear();
		npcs.clear();
		paths.clear();
		joints.clear();
		quests.clear();
		backgrounds.clear();
		lights.clear();
		lightAmbient = GDXLight.convert(GDXLight.DEFAULT_COLOR);
		groups.clear();
		properties.clear();
	}

	public class CreateLevelReturnStruct{
		public final Map<GDXShape,Body> bodies;
		public final Map<GDXJoint,Joint> joints;
		public final CreateLevelLightReturn lights;
		
		CreateLevelReturnStruct(Map<GDXShape,Body> bodies,
				Map<GDXJoint,Joint> joints, CreateLevelLightReturn lights){
			this.bodies = bodies;
			this.joints = joints;
			this.lights = lights;
		}
	}

	public class CreateLevelLightReturn{
		public final RayHandler rayHandler;
		public final List<Light> lights;
		
		public CreateLevelLightReturn(RayHandler rayHandler, List<Light> lights){
			this.rayHandler = rayHandler;
			this.lights = lights;
		}
	}
	
	private static HashMap<String,String> createProperties(){
		HashMap<String,String> propertiesMap = new HashMap<>();
		String propValue = Properties.get("level.properties", "");
		String[] properties = propValue.contains(",") ?
				propValue.split(",") : new String[]{propValue};
		for(String property : properties)
			if(!property.trim().equals(""))
				propertiesMap.put(property, "");
		return propertiesMap;
	}
}
