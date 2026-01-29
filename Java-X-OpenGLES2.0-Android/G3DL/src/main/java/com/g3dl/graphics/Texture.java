/*
 * Copyright 2026 The G3DL Project Developers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.g3dl.graphics;

import android.graphics.*;
import android.opengl.*;
import com.g3dl.*;

public class Texture{
	private Bitmap bmp=null;
	private int[]textures=new int[1];

	public Texture(){}
	public Texture(String path,boolean external){
		bmp=loadBitmap(path,external);
		loadId();
	}

	public Texture(Bitmap bitmap){
		bmp=bitmap;
		loadId();
	}

	public void replace(String path,boolean external){
		Bitmap nbmp=loadBitmap(path,external);
		if(nbmp!=null)replace(nbmp);
	}

	public void replace(Bitmap bitmap){
		if(bmp!=null)bmp.recycle();
		bmp=bitmap;
		if(textures[0]==0)loadId();else initToId();
	}

	private void loadId(){
		GLES20.glGenTextures(1,textures,0);
		initToId();
	}
	private void initToId(){
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textures[0]);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
	}
	public static Bitmap loadBitmap(String path,boolean external){
		Bitmap loadedBmp=null;
		try{loadedBmp=external?BitmapFactory.decodeFile(path):BitmapFactory.decodeStream(G3D.context.getAssets().open(path));}catch(java.io.IOException e){e.printStackTrace();}
		return loadedBmp;
	}

	public void setWidth(int width){
		bmp=Bitmap.createScaledBitmap(bmp,width,bmp.getHeight(),false);
		if(textures[0]==0)loadId();else initToId();
	}
	public void setHeight(int height){
		bmp=Bitmap.createScaledBitmap(bmp,bmp.getWidth(),height,false);
		if(textures[0]==0)loadId();else initToId();
	}

	public int getWidth(){return bmp.getWidth();}
	public int getHeight(){return bmp.getHeight();}
	public int getGlId(){return textures[0];}
	public Bitmap getBitmap(){return bmp;}

	public static class TextureParameter{
		android.widget.LinearLayout n;
	}
}
