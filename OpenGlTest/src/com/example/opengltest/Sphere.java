package com.example.opengltest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class Sphere {

	private float[] lightAmbient = { 0.2f, 0.0f, 0.0f, 1.0f };
	private float[] lightPosition = { 2.0f, 2.0f, 2.0f, 2.0f };
	private float[] lightDirection = {-2.0f, -2.0f, -3.0f};
	private float[] lightSpecular = { 1.0f, 1.0f, 1.0f, 1.0f };

	FloatBuffer light_a;
	FloatBuffer light_p;
	FloatBuffer light_di;
	FloatBuffer light_sp;

	Sphere() {
		light_a = ArrayToBuffer(lightAmbient);
		light_p = ArrayToBuffer(lightPosition);
		light_di = ArrayToBuffer(lightDirection);
		light_sp = ArrayToBuffer(lightSpecular);
	}

	public FloatBuffer ArrayToBuffer(float[] ar) {
		ByteBuffer bytebuf = ByteBuffer.allocateDirect(ar.length * 4);
		bytebuf.order(ByteOrder.nativeOrder());
		FloatBuffer buf = bytebuf.asFloatBuffer();
		buf.put(ar);
		buf.position(0);
		return buf;
	}

	public void draw(GL10 gl, float R, float G, float B) {
		float theta, pai;
		float co, si;
		float r1, r2;
		float h1, h2;
		float step = 5.0f;
		float[][] v = new float[32][3];
		ByteBuffer vbb;
		FloatBuffer vBuf;

		vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vBuf = vbb.asFloatBuffer();

		gl.glDisable(GL10.GL_TEXTURE_2D);

		gl.glColor4f(R, G, B, 1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

		for (pai = -90.0f; pai < 90.0f; pai += step) {
			int n = 0;

			r1 = (float) Math.cos(pai * Math.PI / 180.0);
			r2 = (float) Math.cos((pai + step) * Math.PI / 180.0);
			h1 = (float) Math.sin(pai * Math.PI / 180.0);
			h2 = (float) Math.sin((pai + step) * Math.PI / 180.0);

			for (theta = 0.0f; theta <= 360.0f; theta += step) {
				co = (float) Math.cos(theta * Math.PI / 180.0);
				si = -(float) Math.sin(theta * Math.PI / 180.0);

				v[n][0] = (r2 * co);
				v[n][1] = (h2);
				v[n][2] = (r2 * si);
				v[n + 1][0] = (r1 * co);
				v[n + 1][1] = (h1);
				v[n + 1][2] = (r1 * si);

				vBuf.put(v[n]);
				vBuf.put(v[n + 1]);

				n += 2;

				if (n > 31) {
					vBuf.position(0);

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
					gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);

					n = 0;
					theta -= step;
				}

			}
			vBuf.position(0);

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
		}
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, light_a);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light_p);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, light_di);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, light_sp);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

	}

}