package com.example.opengltest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
// ��ü�����̴ϱ�
// ���� �ĸ� �� �� ���� ���� �������ִ°� �غ��� 

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Texture_Cube {
	float vert[] = { -0.5f, 0.5f, -0.5f, // 0
			0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f,

			-0.5f, 0.5f, 0.5f, // 4
			0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f,

			-0.5f, 0.5f, 0.5f, // 8
			0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f,

			-0.5f, -0.5f, 0.5f, // 12
			0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f,

			0.5f, 0.5f, -0.5f, // 16
			0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f,

			-0.5f, 0.5f, -0.5f, // 20
			-0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, };
	byte index[] = { 0, 3, 1, 1, 3, 2, 4, 7, 6, 4, 6, 5, 8, 11, 10, 8, 10, 9,
			12, 14, 15, 12, 13, 14, 16, 18, 19, 16, 18, 17, 20, 23, 22, 20, 21,
			23, };

	float text[] = { 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1,
			1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0,
			1, 1, 1, 1, 0, 0, 0, };

	FloatBuffer vertbuf;
	ByteBuffer indexbuf;
	FloatBuffer normalBuffer;
	//
	FloatBuffer textbuf;
	int[] textureName;

	FloatBuffer light_a;
	FloatBuffer light_d;
	FloatBuffer light_p;
	FloatBuffer light_di;

	// Ambient - ȯ�汤������ ���� ������ -1.0 ~1.0 ���� ũ�� �Ҽ��� ��������� ��ü�� ������
	// Diffuse - ���� ���۵Ǵ� �������� ������ �Ÿ����� ���� ���� ������ -1.0 ~1.0 ���� ũ�� �Ҽ��� �������� �հ�����
	// ���� ����
	// Shiniess - ������ 0~180 ���� ũ���Ҽ��� ������ ȿ���� ��
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

	public Texture_Cube() {
		vertbuf = ArrayToBuffer(vert);
		light_a = ArrayToBuffer(lightAmbient);
		light_d = ArrayToBuffer(lightDiffuse);
		light_p = ArrayToBuffer(lightPosition);
		light_di = ArrayToBuffer(lightDirection);

		indexbuf = ByteBuffer.allocateDirect(index.length);
		indexbuf.put(index);
		indexbuf.position(0);

		textbuf = ArrayToBuffer(text);

		textureName = new int[1];

	}

	public void draw(GL10 gl) {

		gl.glColor4f(1, 1, 1, 1);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertbuf);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textbuf);

		gl.glDrawElements(GL10.GL_TRIANGLES, index.length,
				GL10.GL_UNSIGNED_BYTE, indexbuf);
		/*
		 * gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, light_a);
		 * gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, light_d);*/
		 gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light_p);
		 gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, light_di);
		 

		gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, 180);
		gl.glMaterialf(GL10.GL_BACK, GL10.GL_SHININESS, 180);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 180);

	}

	/* Initialize Texture */
	public void InitTexture(GL10 gl, Context context) {

		Bitmap imgPanda = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.dog);

		gl.glGenTextures(1, textureName, 0); // �ؽ��� ������ ����
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureName[0]); // �ؽ��� ��� ����

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, imgPanda, 0);

		imgPanda.recycle();
		gl.glEnable(GL10.GL_TEXTURE_2D);

	}
}
