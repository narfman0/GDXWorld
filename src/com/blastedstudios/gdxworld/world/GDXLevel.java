package com.blastedstudios.gdxworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.plugin.quest.manifestation.dialog.DialogManifestation;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.animation.GDXAnimations;
import com.blastedstudios.gdxworld.world.group.GDXGroup;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.GearJoint;
import com.blastedstudios.gdxworld.world.light.GDXLight;
import com.blastedstudios.gdxworld.world.quest.GDXQuest;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

/**
 * Representing a particular area in a world, the level contains a list of all
 * the relevant objects like what physics is like and how the player interacts
 * with that environment.
 */
public class GDXLevel implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Level-" + count++;
	private Vector2 coordinates = new Vector2();
	private Map<Vector2, GDXTile> tiles = new HashMap<>();
	private final List<GDXCircle> circles = new ArrayList<>();
	private final List<GDXPolygon> polygons = new ArrayList<>();
	private final List<GDXNPC> npcs = new ArrayList<>();
	private final List<GDXPath> paths = new ArrayList<>();
	private final List<GDXJoint> joints = new ArrayList<>();
	private final List<GDXQuest> quests = new ArrayList<>();
	private final List<GDXBackground> backgrounds = new ArrayList<>();
	private final List<GDXLight> lights = new ArrayList<>();
	private final List<GDXGroup> groups = new ArrayList<>();
	private List<GDXParticle> particles = new ArrayList<>();
	private List<GDXSound> sounds = new ArrayList<>();
	private List<GDXAnimations> animations = new LinkedList<>();
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
	
	public Map<Vector2, GDXTile> getTiles() {
		if(tiles == null)//for now creating new, for upconversion of old worlds pre-tile
			tiles = new HashMap<>();
		return tiles;
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

	public List<GDXSound> getSounds() {
		if(sounds == null)
			sounds = new ArrayList<>();
		return sounds;
	}
	
	private Map<String, String> createProperties() {
		return Properties.parseProperties("level.properties", "");
	}

	public List<GDXParticle> getParticles() {
		if(particles == null)
			particles = new ArrayList<>();
		return particles;
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

	public GDXParticle getClosestParticle(float x, float y) {
		GDXParticle closest = null;
		float closestDistance = Float.MAX_VALUE;
		for(GDXParticle path : particles){
			float distance = path.getPosition().dst2(x, y);
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
		for(Entry<Vector2, GDXTile> entry : getTiles().entrySet())
			level.getTiles().put(entry.getKey(), entry.getValue().clone());
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
		for(GDXSound sound : sounds)
			level.getSounds().add((GDXSound) sound.clone());
		for(GDXParticle particle : particles)
			level.getParticles().add((GDXParticle) particle.clone());
		for(GDXAnimations animation : animations)
			level.getAnimations().add((GDXAnimations) animation.clone());
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
		sounds.clear();
		properties.clear();
		particles.clear();
		tiles.clear();
		animations.clear();
	}

	public List<GDXAnimations> getAnimations() {
		if(animations == null)
			animations = new LinkedList<>();
		return animations;
	}

	public void setAnimations(List<GDXAnimations> animations) {
		this.animations = animations;
	}
	
	/**
	 * @return list of strings for assets. Really should pluginify all this,
	 * then ask each plugin for its list. Then each complex object should
	 * yield its own assets, along with type. For now want quick n dirty way
	 * to get them. 
	 * 
	 * TODO probably should return type as well, e.g. AssetPath / FileType pair
	 */
	public List<String> createAssetList(){
		HashSet<String> assets = new HashSet<String>();
		for(GDXBackground object : backgrounds)
			assets.add(object.getTexture());
		for(GDXShape object : getShapes())
			assets.add(object.getResource());
		for(GDXQuest object : getQuests()){
			//TODO ask quest itself what resources it needs
			//dialog sound only so far, but handling sound differently
			if(Properties.getBool("dialog.useportrait", false) && 
					object.getManifestation() instanceof DialogManifestation)
				assets.add(((DialogManifestation)object.getManifestation()).getOrigin().replace(" ", ""));
		}
		for(Iterator<String> iter = assets.iterator(); iter.hasNext();)
			if(iter.next().equals(""))
				iter.remove();
		return new ArrayList<String>(assets);
	}

	public AssetManager createAssetManager(boolean block){
		AssetManager wrapper = new AssetManager();
		for(String name : createAssetList())
			wrapper.load(name, Texture.class);
		if(block)
			wrapper.finishLoading();
		return wrapper;
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
}
