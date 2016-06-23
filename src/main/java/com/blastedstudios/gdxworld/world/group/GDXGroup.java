package com.blastedstudios.gdxworld.world.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

/**
 * A group is a set of references to shapes and joints. The intent is that a
 * user may define a group and import/export them individually for duplication
 * or inclusion in other levels, worlds.
 */
public class GDXGroup implements Cloneable,Serializable{
	private static final long serialVersionUID = 1L;
	private static int count = 0;
	private String name = "Group-" + count++;
	private Vector2 center = new Vector2();
	private List<String> circles = new ArrayList<>(), 
			polygons = new ArrayList<>(), 
			joints = new ArrayList<>();

	public GDXGroup(){}

	public GDXGroup(String name, List<String> circles, List<String> polygons,
			List<String> joints){
		this.name = name;
		this.circles = circles;
		this.polygons = polygons;
		this.joints = joints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCircles() {
		return circles;
	}

	public void setCircles(List<String> circles) {
		this.circles = circles;
	}

	public List<String> getPolygons() {
		return polygons;
	}

	public void setPolygons(List<String> polygons) {
		this.polygons = polygons;
	}

	public List<String> getJoints() {
		return joints;
	}

	public void setJoints(List<String> joints) {
		this.joints = joints;
	}

	public Vector2 getCenter() {
		return center;
	}

	public void setCenter(Vector2 center) {
		this.center = center;
	}

	@Override public Object clone(){
		GDXGroup group = new GDXGroup();
		group.name = name;
		group.center = center.cpy();
		for(String object : circles)
			group.circles.add(object);
		for(String object : polygons)
			group.polygons.add(object);
		for(String object : joints)
			group.joints.add(object);
		return group;
	}

	@Override public String toString(){
		return "[GDXGroup name:" + name + " center=" + center + 
				" circle.size():" + circles.size() + " polygons.size():" + 
				polygons.size() + " joints.size():" + joints.size() + "]";
	}

	public GDXGroupExportStruct exportGroup(GDXLevel level){
		List<GDXCircle> circles = new ArrayList<>();
		List<GDXPolygon> polygons = new ArrayList<>();
		List<GDXJoint> joints = new ArrayList<>();
		for(String objectName : this.circles)
			for(GDXCircle object : level.getCircles())
				if(object.getName().equals(objectName))
					circles.add((GDXCircle) object.clone());
		for(String objectName : this.polygons)
			for(GDXPolygon object : level.getPolygons())
				if(object.getName().equals(objectName))
					polygons.add((GDXPolygon) object.clone());
		for(String objectName : this.joints)
			for(GDXJoint object : level.getJoints())
				if(object.getName().equals(objectName))
					joints.add((GDXJoint) object.clone());
		return new GDXGroupExportStruct(name, circles, polygons, joints).
				translate(center.cpy().scl(-1f));
	}
	
	public static GDXGroup importGroup(GDXGroupExportStruct groupStruct){
		GDXGroup group = new GDXGroup();
		group.name = groupStruct.name;
		for(GDXCircle object : groupStruct.circles)
			group.circles.add(object.getName());
		for(GDXPolygon object : groupStruct.polygons)
			group.polygons.add(object.getName());
		for(GDXJoint object : groupStruct.joints)
			group.joints.add(object.getName());
		return group;
	}
}
