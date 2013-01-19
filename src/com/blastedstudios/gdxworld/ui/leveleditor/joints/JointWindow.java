package com.blastedstudios.gdxworld.ui.leveleditor.joints;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.GDXWindow;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;

public class JointWindow extends GDXWindow {
	private BaseJointWindow baseWindow;
	
	public JointWindow(final Skin skin, final LevelEditorScreen levelEditorScreen) {
		super("Joint Editor", skin);
		final List typeList = new List(JointType.values(), skin);
		final Button newButton = new TextButton("New", skin);
		newButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(baseWindow != null)
					baseWindow.remove();
				JointType type = JointType.values()[typeList.getSelectedIndex()];
				switch(type){
				case DistanceJoint:
					baseWindow = new DistanceWindow(skin, levelEditorScreen);
					break;
				case WeldJoint:
					baseWindow = new WeldWindow(skin, levelEditorScreen);
					break;
				case RevoluteJoint:
					baseWindow = new RevoluteWindow(skin, levelEditorScreen);
					break;
				default:
					Gdx.app.log("JointWindow.newButton.clicked", "Joint not implemented: " + type);
					break;
				}
				if(baseWindow != null)
					levelEditorScreen.getStage().addActor(baseWindow);
			}
		});
		add(typeList);
		row();
		add(newButton);
		setMovable(false);
		pack();
	}

	public void clicked(Vector2 pos) {
		baseWindow.clicked(pos);
	}

	@Override public boolean remove(){
		if(baseWindow != null)
			baseWindow.remove();
		return super.remove();
	}

	public boolean contains(float x, float y){
		return super.contains(x, y) || (baseWindow != null && baseWindow.contains(x, y));
	}
}
