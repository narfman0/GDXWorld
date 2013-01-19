package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.GDXWindow;

public class JointWindow extends GDXWindow {
	public JointWindow(Skin skin, LevelEditorScreen levelEditorScreen) {
		super("Joint Editor", skin);
		List typeList = new List(JointType.values(), skin);
		final Button newButton = new TextButton("New", skin);
		newButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		add(typeList);
		row();
		add(newButton);
		setMovable(false);
		pack();
	}

	public void clicked(Vector2 vector2) {
		// TODO Auto-generated method stub
		
	}

}
