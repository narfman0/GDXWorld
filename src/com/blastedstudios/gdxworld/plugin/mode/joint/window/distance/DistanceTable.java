package com.blastedstudios.gdxworld.plugin.mode.joint.window.distance;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.blastedstudios.gdxworld.plugin.mode.joint.BaseJointTable;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.joint.DistanceJoint;

public class DistanceTable extends BaseJointTable {
	private final VertexTable anchorATable, anchorBTable;
	private final TextField dampeningRatioField, frequencyHzField, lengthField;

	public DistanceTable(Skin skin, DistanceJoint joint){
		super(skin, joint, JointType.DistanceJoint);
		anchorATable = new VertexTable(joint.getAnchorA().cpy(), skin);
		anchorBTable = new VertexTable(joint.getAnchorB().cpy(), skin);
		dampeningRatioField = new TextField(joint.getDampeningRatio()+"", skin);
		dampeningRatioField.setMessageText("<dampening ratio>");
		frequencyHzField = new TextField(joint.getFrequencyHz()+"", skin);
		frequencyHzField.setMessageText("<frequency hz>");
		lengthField = new TextField(joint.getLength()+"", skin);
		lengthField.setMessageText("<length>");
		add("Anchor A: ");
		add(anchorATable);
		row();
		add("Anchor B: ");
		add(anchorBTable);
		row();
		add("Dampening Ratio: ");
		add(dampeningRatioField);
		row();
		add("Frequency Hz: ");
		add(frequencyHzField);
		row();
		add("Length: ");
		add(lengthField);
	}
	
	public void apply(DistanceJoint joint){
		super.apply(joint);
		joint.setAnchorA(anchorATable.getVertex());
		joint.setAnchorB(anchorBTable.getVertex());
		joint.setDampeningRatio(Float.parseFloat(dampeningRatioField.getText()));
		joint.setFrequencyHz(Float.parseFloat(frequencyHzField.getText()));
		joint.setLength(Float.parseFloat(lengthField.getText()));
	}

	@Override public boolean clicked(Vector2 pos, GDXLevel level) {
		if(!super.clicked(pos, level))
			anchorATable.setVertex(pos.x, pos.y);
		return true;
	}

	@Override public Vector2 getCenter() {
		return anchorATable.getVertex().sub(anchorBTable.getVertex()).scl(.5f);
	}
}
