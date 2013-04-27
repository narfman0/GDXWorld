package com.blastedstudios.gdxworld.world.quest.trigger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

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
		return new InputTrigger(input);
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
