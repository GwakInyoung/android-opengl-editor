package com.example.opengltest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class MainActivity extends Activity {

	GLSurfaceView surfaceView;

	String strColor = "#FFFFFF";

	int init_color = Color.parseColor(strColor);
	boolean rotate = false;

	final static float DRAGSPEED = 5;
	float oldx, oldy;
	float x = 0;
	float y = 0;
	float z = 0;

	Cube cube;

	TextView selection;
	String[] ar = { "triangle", "square", "pyramid", "cube", "textured cube",
			"circle", "sphere" };

	GLRenderer renderer = new GLRenderer(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		surfaceView = new GLSurfaceView(this);
		surfaceView.setRenderer(renderer);
		surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

		setContentView(surfaceView);

		// Spinner
		LayoutInflater in = getLayoutInflater();

		// < add layout >
		// shape_layout
		View layout_shape = (View) in.inflate(R.layout.shape, null);
		addContentView(layout_shape, new LinearLayout.LayoutParams(200, 480));

		ArrayAdapter<String> a = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ar);

		a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner sp = (Spinner) findViewById(R.id.spinner);
		sp.setPrompt("shape");
		sp.setAdapter(a);

		sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), ar[position],
						Toast.LENGTH_SHORT).show();
				int color = Color.parseColor(strColor);
				((TextView) parent.getChildAt(0)).setTextColor(color);
				if (ar[position].equals("square")) {
					renderer.option = "2";
					surfaceView.requestRender();
				} else if (ar[position].equals("triangle")) {
					renderer.option = "1";
					surfaceView.requestRender();
				} else if (ar[position].equals("pyramid")) {
					renderer.option = "4";
					surfaceView.requestRender();
				} else if (ar[position].equals("cube")) {
					renderer.option = "3";
					surfaceView.requestRender();
				} else if (ar[position].equals("textured cube")) {
					renderer.option = "5";
					surfaceView.requestRender();
				} else if (ar[position].equals("circle")) {
					renderer.option = "6";
					surfaceView.requestRender();
				} else if (ar[position].equals("sphere")) {
					renderer.option = "7";
					surfaceView.requestRender();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// shading_layout
		View layout_shading = (View) in.inflate(R.layout.shading_layout, null);
		addContentView(layout_shading, new LinearLayout.LayoutParams(2000, 500));
		Button f_shading = (Button) findViewById(R.id.f_shading);
		Button g_shading = (Button) findViewById(R.id.g_shading);

		f_shading.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				renderer.select = "0";
				surfaceView.requestRender();
			}
		});

		g_shading.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				renderer.select = "1";
				surfaceView.requestRender();
			}
		});

		// scaling_layout
		addContentView(
				LayoutInflater.from(this)
						.inflate(R.layout.scaling_layout, null),
				new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
		Button plus_btn = (Button) findViewById(R.id.scaling_plus);
		Button minus_btn = (Button) findViewById(R.id.scaling_minus);

		plus_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				x += 0.1;
				y += 0.1;
				z += 0.1;
				renderer.setScale(x, y, z);
				surfaceView.requestRender();
			}
		});

		minus_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				x -= 0.1;
				y -= 0.1;
				z -= 0.1;
				renderer.setScale(x, y, z);
				surfaceView.requestRender();
			}
		});
		// button_layout
		View layout_color = (View) in.inflate(R.layout.button_layout, null);
		addContentView(layout_color, new LinearLayout.LayoutParams(800, 600));
		Button color_btn = (Button) findViewById(R.id.color);

		color_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				color_Pick();
			}
		});

	}

	public void color_Pick() {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, init_color,
				new OnAmbilWarnaListener() {

					@Override
					public void onOk(AmbilWarnaDialog dialog, int color) {
						String color2 = Integer.toHexString(color);
						renderer.setColor(color2);
						init_color = color;
						surfaceView.requestRender();
					}

					@Override
					public void onCancel(AmbilWarnaDialog dialog) {
						// cancel was selected by the user
					}
				});

		dialog.show();

	}

	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float dx = (oldx - x) / DRAGSPEED; // 속도 줄이기
			float dy = (oldy - y) / DRAGSPEED;

			renderer.yAngle += dx;
			renderer.xAngle += dy;

			surfaceView.requestRender();
		}

		oldx = x;
		oldy = y;

		return true;
	}

	protected void onResume() {
		super.onResume();
		surfaceView.onResume();
	}

	protected void onPause() {
		super.onPause();
		surfaceView.onPause();
	}
}
