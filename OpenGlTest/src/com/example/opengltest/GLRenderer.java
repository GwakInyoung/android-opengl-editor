package com.example.opengltest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

public class GLRenderer implements Renderer {

	Triangle triangle;
	Square square;
	// 입체 도형
	Cube cube;
	Pyramid pyramid;
	Texture_Cube texture_cube;
	Sphere sphere;
	Circle circle;
	
	

	float R_set, G_set, B_set;
	float xAngle, yAngle;
	public String select = "0";

	public String option = "0";
	float x, y, z;
	Context context;

	public GLRenderer(Context context) {

		this.context = context;

		cube = new Cube();
		triangle = new Triangle();
		square = new Square();
		pyramid = new Pyramid();
		texture_cube = new Texture_Cube();
		sphere = new Sphere();
		circle = new Circle();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// texture
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// 깊이 3차원 도형일 때
		/*
		 * if (select.equals("0")) { gl.glShadeModel(GL10.GL_FLAT); // flat
		 * shading } else if (select.equals("1"))
		 * gl.glShadeModel(GL10.GL_SMOOTH); // 구로도 shading //
		 * gl.glShadeModel(mode); // 퐁 쉐이딩??
		 */
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glEnable(GL10.GL_LIGHTING);
		// 본 물체의 색도 살리면서 빛에 색감이 죽이지 않도록 유지하는 함수
		gl.glEnable(GL10.GL_COLOR_MATERIAL);

		// depth 설정
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);

	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);

	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glRotatef(xAngle, 1, 0, 0);
		gl.glRotatef(yAngle, 0, 1, 0);
		// gl.glTranslatef(0.0f, 0.0f, 0.0f);
		gl.glScalef(1.0f + x, 1.0f + y, 1.0f + z);

		if (select.equals("0")) {
			gl.glShadeModel(GL10.GL_FLAT); // flat shading
		} else if (select.equals("1"))
			gl.glShadeModel(GL10.GL_SMOOTH); // 구로도 shading
		// gl.glShadeModel(mode); // 퐁 쉐이딩??

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		if (option.equals("1")) // 하나만 설정해줘도 되는뎅? triangle 하나만
			triangle.draw(gl, R_set, G_set, B_set);
		else if (option.equals("2"))
			square.draw(gl, R_set, G_set, B_set);
		else if (option.equals("3")) {
			cube.draw(gl, R_set, G_set, B_set);
		} else if (option.equals("4")) {
			pyramid.draw(gl, R_set, G_set, B_set);
		} else if (option.equals("5")) {
			texture_cube.InitTexture(gl, context);
			texture_cube.draw(gl);
		} else if (option.equals("6"))
			circle.draw(gl, R_set, G_set, B_set);
		else if (option.equals("7"))
			sphere.draw(gl, R_set, G_set, B_set);
		gl.glLoadIdentity();

	}

	public void setScale(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setColor(String color) {
		String R = color.substring(2, 4);
		String G = color.substring(4, 6);
		String B = color.substring(6, 8);

		long R_l = Long.parseLong(R, 16);
		long G_l = Long.parseLong(G, 16);
		long B_l = Long.parseLong(B, 16);

		float R_f = (float) R_l;
		float G_f = (float) G_l;
		float B_f = (float) B_l;

		this.R_set = R_f / 255;
		this.G_set = G_f / 255;
		this.B_set = B_f / 255;

	}

}
