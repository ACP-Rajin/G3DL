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

package com.g3dl;

import android.app.*;
import android.content.*;
import android.opengl.*;
import android.os.*;
import android.view.*;
import com.g3dl.file.*;
import java.io.*;
import javax.microedition.khronos.opengles.*;
import com.g3dl.graphics.*;

public class G3DActivity extends GLSurfaceView{

	private Activity a;
	private GRenderer renderer;
	private GameInterface gi;
	private ContextWrapper contextWrapper;

	public G3DActivity(Activity activity){
		super(activity);
		a=activity;
		contextWrapper=a;
		setEGLContextClientVersion(2);
		renderer=new GRenderer();
		setRenderer(renderer);

		initFilePaths();
	}

	public void init(GameInterface gameInterface){
		G3D.context=a;
		G3D.input=new Input();

		gi=gameInterface;
		renderer.init(gi);
		a.setContentView(this);

		G3D.renderer=renderer;
		G3D.glScreen=renderer.s;
		G3D.deviceScreen=new Screen(G3D.context.getResources().getDisplayMetrics().widthPixels,G3D.context.getResources().getDisplayMetrics().heightPixels);
	}

	private void initFilePaths(){
		String localPath=contextWrapper.getFilesDir().getAbsolutePath();
		GFile.localPath=localPath.endsWith("/")?localPath:localPath+"/";

		File externalFilesDir=Environment.getExternalStorageDirectory();
		if(externalFilesDir!=null){
			String externalFilesPath=externalFilesDir.getAbsolutePath();
			GFile.externalFilesPath=externalFilesPath.endsWith("/")?externalFilesPath:externalFilesPath+"/";
		}else GFile.externalFilesPath=null;
	}

	public void handleTouchEvent(MotionEvent event){
		G3D.input.processTouch(event);
		G3D.event=event;
	}

	public void useImmersiveMode(){
		a.requestWindowFeature(Window.FEATURE_NO_TITLE);
		a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		a.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	}

	public void onDespose(){
		gi.onDespose();
	}

	public static class GRenderer implements GLSurfaceView.Renderer{
		private GameInterface gi;
		private long startTime=System.nanoTime(),lastFrameTime,frameCount,fpsTimeAccumulator;
		private float deltaTime,fps;
		private Screen s=new Screen();

		public void init(GameInterface gameInterface){
			this.gi=gameInterface;
			lastFrameTime=System.nanoTime();
		}

		@Override
		public void onSurfaceCreated(GL10 gl,javax.microedition.khronos.egl.EGLConfig config){
			gi.create();
		}

		@Override
		public void onSurfaceChanged(GL10 gl,int width,int height){
			s.setWidth(width);
			s.setHeight(height);
			gi.resize(s);
		}

		@Override
		public void onDrawFrame(GL10 gl){
			long currentFrameTime=System.nanoTime();
			deltaTime=(currentFrameTime-lastFrameTime)*1E-9f;

			gi.render();

			frameCount++;
			fpsTimeAccumulator+=currentFrameTime-lastFrameTime;
			if(fpsTimeAccumulator>=1_000_000_000L){
				fps=frameCount;
				frameCount=0;
				fpsTimeAccumulator=0;
			}
			lastFrameTime=currentFrameTime;
		}

		public float getTime(){return(float)(startTime-System.nanoTime())*1E-9f;}

		public float getDeltaTime(){return deltaTime;}

		public float getFPS(){return fps;}
	}
}
