package com.blastedstudios.gdxworld.plugin.mode.joint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;

abstract class BaseJointWindow extends AbstractWindow {
	private final TextField nameField, bodyAField, bodyBField;
	private final CheckBox collideConnectedBox;
	private JointType jointType;
	private final Skin skin;
	private final JointMode mouseMode;
	private final GDXJoint joint;
	
	public BaseJointWindow(final String title, final Skin skin, 
			JointType jointType, JointMode mouseMode, GDXJoint joint){
		super(title, skin);
		this.skin = skin;
		this.jointType = jointType;
		this.mouseMode = mouseMode;
		this.joint = joint;
		nameField = new TextField(joint.getName(), skin);
		nameField.setMessageText("<name>");
		bodyAField = new TextField(joint.getBodyA(), skin);
		bodyAField.setMessageText("<bodyA>");
		bodyBField = new TextField(joint.getBodyB(), skin);
		bodyBField.setMessageText("<bodyB>");
		collideConnectedBox = new CheckBox("", skin);
		collideConnectedBox.setChecked(joint.isCollideConnected());
		add(new Label("Name: ", skin));
		add(nameField);
		row();
		add(new Label("Body A: ", skin));
		add(bodyAField);
		row();
		add(new Label("Body B: ", skin));
		add(bodyBField);
		row();
		add(new Label("Collide Connected: ", skin));
		add(collideConnectedBox);
		row();
		setX(Gdx.graphics.getWidth());
		setMovable(false);
	}
	
	protected void addControlTable(){
		final Button createButton = new TextButton("Create", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		createButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mouseMode.addJoint(generate());
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mouseMode.removeJoint(joint);
			}
		});
		row();
		add(createButton);
		add(deleteButton);
	}
	
	/**
	 * apply ui values to joint 
	 */
	protected GDXJoint apply(GDXJoint joint){
		joint.setBodyA(bodyAField.getText());
		joint.setBodyB(bodyBField.getText());
		joint.setCollideConnected(collideConnectedBox.isChecked());
		joint.setName(nameField.getText());
		joint.setJointType(jointType);
		return joint;
	}
	
	public abstract GDXJoint generate();
	public abstract void clicked(Vector2 vector2);
}
