package com.blastedstudios.gdxworld.plugin.mode.path;

import java.util.List;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.AbstractMode;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXPath;

@PluginImplementation
public class PathMode extends AbstractMode {
	private PathWindow pathWindow;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Log.debug("PathMode.touchDown", "x="+x+ " y="+y);
		GDXPath path = screen.getLevel().getClosestPath(coordinates.x, coordinates.y);
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || path == null || 
				path.getClosestNode(coordinates.x, coordinates.y).
				dst(coordinates.x, coordinates.y) > LevelEditorScreen.getNodeRadius())
			path = new GDXPath();
		if(pathWindow == null)
			screen.getStage().addActor(pathWindow = new PathWindow(screen.getSkin(), this, path));
		if(path.getNodes().isEmpty())
			pathWindow.add(coordinates.cpy());
		return false;
	}

	public void addPath(GDXPath path) {
		Log.log("PathMode.addPath", path.toString());
		if(!screen.getLevel().getPaths().contains(path))
			screen.getLevel().getPaths().add(path);
	}

	public void removePath(GDXPath path) {
		Log.log("PathMode.removePath", path.toString());
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
	
	@Override public void render(float delta, OrthographicCamera camera, GDXRenderer gdxRenderer, ShapeRenderer renderer){
		if(!screen.isLive()){
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeType.Line);
			for(GDXPath object : screen.getLevel().getPaths())
				renderNodeList(object.getNodes(), renderer, Color.GRAY);
			if(pathWindow != null)
				renderNodeList(pathWindow.getNodes(), renderer, Color.DARK_GRAY);
			renderer.end();
		}
	};
	
	private static void renderNodeList(List<Vector2> nodes, ShapeRenderer renderer, Color color){
		renderer.setColor(color);
		for(int i=0; i<nodes.size(); i++){
			Vector2 node = nodes.get(i);
			renderer.circle(node.x, node.y, LevelEditorScreen.getNodeRadius(), 8);
			if(i>0){
				Vector2 previousNode = nodes.get(i-1);
				renderer.line(node.x, node.y, previousNode.x, previousNode.y);
			}
		}
	}

	@Override public int getLoadPriority() {
		return 50;
	}
}
