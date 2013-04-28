package com.blastedstudios.gdxworld.plugin.mode.quest.trigger;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

public abstract class TriggerTable extends Table{
	public TriggerTable(final Skin skin){
		super(skin);
	}
	
	public abstract AbstractQuestTrigger apply();
	public void touched(Vector2 coordinates){}
}
