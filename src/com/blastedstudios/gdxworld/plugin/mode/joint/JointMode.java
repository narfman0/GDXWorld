package com.blastedstudios.gdxworld.plugin.mode.joint;

import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.blastedstudios.gdxworld.plugin.mode.circle.CircleMode;
import com.blastedstudios.gdxworld.plugin.mode.polygon.PolygonMode;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.PluginUtil.Dependency;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.joint.GearJoint;

@PluginImplementation
@Dependency(classes={CircleMode.class,PolygonMode.class})
public class JointMode extends AbstractMode {
	private final Map<String, Joint> joints = new HashMap<>();
	private JointWindow jointWindow;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("JointMode.touchDown", "x="+x+ " y="+y);
		GDXJoint joint = screen.getLevel().getClosestJoint(coordinates.x, coordinates.y, screen.getWorld());
		if(joint != null && joint.getCenter().dst(coordinates.x, coordinates.y) < LevelEditorScreen.getNodeRadius())
			jointWindow.setSelected(joint);
		jointWindow.clicked(coordinates);
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		jointWindow.clicked(coordinates);
		return false;
	}
	
	@Override public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		jointWindow.clicked(coordinates);
		return false;
	}

	/**
	 * @return true if successful
	 */
	public boolean addJoint(GDXJoint joint){
		if(joint instanceof GearJoint){
			GearJoint gear = (GearJoint)joint;
			gear.initialize(joints.get(gear.getJoint1()), joints.get(gear.getJoint2()));
		}
		//destroy previous and make new joint
		if(joints.containsKey(joint.getName()))
			screen.getWorld().destroyJoint(joints.remove(joint.getName()));
		try{
			joints.put(joint.getName(), joint.attach(screen.getWorld()));
			//add to level
			if(!screen.getLevel().getJoints().contains(joint))
				screen.getLevel().getJoints().add(joint);
			Gdx.app.log("JointMode.addJoint", "Added joint " + joint.toString());
			return true;
		}catch(Exception e){
			Gdx.app.error("JointMode.addJoint", "Failed to add joint " + joint.toString() +
					", exception: " + e.getClass() + ": " + e.getMessage());
			return false;
		}
	}

	public void removeJoint(GDXJoint joint) {
		Gdx.app.log("JointMode.removeJoint", "Removing joint " + joint.toString());
		if(joints.containsKey(joint.getName()))
			screen.getWorld().destroyJoint(joints.remove(joint.getName()));
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
		joints.clear();
		for(GDXJoint joint : level.getJoints())
			addJoint(joint);
	}

	@Override public void start() {
		super.start();
		screen.getStage().addActor(jointWindow = new JointWindow(screen.getSkin(), screen, this));
	}

	@Override public void render(SpriteBatch batch, float delta, OrthographicCamera camera, 
			ShapeRenderer renderer, GDXRenderer gdxRenderer){
		if(!screen.isLive()){
			renderer.setColor(Color.GREEN);
			for(GDXJoint object : screen.getLevel().getJoints())
				renderer.circle(object.getCenter().x, object.getCenter().y, LevelEditorScreen.getNodeRadius(), 12);
			if(jointWindow != null && jointWindow.getBaseWindow() != null){
				renderer.setColor(new Color(0, .9f, 0, 1));//DARK_GREEN
				Vector2 center = jointWindow.getBaseWindow().getCenter();
				renderer.circle(center.x, center.y, LevelEditorScreen.getNodeRadius(), 12);
			}
		}
	}
	
	public JointWindow getJointWindow(){
		return jointWindow;
	}
}
