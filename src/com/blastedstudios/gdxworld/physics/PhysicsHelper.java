package com.blastedstudios.gdxworld.physics;

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
	
	public static Body createCircle(World world, float radius, Vector2 position){
		Body body = world.createBody(new BodyDef());
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		body.createFixture(shape, 1);
		body.setTransform(position, 0);
		return body;
	}
	
	public static Body createFixture(World world, FixtureDef fixtureDef, BodyType type,
			List<Vector2> nodes, Shape shape) {
		Vector2[][] verts = Clipper.polygonize(Polygonizer.BAYAZIT, nodes.toArray(new Vector2[nodes.size()]));
		if(verts == null){
			Gdx.app.log("GDXPolygon.createFixture", "Can't create fixture(s), verts null");
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
}
