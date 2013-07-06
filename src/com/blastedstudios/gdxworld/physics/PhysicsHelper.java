package com.blastedstudios.gdxworld.physics;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.blastedstudios.gdxworld.math.decomposers.Clipper;
import com.blastedstudios.gdxworld.math.decomposers.Clipper.Polygonizer;

public class PhysicsHelper {
	public static final PolygonShape POLYGON_SHAPE = new PolygonShape();
	public static final CircleShape CIRCLE_SHAPE = new CircleShape();

	public static Body createCircle(World world, float radius, Vector2 position, BodyType type){
		return createCircle(world, radius, position, type);
	}
	
	public static Body createCircle(World world, float radius, Vector2 position, BodyType type, float density){
		BodyDef def = new BodyDef();
		def.type = type;
		Body body = world.createBody(def);
		CIRCLE_SHAPE.setRadius(radius);
		body.createFixture(CIRCLE_SHAPE, density);
		body.setTransform(position, 0);
		return body;
	}
	
	public static Body createRectangle(World world, float width, float height, Vector2 position, BodyType type){
		return createRectangle(world, width, height, position, type);
	}

	public static Body createRectangle(World world, float width, float height, Vector2 position, BodyType type, float density){
		BodyDef def = new BodyDef();
		def.type = type;
		Body body = world.createBody(def);
		POLYGON_SHAPE.setAsBox(width, height);
		body.createFixture(POLYGON_SHAPE, density);
		body.setTransform(position, 0);
		return body;
	}
	
	public static Body createFixture(World world, FixtureDef fixtureDef, BodyType type,
			List<Vector2> nodes, Shape shape) {
		Vector2[][] verts = Clipper.polygonize(Polygonizer.BAYAZIT, 
				nodes.toArray(new Vector2[nodes.size()]));
		if(verts == null){
			Gdx.app.log("GDXPolygon.createFixture", "Can't create fixture(s),"+
					" verts null. Did a vertex cross the line segment between"+
					" two other vertices (e.g. hourglass)?");
			return null;
		}
		BodyDef bd = new BodyDef();
		bd.type = type;
		Body body = world.createBody(bd); 
		for(Vector2[] vertSet : verts){
			if(shape instanceof PolygonShape)
				((PolygonShape)shape).set(vertSet);
			fixtureDef.shape = shape;
			body.createFixture(fixtureDef);
		}
		return body;
	}
	
	/**
	 * @return closest body to the given x,y in world coordinates
	 */
	public static Body closestBody(World world, float x, float y){
		Body body = null;
		float closestDistance = Float.MAX_VALUE;
		for(Iterator<Body> iter = world.getBodies(); iter.hasNext();){
			Body worldBody = iter.next();
			float distance = worldBody.getPosition().dst2(x,y);
			if(body == null || closestDistance > distance){
				body = worldBody;
				closestDistance = distance;
			}
		}
		return body;
	}
}
