package com.blastedstudios.gdxworld.physics;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.math.decomposers.Clipper;
import com.blastedstudios.gdxworld.math.decomposers.Clipper.Polygonizer;

public class PhysicsHelper {
	public static Body createCircle(World world, float radius, Vector2 position, BodyType type, 
			float friction, float restitution, float density){
		return createCircle(world, radius, position, type, friction, restitution, 
				density, (short)1, (short)-1, (short)0);
	}
	
	public static Body createCircle(World world, float radius, Vector2 position, BodyType type, float friction, 
			float restitution, float density, short maskBits, short categoryBits, short groupIndex){
		BodyDef def = new BodyDef();
		def.type = type;
		Body body = world.createBody(def);
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		shape.setPosition(position);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		Fixture fixture = body.createFixture(fd);
		setFilterData(fixture, maskBits, categoryBits, groupIndex);
		return body;
	}
	
	public static Body createRectangle(World world, float width, float height, Vector2 position, 
			BodyType type, float friction, float restitution, float density){
		return createRectangle(world, width, height, position, type, friction, restitution, 
				density, (short)1, (short)-1, (short)0);
	}

	public static Body createRectangle(World world, float width, float height, Vector2 position, 
			BodyType type, float friction, float restitution, float density,
			short maskBits, short categoryBits, short groupIndex){
		BodyDef def = new BodyDef();
		def.type = type;
		Body body = world.createBody(def);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = friction;
		fd.restitution = restitution;
		setFilterData(body.createFixture(fd), maskBits, categoryBits, groupIndex);
		body.setTransform(position, 0);
		return body;
	}
	
	public static Body createEdge(World world, BodyType type, float x1, float y1, 
			float x2, float y2, float density) {
		synchronized(world){
			BodyDef def = new BodyDef();
			def.type = type;
			Body box = world.createBody(def);
			EdgeShape boxShape = new EdgeShape();		
			boxShape.set(new Vector2(0, 0), new Vector2(x2 - x1, y2 - y1));
			/*Fixture fixture = */box.createFixture(boxShape, density);
			box.setTransform(x1, y1, 0);
			boxShape.dispose();
			return box;
		}
	}
	
	public static void setFilterData(Fixture fixture, short maskBits, short categoryBits, short groupIndex){
		Filter filter = fixture.getFilterData();
		filter.categoryBits = categoryBits;
		filter.maskBits = maskBits;
		filter.groupIndex = groupIndex;
		fixture.setFilterData(filter);
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
		Array<Body> bodyArray = new Array<>(world.getBodyCount());
		world.getBodies(bodyArray);
		for(Body worldBody : bodyArray){
			float distance = worldBody.getPosition().dst2(x,y);
			if(body == null || closestDistance > distance){
				body = worldBody;
				closestDistance = distance;
			}
		}
		return body;
	}
}
