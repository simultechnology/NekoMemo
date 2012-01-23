package neko.memo.renderer;

import neko.memo.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import neko.memo.global.Global;
import neko.memo.util.GraphicUtils;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class MyRenderer implements Renderer {
	
	private Context mContext;
	
	private int mWidth;
	private int mHeight;
	
	// テクスチャを管理するためのID
//	private int mTextureNekomemo;
	// クロネコの画像ID
	private int mTextureKuroneko;
	
	public MyRenderer(Context context) {
		this.mContext = context;
	}
	
	private float mTransY = 1.f;
	
	public void renderMain(GL10 gl) {
		
		// 土台の描写
		GraphicUtils.drawRectangle(gl, 0.f, -1.4f, 2.f, 0.2f, 0.4f, 0.6f, 0.4f, 1.0f);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//GraphicUtils.drawTexture(gl, 0.f, 0.75f, 1.7f, 1.3f, mTextureNekomemo, 0.f, 0.f, 1f, 1.f, 1.0f, 1.0f, 1.0f, 0.8f);
//		GraphicUtils.drawTexture(gl, -0.6f, 1.18f, 0.5f, 0.5f, mTextureNekomemo, 0.f, 0.f, 0.31f, 0.3f, 1.0f, 1.0f, 1.0f, 1.0f);
//		GraphicUtils.drawTexture(gl, -0.05f, 1.18f, 0.5f, 0.5f, mTextureNekomemo, 0.31f, 0.f, 0.295f, 0.3f, 1.0f, 1.0f, 1.0f, 1.0f);
//		GraphicUtils.drawTexture(gl, 0.48f, 1.18f, 0.5f, 0.5f, mTextureNekomemo, 0.605f, 0.f, 0.28f, 0.3f, 1.0f, 1.0f, 1.0f, 1.0f);
//		GraphicUtils.drawTexture(gl, 0.8f, 1.1f, 0.15f, 0.3f, mTextureNekomemo, 0.885f, 0.f, 0.115f, 0.3f, 1.0f, 1.0f, 1.0f, 1.0f);

		//GraphicUtils.drawTexture(gl, 0.8f, 1.1f, 0.3f, 0.3f, mTextureNekomemo, 0.885f, 0.f, 0.9f, 0.3f, 1.0f, 1.0f, 1.0f, 1.0f);
//		GraphicUtils.drawTexture(gl, 0.1f, 1.1f, 0.5f, 0.5f, mTextureNekomemo, 0.35f, 0.f, 0.34f, 0.4f, 1.0f, 1.0f, 1.0f, 0.8f);
//		GraphicUtils.drawTexture(gl, 0.7f, 1.1f, 0.5f, 0.5f, mTextureNekomemo, 0.7f, 0.f, 0.25f, 0.4f, 1.0f, 1.0f, 1.0f, 0.8f);
		gl.glTranslatef((float) Math.sin(mTransY / 2.f), 0.f, 0.f);
		mTransY += 0.050;
		GraphicUtils.drawTexture(gl, 0.0f, -1.f, 0.6f, 0.6f, mTextureKuroneko, 0.f, 0.f, 1.f, 1.f, 1.0f, 1.0f, 1.0f, 1.f);
		gl.glDisable(GL10.GL_BLEND);
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		gl.glViewport(0, 0, mWidth, mHeight);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		
		gl.glLoadIdentity();
		gl.glOrthof(-1.f, 1.f, -1.5f, 1.5f, 0.5f, -0.5f);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		renderMain(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.mWidth = width;
		this.mHeight = height;
		
		Global.gl = gl;
		
//		// テクスチャの生成を行う
//		this.mTextureNekomemo = GraphicUtils.loadTexture(gl, mContext.getResources(), R.drawable.nekomemo);
//		if (this.mTextureNekomemo ==  0) {
//			Log.e(getClass().toString(), "texture load Error!");
//		}
		
		// テクスチャの生成を行う
		this.mTextureKuroneko = GraphicUtils.loadTexture(gl, mContext.getResources(), R.drawable.kuroneko2);
		if (this.mTextureKuroneko ==  0) {
			Log.e(getClass().toString(), "texture load Error!");
		}
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
