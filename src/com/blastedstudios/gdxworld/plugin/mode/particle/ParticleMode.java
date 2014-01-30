package com.blastedstudios.gdxworld.plugin.mode.particle;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXParticle;

@PluginImplementation
public class ParticleMode extends AbstractMode {
	private ParticleWindow particleWindow;
	private GDXParticle lastTouched;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("PathMode.touchDown", "x="+x+ " y="+y);
		GDXParticle particle = screen.getLevel().getClosestParticle(coordinates.x, coordinates.y);
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || particle == null || 
				particle.getPosition().dst(coordinates.x, coordinates.y) > LevelEditorScreen.getNodeRadius())
			particle = new GDXParticle();
		if(particleWindow == null)
			screen.getStage().addActor(particleWindow = new ParticleWindow(this, screen.getSkin(), particle));
		lastTouched = particle;
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		shift();
		return false;
	}
	
	public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x, y, arg2, arg3);
		shift();
		lastTouched = null;
		return false;
	}
	
	private void shift(){
		if(lastTouched != null){
			Gdx.app.debug("ParticleMode.shift", lastTouched.toString() + " to " + coordinates);
			if(particleWindow != null)
				particleWindow.setPosition(coordinates.x, coordinates.y);
		}
	}

	public void addParticle(GDXParticle particle) {
		Gdx.app.log("ParticleMode.addParticle", particle.toString());
		if(!screen.getLevel().getParticles().contains(particle))
			screen.getLevel().getParticles().add(particle);
	}

	public void removeParticle(GDXParticle particle) {
		Gdx.app.log("ParticleMode.removeParticle", particle.toString());
		screen.getLevel().getParticles().remove(particle);
	}

	@Override public boolean contains(float x, float y) {
		return particleWindow != null && particleWindow.contains(x, y);
	}

	@Override public void clean() {
		if(particleWindow != null)
			particleWindow.remove();
		particleWindow = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXParticle npc : level.getParticles())
			addParticle(npc);
	}
	
	@Override public void render(float delta, OrthographicCamera camera, GDXRenderer gdxRenderer, ShapeRenderer renderer){
		renderer.setProjectionMatrix(camera.combined);
		renderer.setColor(Color.RED);
		renderer.begin(ShapeType.Line);
		if(!screen.isLive())
			for(GDXParticle object : screen.getLevel().getParticles())
				renderer.circle(object.getPosition().x, object.getPosition().y, LevelEditorScreen.getNodeRadius(), 12);
		renderer.setColor(new Color(.9f, 0.5f, 0.5f, 1));
		if(particleWindow != null)
			renderer.circle(particleWindow.getPosition().x, particleWindow.getPosition().y, LevelEditorScreen.getNodeRadius());
		renderer.end();
	};

	@Override public int getLoadPriority() {
		return 40;
	}
}
