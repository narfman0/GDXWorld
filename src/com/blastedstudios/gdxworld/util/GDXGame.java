package com.blastedstudios.gdxworld.util;

import java.util.Stack;

import com.badlogic.gdx.Game;
import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.ui.AbstractScreen;

public abstract class GDXGame extends Game {
	private Stack<AbstractScreen> screenStack = new Stack<>();

	public void pushScreen(AbstractScreen screen){
		screenStack.push(screen);
		setScreen(screen);
	}
	
	public AbstractScreen popScreen(){
		AbstractScreen previous = screenStack.pop();
		if(!screenStack.isEmpty())
			setScreen(screenStack.peek());
		return previous;
	}
}
