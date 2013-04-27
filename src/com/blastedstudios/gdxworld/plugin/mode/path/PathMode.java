package com.blastedstudios.gdxworld.plugin.mode.path;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXPath;

@PluginImplementation
public class PathMode extends AbstractMode {
	private PathWindow pathWindow;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("PathMode.touchDown", "x="+x+ " y="+y);
		GDXPath path = screen.getLevel().getClosestPath(coordinates.x, coordinates.y);
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || path == null || 
				path.getClosestNode(coordinates.x, coordinates.y).
				dst(coordinates.x, coordinates.y) > LevelEditorScreen.getNodeRadius())
			path = new GDXPath();
		if(pathWindow == null)
			screen.getStage().addActor(pathWindow = new PathWindow(screen.getSkin(), this, path));
		Vector2 vertex = new Vector2(coordinates.x, coordinates.y);
		if(path.getNodes().isEmpty())
			pathWindow.add(vertex);
		return false;
	}

	public void addPath(GDXPath path) {
		Gdx.app.log("PathMode.addPath", path.toString());
		if(!screen.getLevel().getPaths().contains(path))
			screen.getLevel().getPaths().add(path);
	}

	public void removePath(GDXPath path) {
		Gdx.app.log("PathMode.removePath", path.toString());
		screen.getLevel().getPaths().remove(path);
	}

	@Override public boolean contains(float x, float y) {
		return pathWindow != null && pathWindow.contains(x, y);
	}

	@Override public void clean() {
		if(pathWindow != null)
			pathWindow.remove();
		pathWindow = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXPath npc : level.getPaths())
			addPath(npc);
	}
	
	@Override public void render(float delta, Camera camera, ShapeRenderer renderer){
		renderer.setColor(Color.GRAY);
		if(!screen.isLive())
			for(GDXPath object : screen.getLevel().getPaths())
				for(int i=0; i<object.getNodes().size(); i++){
					Vector2 node = object.getNodes().get(i);
					renderer.circle(node.x, node.y, LevelEditorScreen.getNodeRadius(), 8);
					if(i>0){
						Vector2 previousNode = object.getNodes().get(i-1);
						renderer.line(node.x, node.y, previousNode.x, previousNode.y);
					}
				}
	};
}
