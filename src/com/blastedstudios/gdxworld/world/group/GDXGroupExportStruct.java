package com.blastedstudios.gdxworld.world.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public class GDXGroupExportStruct{
	public final String name;
	public final List<GDXCircle> circles;
	public final List<GDXPolygon> polygons;
	public final List<GDXJoint> joints;

	public GDXGroupExportStruct(String name, List<GDXCircle> circles, 
			List<GDXPolygon> polygons, List<GDXJoint> joints){
		this.name = name;
		this.circles = circles;
		this.polygons = polygons;
		this.joints = joints;
	}

	public GDXGroupExportStruct translate(Vector2 translate){
		for(GDXCircle circle : circles)
			circle.getCenter().add(translate);
		for(GDXPolygon object : polygons)
			object.getCenter().add(translate);
		for(GDXJoint object : joints)
			object.translate(translate);
		return this;
	}
	
	public GDXGroup create(){
		List<String> circles = new ArrayList<>(), 
				polygons = new ArrayList<>(), 
				joints = new ArrayList<>();
		for(GDXCircle object : this.circles)
			circles.add(object.getName());
		for(GDXPolygon object : this.polygons)
			polygons.add(object.getName());
		for(GDXJoint object : this.joints)
			joints.add(object.getName());
		return new GDXGroup(name, circles, polygons, joints);
	}
	
	/**
	 * @param scalar - magnitude to scale the group
	 */
	public void scl(float scalar){
		for(GDXCircle circle : circles){
			circle.getCenter().scl(scalar);
			circle.setRadius(circle.getRadius()*scalar);
		}
		for(GDXPolygon polygon : polygons){
			polygon.scl(scalar);
			polygon.getCenter().scl(scalar);
		}
		for(GDXJoint joint : joints)
			joint.scl(scalar);
	}
	
	/**
	 * @return map of instantiated bodies. Will convert names if collision
	 */
	public Map<String, Body> instantiate(World world, Vector2 location){
		translate(location);
		HashMap<String, Object> usedBodyNames = new HashMap<>();
		HashMap<String, String> reverseBodyNames = new HashMap<>();
		Array<Body> bodyArray = new Array<>(world.getBodyCount());
		world.getBodies(bodyArray);
		for(Body body : bodyArray)
			if(body.getUserData() instanceof String)
				usedBodyNames.put((String)body.getUserData(), body);
		HashMap<String, Body> bodies = new HashMap<>();
		List<GDXShape> shapes = new ArrayList<>();
		shapes.addAll(circles);
		shapes.addAll(polygons);
		for(GDXShape shape : shapes){
			String name = shape.getName();
			while(usedBodyNames.containsKey(name)){
				if(name.contains("-")){
					int count = Integer.parseInt(name.substring(name.indexOf('-')+1));
					name = name.replace("-" + count, "-" + (count+1));
				}else
					name += "-1";
			}
			usedBodyNames.put(name, shape);
			reverseBodyNames.put(shape.getName(), name);
			shape.setName(name);
			bodies.put(shape.getName(), shape.createFixture(world, false));
		}
		for(GDXJoint joint : joints){
			joint.setBodyA(reverseBodyNames.get(joint.getBodyA()));
			joint.setBodyB(reverseBodyNames.get(joint.getBodyB()));
			joint.attach(world);
		}
		return bodies;
	}
}