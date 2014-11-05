package com.blastedstudios.gdxworld.plugin.mode.npc;

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
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXNPC;

@PluginImplementation
public class NPCMode extends AbstractMode {
	private NPCWindow npcWindow;
	private GDXNPC lastTouched;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Log.debug("NPCMode.touchDown", "x="+x+ " y="+y);
		GDXNPC npc = screen.getLevel().getClosestNPC(coordinates.x, coordinates.y);
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || npc == null || 
				npc.getCoordinates().dst(coordinates.x, coordinates.y) > LevelEditorScreen.getNodeRadius())
			npc = new GDXNPC();
		if(npcWindow == null)
			screen.getStage().addActor(npcWindow = new NPCWindow(screen.getSkin(), this, npc));
		npcWindow.setCoordinates(coordinates);
		lastTouched = npc;
		return false;
	}
	
	@Override public boolean touchDragged(int x, int y, int ptr){
		super.touchDragged(x, y, ptr);
		shift();
		return false;
	}
	
	public boolean touchUp(int x, int y, int arg2, int arg3){
		super.touchUp(x,y,arg2,arg3);
		shift();
		lastTouched = null;
		return false;
	}
	
	private void shift(){
		if(lastTouched != null){
			Log.debug("NPCMode.touchDown", lastTouched.toString() + " to " + coordinates);
			if(npcWindow != null)
				npcWindow.setCoordinates(coordinates);
		}
	}

	public void addNPC(GDXNPC npc){
		Log.log("NPCMode.addNPC", npc.toString());
		if(!screen.getLevel().getNpcs().contains(npc))
			screen.getLevel().getNpcs().add(npc);
	}

	public void removeNPC(GDXNPC npc) {
		Log.log("NPCMode.removeNPC", npc.toString());
		screen.getLevel().getNpcs().remove(npc);
	}

	@Override public boolean contains(float x, float y) {
		return npcWindow != null && npcWindow.contains(x, y);
	}

	@Override public void clean() {
		if(npcWindow != null)
			npcWindow.remove();
		npcWindow = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXNPC npc : level.getNpcs())
			addNPC(npc);
	}
	
	@Override public void render(float delta, OrthographicCamera camera, GDXRenderer gdxRenderer, ShapeRenderer renderer){
		renderer.setProjectionMatrix(camera.combined);
		renderer.setColor(Color.PINK);
		renderer.begin(ShapeType.Line);
		if(!screen.isLive())
			for(GDXNPC object : screen.getLevel().getNpcs())
				renderer.circle(object.getCoordinates().x, object.getCoordinates().y, LevelEditorScreen.getNodeRadius(), 12);
		renderer.setColor(new Color(.9f, 0.5f, 0.5f, 1));
		if(npcWindow != null)
			renderer.circle(npcWindow.getCoordinates().x, npcWindow.getCoordinates().y, LevelEditorScreen.getNodeRadius());
		renderer.end();
	};

	@Override public int getLoadPriority() {
		return 90;
	}
}
