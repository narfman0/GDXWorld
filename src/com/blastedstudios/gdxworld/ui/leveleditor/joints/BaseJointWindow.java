package com.blastedstudios.gdxworld.ui.leveleditor.joints;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;

public abstract class BaseJointWindow extends Window {
	private final TextField nameField, bodyAField, bodyBField;
	private final CheckBox collideConnectedBox;
	private JointType jointType;
	private final Skin skin;
	private final LevelEditorScreen levelEditorScreen;
	
	public BaseJointWindow(final String title, final Skin skin, 
			JointType jointType, LevelEditorScreen levelEditorScreen){
		super(title, skin);
		this.skin = skin;
		this.jointType = jointType;
		this.levelEditorScreen = levelEditorScreen;
		nameField = new TextField("", skin);
		nameField.setMessageText("<name>");
		bodyAField = new TextField("", skin);
		bodyAField.setMessageText("<bodyA>");
		bodyBField = new TextField("", skin);
		bodyBField.setMessageText("<bodyB>");
		collideConnectedBox = new CheckBox("", skin);
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
	}
	
	protected void addCreateButton(){
		final Button createButton = new TextButton("Create", skin);
		createButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				levelEditorScreen.addJoint(generate());
			}
		});
		row();
		add(createButton).colspan(3);
	}
	
	/**
	 * apply ui values to @param joint 
	 */
	protected void apply(GDXJoint joint){
		joint.setBodyA(bodyAField.getText());
		joint.setBodyB(bodyBField.getText());
		joint.setCollideConnected(collideConnectedBox.isChecked());
		joint.setName(nameField.getText());
		joint.setJointType(jointType);
	}
	
	public abstract GDXJoint generate();
}
