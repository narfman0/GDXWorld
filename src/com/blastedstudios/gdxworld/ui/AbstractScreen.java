package com.blastedstudios.gdxworld.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class AbstractScreen implements Screen, InputProcessor{
	protected final Stage stage;
	protected static Skin skin;
	protected final Game game;
	protected final InputMultiplexer inputMultiplexer;
	
	public AbstractScreen(final Game game){
		this.game = game;
		if(skin == null)
			skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		Gdx.input.setInputProcessor(inputMultiplexer = new InputMultiplexer());
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
	}

	@Override public void render(float delta) {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Skin getSkin(){
		return skin;
	}
	
	@Override public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}
	@Override public void dispose() {}
	@Override public void hide() {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void show() {}

	@Override public boolean keyDown(int arg0) {
		return false;
	}

	@Override public boolean keyTyped(char arg0) {
		return false;
	}

	@Override public boolean keyUp(int arg0) {
		return false;
	}

	@Override public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override public boolean scrolled(int amount) {
		return false;
	}

	@Override public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	@Override public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}
}
