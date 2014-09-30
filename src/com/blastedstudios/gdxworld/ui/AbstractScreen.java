package com.blastedstudios.gdxworld.ui;

import java.util.LinkedList;

import com.blastedstudios.gdxworld.util.GDXGame;
import com.blastedstudios.gdxworld.util.GDXGameFade.IScreenListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class AbstractScreen implements Screen, InputProcessor{
	private static final int GL_CLEAR = GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT;
	protected final Stage stage;
	protected Skin skin;
	protected final GDXGame game;
	protected final InputMultiplexer inputMultiplexer;
	private final LinkedList<IScreenListener> renderListeners;
	
	public AbstractScreen(final GDXGame game, final Skin skin){
		this.game = game;
		this.skin = skin;
		renderListeners = new LinkedList<>();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(inputMultiplexer = new InputMultiplexer());
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(stage);
	}
	
	public AbstractScreen(final GDXGame game, final String skinPath){
		this(game, new Skin(Gdx.files.internal(skinPath)));
	}

	@Override public void render(float delta) {
		Gdx.gl.glClear(GL_CLEAR);
		stage.act(Math.min(Gdx.graphics.getRawDeltaTime(), 1 / 30f));
		for(IScreenListener listener : renderListeners)
			listener.render(delta);
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Skin getSkin(){
		return skin;
	}
	
	public GDXGame getGame(){
		return game;
	}
	
	@Override public void resize(int width, int height) {
		stage.getViewport().update(width, height);
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

	public LinkedList<IScreenListener> getRenderListeners() {
		return renderListeners;
	}
}
