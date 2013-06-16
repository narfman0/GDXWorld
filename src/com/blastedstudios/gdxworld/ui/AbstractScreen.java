package com.blastedstudios.gdxworld.ui;

import com.blastedstudios.gdxworld.util.GDXGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.blastedstudios.gdxworld.util.Properties;

public abstract class AbstractScreen implements Screen, InputProcessor{
	private static final int GL_CLEAR = Gdx.graphics.isGL20Available() ? 
			GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT : 
			GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT;
	protected final Stage stage;
	protected static Skin skin;
	protected final GDXGame game;
	protected final InputMultiplexer inputMultiplexer;
	
	public AbstractScreen(final GDXGame game){
		this.game = game;
		if(skin == null)
			skin = new Skin(Gdx.files.internal(Properties.get("screen.skin","data/ui/uiskin.json")));
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		Gdx.input.setInputProcessor(inputMultiplexer = new InputMultiplexer());
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
	}

	@Override public void render(float delta) {
		Gdx.gl.glClear(GL_CLEAR);
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
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}
	
	@Override public void show() {
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override public boolean keyDown(int arg0) {
		return false;
	}

	@Override public boolean keyTyped(char arg0) {
		return false;
	}

	@Override public boolean keyUp(int arg0) {
		return false;
	}

	@Override public boolean mouseMoved(int x, int y) {
		return false;
	}

	@Override public boolean scrolled(int amount) {
		return false;
	}

	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		return false;
	}

	@Override public boolean touchDragged(int x, int y, int ptr) {
		return false;
	}

	@Override public boolean touchUp(int x, int y, int x1, int y1) {
		return false;
	}
}
