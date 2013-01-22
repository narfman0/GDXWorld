package com.blastedstudios.gdxworld.ui.leveleditor.mousemode;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.joints.JointWindow;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.GearJoint;

public class JointMouseMode extends AbstractMouseMode {
	private JointWindow jointWindow;
	private LevelEditorScreen screen;
	
	public JointMouseMode(LevelEditorScreen screen){
		super(screen.getCamera());
		this.screen = screen;
		screen.getStage().addActor(jointWindow = new JointWindow(screen.getSkin(), screen, this));
	}
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("PolygonMouseMode.touchDown", "x="+x+ " y="+y);
		GDXJoint joint = screen.getLevel().getClosestJoint(coordinates.x, coordinates.y, screen.getWorld());
		if(joint != null && joint.getCenter().dst(coordinates.x, coordinates.y) < LevelEditorScreen.NODE_RADIUS)
			jointWindow.setSelected(joint);
		jointWindow.clicked(new Vector2(coordinates.x, coordinates.y));
		return false;
	}

	public void addJoint(GDXJoint gdxJoint) {
		removeJoint(gdxJoint);
		if(gdxJoint instanceof GearJoint){
			GearJoint gear = (GearJoint)gdxJoint;
			gear.initialize(screen.getJoints().get(gear.getJoint1()), screen.getJoints().get(gear.getJoint2()));
		}
		screen.getJoints().put(gdxJoint.getName(), gdxJoint.attach(screen.getWorld()));
		if(!screen.getLevel().getJoints().contains(gdxJoint))
			screen.getLevel().getJoints().add(gdxJoint);
		if(!screen.isLive()){
			Body body = PhysicsHelper.createCircle(screen.getWorld(), LevelEditorScreen.NODE_RADIUS, gdxJoint.getCenter(), BodyType.DynamicBody);
			screen.getBodies().put(gdxJoint.getName(), Arrays.asList(body));
		}
		Gdx.app.log("LevelEditorScreen.addJoint", "Added joint " + gdxJoint.toString());
	}

	public void removeJoint(GDXJoint joint) {
		Gdx.app.log("LevelEditorScreen.removeJoint", "Removing joint " + joint.toString());
		if(screen.getBodies().containsKey(joint.getName()))
			for(Body body : screen.getBodies().remove(joint.getName()))
				screen.getWorld().destroyBody(body);
		if(screen.getJoints().containsKey(joint.getName()))
			screen.getWorld().destroyJoint(screen.getJoints().remove(joint.getName()));
		if(screen.getLevel().getJoints().contains(joint))
			screen.getLevel().getJoints().remove(joint);
	}

	@Override public boolean contains(float x, float y) {
		return jointWindow != null && jointWindow.contains(x, y);
	}

	@Override public void clean() {
		if(jointWindow != null)
			jointWindow.remove();
		jointWindow = null;
	}

}
