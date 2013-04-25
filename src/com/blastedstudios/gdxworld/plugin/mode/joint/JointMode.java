package com.blastedstudios.gdxworld.plugin.mode.joint;

import java.util.Arrays;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.plugin.mode.circle.CircleMode;
import com.blastedstudios.gdxworld.plugin.mode.polygon.PolygonMode;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.util.PluginUtil.Dependency;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.GearJoint;

@PluginImplementation
@Dependency(classes={CircleMode.class,PolygonMode.class})
public class JointMode extends AbstractMode {
	private JointWindow jointWindow;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("JointMode.touchDown", "x="+x+ " y="+y);
		GDXJoint joint = screen.getLevel().getClosestJoint(coordinates.x, coordinates.y, screen.getWorld());
		if(joint != null && joint.getCenter().dst(coordinates.x, coordinates.y) < LevelEditorScreen.getNodeRadius())
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
			Body body = PhysicsHelper.createCircle(screen.getWorld(), LevelEditorScreen.getNodeRadius(), gdxJoint.getCenter(), BodyType.DynamicBody);
			screen.getBodies().put(gdxJoint.getName(), Arrays.asList(body));
		}
		Gdx.app.log("JointMode.addJoint", "Added joint " + gdxJoint.toString());
	}

	public void removeJoint(GDXJoint joint) {
		Gdx.app.log("JointMode.removeJoint", "Removing joint " + joint.toString());
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

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXJoint joint : level.getJoints())
			addJoint(joint);
	}

	@Override public void start() {
		super.start();
		screen.getStage().addActor(jointWindow = new JointWindow(screen.getSkin(), screen, this));
	}
}
