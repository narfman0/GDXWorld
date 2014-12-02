package com.blastedstudios.gdxworld.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.blastedstudios.gdxworld.world.shape.GDXPolygon;

public class MaskTextureStruct {
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
	static final ShaderProgram SHADER = new ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
	public final Mesh mesh;
	public final Texture texture;
	public final GDXPolygon polygon;

	public MaskTextureStruct(Mesh mesh, Texture texture, GDXPolygon polygon){
		this.mesh = mesh;
		this.texture = texture;
		this.polygon = polygon;
	}
	
	public void render(Camera camera){
		// is better to batch this shader call, so SHADER is not private
		SHADER.begin();
		SHADER.setUniformMatrix("u_worldView", camera.combined);
		SHADER.setUniformi("u_texture", 0);
		texture.bind();
		mesh.render(SHADER, GL20.GL_TRIANGLES);
		SHADER.end();
	}
}
