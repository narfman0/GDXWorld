package com.blastedstudios.gdxworld.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.math.EarClippingTriangulator;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

public class TiledMeshRenderer {
	private final LinkedList<MaskTextureStruct> maskTextures = new LinkedList<>();
	private final static float UV_SCALE = Properties.getFloat("tiled.uv.scale", 10f);
	private final static ShapeRenderer SHAPE_RENDERER = new ShapeRenderer();

	public TiledMeshRenderer(GDXRenderer gdxRenderer, Collection<GDXPolygon> polygons){
		for(GDXPolygon polygon : polygons)
			if(polygon.isRepeatable() && polygon.getResource() != null && !polygon.getResource().isEmpty())
				maskTextures.add(buildMesh(gdxRenderer, polygon));
	}
	
	public static MaskTextureStruct buildMesh(GDXRenderer gdxRenderer, GDXPolygon polygon){
		EarClippingTriangulator triangulator = new EarClippingTriangulator();
		List<Vector2> verticesAbsolute = triangulator.computeTriangles(polygon.getVerticesAbsolute());
		Mesh mesh = new Mesh(true, verticesAbsolute.size(), 0, VertexAttribute.Position(), VertexAttribute.TexCoords(0));
		Texture texture = gdxRenderer.getTexture(polygon.getResource());
		mesh.setVertices(convert(verticesAbsolute, polygon.getCenter()));
		return new MaskTextureStruct(mesh, texture, polygon);
	}

	private static float[] convert(final List<Vector2> vertices, final Vector2 center){
		float[] output = new float[vertices.size()*5];
		for(int vertexIndex = 0, i=0; vertexIndex < vertices.size(); vertexIndex++){
			output[i++] = vertices.get(vertexIndex).x;
			output[i++] = vertices.get(vertexIndex).y;
			output[i++] = 0;
			output[i++] = (vertices.get(vertexIndex).x - center.x)/UV_SCALE;
			output[i++] = (vertices.get(vertexIndex).y - center.y)/UV_SCALE;
		}
		return output;
	}

	public void render(Camera camera){
		MaskTextureStruct.SHADER.begin();
		MaskTextureStruct.SHADER.setUniformMatrix("u_worldView", camera.combined);
		MaskTextureStruct.SHADER.setUniformi("u_texture", 0);
		for(MaskTextureStruct struct : maskTextures){
			struct.texture.bind();
			struct.mesh.render(MaskTextureStruct.SHADER, GL20.GL_TRIANGLES);
		}
		MaskTextureStruct.SHADER.end();
		if(Properties.getBool("tiled.renderer.drawoutline", true))
			renderOutlines(camera);
	}
	
	public void renderOutlines(Camera camera){
		SHAPE_RENDERER.setColor(Color.BLACK);
		SHAPE_RENDERER.setProjectionMatrix(camera.combined);
		SHAPE_RENDERER.begin(ShapeType.Line);
		for(MaskTextureStruct struct : maskTextures){
			List<Vector2> verts = struct.polygon.getVerticesAbsolute();
			for(int i=1; i<verts.size(); i++)
				SHAPE_RENDERER.line(verts.get(i-1), verts.get(i));
		}
		SHAPE_RENDERER.end();
	}
}
