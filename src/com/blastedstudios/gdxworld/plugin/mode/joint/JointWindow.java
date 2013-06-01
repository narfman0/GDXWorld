package com.blastedstudios.gdxworld.plugin.mode.joint;

import java.lang.reflect.Constructor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.*;

class JointWindow extends AbstractWindow {
	private BaseJointWindow baseWindow;
	private final Skin skin;
	private final JointMode mode;
	private final LevelEditorScreen levelEditorScreen;
	
	public JointWindow(final Skin skin, final LevelEditorScreen levelEditorScreen,
			final JointMode mode) {
		super("Joint Editor", skin);
		this.skin = skin;
		this.mode = mode;
		this.levelEditorScreen = levelEditorScreen;
		final List typeList = new List(JointType.values(), skin);
		final Button newButton = new TextButton("New", skin);
		newButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				createBaseWindow(JointType.values()[typeList.getSelectedIndex()], null);
			}
		});
		add(typeList);
		row();
		add(newButton);
		setMovable(false);
		pack();
	}
	
	private void createBaseWindow(JointType type, GDXJoint joint){
		if(baseWindow != null)
			baseWindow.remove();
		try{
			Class<?> jointClass = Class.forName(GDXJoint.class.getPackage().getName() + "." + type.name());
			GDXJoint gdxJoint = (joint == null ? (GDXJoint)jointClass.getConstructor().newInstance() : joint);
			String windowClassName = BaseJointWindow.class.getPackage().getName() + "." + type.name().replace("Joint", "Window");
			Class<?> windowClass = Class.forName(windowClassName);
			Constructor<?> windowConstructor = windowClass.getConstructor(Skin.class, JointMode.class, gdxJoint.getClass());
			baseWindow = (BaseJointWindow) windowConstructor.newInstance(skin, mode, gdxJoint);
		}catch(Exception e){
			Gdx.app.log("JointWindow.createBaseWindow", "Exception creating window for type: " + type + ", error: " + e.getMessage());
		}
		if(baseWindow != null)
			levelEditorScreen.getStage().addActor(baseWindow);
	}

	public void clicked(Vector2 pos) {
		if(baseWindow != null)
			baseWindow.clicked(pos);
	}

	@Override public boolean remove(){
		removeBaseJointWindow();
		return super.remove();
	}

	public void removeBaseJointWindow() {
		if(baseWindow != null){
			baseWindow.remove();
			baseWindow = null;
		}
	}

	public boolean contains(float x, float y){
		return super.contains(x, y) || (baseWindow != null && baseWindow.contains(x, y));
	}

	public void setSelected(GDXJoint joint) {
		if(baseWindow == null)
			createBaseWindow(joint.getJointType(), joint);
	}

	public BaseJointWindow getBaseWindow() {
		return baseWindow;
	}
}
