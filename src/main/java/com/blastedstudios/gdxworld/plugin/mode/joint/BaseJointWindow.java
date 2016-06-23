package com.blastedstudios.gdxworld.plugin.mode.joint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.GDXJoint;

public abstract class BaseJointWindow extends AbstractWindow {
	private final Skin skin;
	private final JointMode mode;
	private final GDXJoint joint;
	
	public BaseJointWindow(final String title, final Skin skin, 
			JointType jointType, JointMode mode, GDXJoint joint){
		super(title, skin);
		this.skin = skin;
		this.mode = mode;
		this.joint = joint;
		setX(Gdx.graphics.getWidth());
		setMovable(false);
	}
	
	protected Table createControlTable(){
		Table controls = new Table();
		final Button addButton = new TextButton("Add", skin);
		final Button updateButton = new TextButton("Update", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		addButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				apply();
				if(mode.addJoint(joint))
					mode.getJointWindow().removeBaseJointWindow();
			}
		});
		updateButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				apply();
				mode.addJoint(joint);
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.removeJoint(joint);
				mode.getJointWindow().removeBaseJointWindow();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.getJointWindow().removeBaseJointWindow();
			}
		});
		controls.add(addButton);
		controls.add(updateButton);
		controls.add(deleteButton);
		controls.add(cancelButton);
		return controls;
	}
	
	/**
	 * apply ui values to joint 
	 */
	abstract public void apply();
	
	/**
	 * @param gdxLevel 
	 * @return true if event was consumed
	 */
	abstract public boolean clicked(Vector2 loc, GDXLevel level);

	abstract public Vector2 getCenter();
}
