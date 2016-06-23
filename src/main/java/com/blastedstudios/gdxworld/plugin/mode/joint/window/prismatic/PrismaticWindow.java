package com.blastedstudios.gdxworld.plugin.mode.joint.window.prismatic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointWindow;
import com.blastedstudios.gdxworld.plugin.mode.joint.JointMode;
import com.blastedstudios.gdxworld.util.AxisCalculatorWindow;
import com.blastedstudios.gdxworld.util.AxisCalculatorWindow.IAxisReceiver;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.PrismaticJoint;

public class PrismaticWindow extends BaseJointWindow  {
	private final PrismaticJoint joint;
	private AxisCalculatorWindow axisWindow;
	private final PrismaticTable table;
	
	public PrismaticWindow(final Skin skin, final JointMode mode, final PrismaticJoint joint){
		super("Prismatic Editor", skin, JointType.PrismaticJoint, mode, joint);
		this.joint = joint;
		ClickListener axisSelectListener = new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(axisWindow != null)
					axisWindow.remove();
				IAxisReceiver receiver = new IAxisReceiver() {
					@Override public void setAxis(Vector2 axis) {
						axis.nor();
						table.setAxisTable(axis);
						axisWindow.remove();
						axisWindow = null;
					}
				};
				axisWindow = new AxisCalculatorWindow(skin, joint.getAxis(), receiver, true);
				mode.getScreen().getStage().addActor(axisWindow);
			}
		};
		add(table = new PrismaticTable(skin, joint, axisSelectListener)).colspan(2);
		row();
		add(createControlTable()).colspan(2);
		pack();
	}
	
	@Override public void apply(){
		table.apply(joint);
	}

	@Override public boolean clicked(Vector2 pos, GDXLevel level) {
		return (axisWindow != null && axisWindow.clicked(pos)) || table.clicked(pos, level);
	}

	@Override public Vector2 getCenter() {
		return table.getCenter();
	}
	
	@Override public boolean remove(){
		if(axisWindow != null)
			axisWindow.remove();
		return super.remove();
	}
}
