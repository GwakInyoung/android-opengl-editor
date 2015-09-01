package com.example.opengltest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Pyramid {

	float vert[] = { 0, 0, -0.8f, // Áß¾Ó
			0.5f, 0.5f, 0, // ¿ì»ó
			-0.5f, 0.5f, 0, // ÁÂ»ó
			-0.5f, -0.5f, 0, // ÁÂÇÏ
			0.5f, -0.5f, 0, // ¿ìÇÏ
			0.5f, 0.5f, 0, // ¹Ø¸é ¿ì»ó
	};

	byte index[] = { 3, 2, 5, // ¹Ø¸é1
			4, 3, 5, // ¹Ø¸é2
			0, 1, 2, // 12½Ã
			0, 2, 3, // 9½Ã
			0, 3, 4, // 6½Ã
			0, 4, 1, // 3½Ã
	};

	FloatBuffer vertbuf;
	ByteBuffer indexbuf;

	FloatBuffer light_a;
	FloatBuffer light_d;
	FloatBuffer light_p;
	FloatBuffer light_di;

	private float[] lightAmbient = { 0.1f, 0.3f, 0.3f, 0.1f };
	private float[] lightDiffuse = { 0.7f, 0.7f, 0.7f, 1.0f };
	private float[] lightPosition = { 2.0f, 2.0f, 2.0f, 2.0f };
	private float[] lightDirection = { 1.0f, 1.0f, 1.0f };

	public FloatBuffer ArrayToBuffer(float[] ar) {
		ByteBuffer bytebuf = ByteBuffer.allocateDirect(ar.length * 4);
		bytebuf.order(ByteOrder.nativeOrder());
		FloatBuffer buf = bytebuf.asFloatBuffer();
		buf.put(ar);
		buf.position(0);
		return buf;
	}

	public Pyramid() {

		vertbuf = ArrayToBuffer(vert);

		indexbuf = ByteBuffer.allocateDirect(index.length);
		indexbuf.put(index);
		indexbuf.position(0);

		light_a = ArrayToBuffer(lightAmbient);
		light_d = ArrayToBuffer(lightDiffuse);
		light_p = ArrayToBuffer(lightPosition);
		light_di = ArrayToBuffer(lightDirection);
	}

	public void draw(GL10 gl, float R, float G, float B) {

		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glColor4f(R, G, B, 1);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertbuf);
		gl.glDrawElements(GL10.GL_TRIANGLES, index.length,
				GL10.GL_UNSIGNED_BYTE, indexbuf);

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, light_a);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, light_d);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light_p);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, light_di);

		gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 180);
		gl.glMaterialf(GL10.GL_BACK, GL10.GL_SHININESS, 180);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 180);

	}

}
