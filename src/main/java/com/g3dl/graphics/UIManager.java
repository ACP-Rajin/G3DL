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

import android.opengl.*;
import android.view.*;
import com.g3dl.graphics.g2d.*;
import java.util.*;
import com.g3dl.graphics.g3d.*;
import com.g3dl.*;

public class UIManager{
	private ArrayList<UI>uiList;
	private OrthographicCamera camera=new OrthographicCamera();

	public UIManager(){
		uiList=new ArrayList<>();
	}

	public void resize(Screen screen){
		for(UI ui:uiList)ui.resize(screen);
		camera.resize(screen);
	}

	public void render(){
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA,GLES20.GL_ONE_MINUS_SRC_ALPHA);

		camera.setSize(5);
		camera.setZ(-10);
		camera.update();

		// for(UI ui:uiList)ui.render(camera);

		GLES20.glDisable(GLES20.GL_BLEND);
	}

	public void onTouchEvent(MotionEvent e){for(UI ui:uiList)ui.event(e);}

	public void addUI(UI ui){uiList.add(ui);}

	public void removeUI(UI ui){uiList.remove(ui);}

	public UI getUI(int id){return uiList.get(id);}
}
