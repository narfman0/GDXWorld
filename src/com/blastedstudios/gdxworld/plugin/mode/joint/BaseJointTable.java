package com.blastedstudios.gdxworld.plugin.mode.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;
import com.blastedstudios.gdxworld.world.shape.GDXShape;

public abstract class BaseJointTable extends Table{
	private final TextField nameField, bodyAField, bodyBField;
	private final CheckBox collideConnectedBox;
	private JointType jointType;
	private TextField selectField = null;

	public BaseJointTable(Skin skin, GDXJoint joint, JointType jointType){
		super(skin);
		this.jointType = jointType;
		nameField = new TextField(joint.getName(), skin);
		nameField.setMessageText("<name>");
		bodyAField = new TextField(joint.getBodyA(), skin);
		bodyAField.setMessageText("<bodyA>");
		Button bodyASelectButton = new TextButton("+", skin);
		bodyASelectButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selectField = bodyAField;
			}
		});
		Table bodyATable = new Table(skin);
		bodyATable.add(bodyAField);
		bodyATable.add(bodyASelectButton);
		bodyBField = new TextField(joint.getBodyB(), skin);
		bodyBField.setMessageText("<bodyB>");
		Button bodyBSelectButton = new TextButton("+", skin);
		bodyBSelectButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selectField = bodyBField;
			}
		});
		Table bodyBTable = new Table(skin);
		bodyBTable.add(bodyBField);
		bodyBTable.add(bodyBSelectButton);
		collideConnectedBox = new CheckBox("", skin);
		collideConnectedBox.setChecked(joint.isCollideConnected());
		add(new Label("Name: ", skin));
		add(nameField);
		row();
		add(new Label("Body A: ", skin));
		add(bodyATable);
		row();
		add(new Label("Body B: ", skin));
		add(bodyBTable);
		row();
		add(new Label("Collide Connected: ", skin));
		add(collideConnectedBox);
		row();//between base and inherited table
	}
	
	public void apply(GDXJoint joint){
		joint.setBodyA(bodyAField.getText());
		joint.setBodyB(bodyBField.getText());
		joint.setCollideConnected(collideConnectedBox.isChecked());
		joint.setName(nameField.getText());
		joint.setJointType(jointType);
	}
	
	public boolean clicked(Vector2 loc, GDXLevel level){
		if(selectField != null){
			GDXShape shape = level.getClosestShape(loc.x, loc.y);
			if(shape != null)
				selectField.setText(shape.getName());
			selectField = null;
			return true;
		}
		return false;
	}
	
	public abstract Vector2 getCenter();
}
