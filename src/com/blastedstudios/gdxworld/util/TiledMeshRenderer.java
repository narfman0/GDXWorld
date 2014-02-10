package com.blastedstudios.gdxworld.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

public class TiledMeshRenderer {
	private final LinkedList<MaskTextureStruct> maskTextures = new LinkedList<>();
	private final static float UV_SCALE = Properties.getFloat("tiled.uv.scale", 10f);
	private final static String FRAGMENT_SHADER = 
		"#ifdef GL_ES \n" + 
		"precision mediump float; \n" + 
		"#endif \n" + 
		"varying vec4 v_color; \n" + 
		"varying vec2 v_texCoords; \n" + 
		"uniform sampler2D u_texture; \n" + 
		"void main(){ \n" + 
		"  gl_FragColor = v_color * texture2D(u_texture, v_texCoords); \n" + 
		"}";
	private final static String VERTEX_SHADER = 
		"attribute vec4 a_position; \n" + 
		"attribute vec4 a_color; \n" + 
		"attribute vec2 a_texCoord0; \n" + 
		"uniform mat4 u_worldView; \n" + 
		"varying vec4 v_color; \n" + 
		"varying vec2 v_texCoords; \n" + 
		"void main() { \n" + 
		"  v_color = vec4(1, 1, 1, 1); \n" + 
		"  v_texCoords = a_texCoord0; \n" + 
		"  gl_Position = u_worldView * a_position; \n" + 
		"}";
	private final ShaderProgram shader;

	public TiledMeshRenderer(GDXRenderer gdxRenderer, Collection<GDXPolygon> polygons){
		shader = new ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
		for(GDXPolygon polygon : polygons)
			if(polygon.isRepeatable() && polygon.getResource() != null && !polygon.getResource().equals("")){
				EarClippingTriangulator triangulator = new EarClippingTriangulator();
				List<Vector2> verticesAbsolute = triangulator.computeTriangles(polygon.getVerticesAbsolute());
				Mesh mesh = new Mesh(true, verticesAbsolute.size(), 0, VertexAttribute.Position(), VertexAttribute.TexCoords(0));
				Texture texture = gdxRenderer.getTexture(polygon.getResource());
				mesh.setVertices(convert(verticesAbsolute, polygon.getCenter()));
				maskTextures.add(new MaskTextureStruct(mesh, texture));
			}
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
		shader.begin();
		shader.setUniformMatrix("u_worldView", camera.combined);
		shader.setUniformi("u_texture", 0);
		for(MaskTextureStruct struct : maskTextures){
			struct.texture.bind();
			struct.mesh.render(shader, GL10.GL_TRIANGLES);
		}
		shader.end();
	}

	private class MaskTextureStruct{
		public final Mesh mesh;
		public final Texture texture;

		public MaskTextureStruct(Mesh mesh, Texture texture){
			this.mesh = mesh;
			this.texture = texture;
		}
	}
}
