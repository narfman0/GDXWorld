package com.blastedstudios.gdxworld.plugin.quest.trigger.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

/**
 * Trigger for when a player presses a particular key
 */
public class InputTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final InputTrigger DEFAULT = new InputTrigger();
	private int input = Keys.W;
	
	public InputTrigger(){}
	
	public InputTrigger(int input){
		this.input = input;
	}
	
	@Override public boolean activate() {
		return Gdx.input.isKeyPressed(input);
	}

	@Override public AbstractQuestTrigger clone() {
		return super.clone(new InputTrigger(input));
	}

	@Override public String toString() {
		return "[InputTrigger input:" + input + "]";
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}
}
