package com.blastedstudios.gdxworld.world.group;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.shape.GDXCircle;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

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
	
	public GDXGroup create(LevelEditorScreen screen){
		screen.getLevel().getCircles().addAll(circles);
		screen.getLevel().getPolygons().addAll(polygons);
		screen.getLevel().getJoints().addAll(joints);
		screen.loadLevel();
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
}