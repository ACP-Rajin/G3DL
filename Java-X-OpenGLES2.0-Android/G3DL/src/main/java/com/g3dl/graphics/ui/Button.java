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

package com.g3dl.graphics.ui;

import android.view.*;
import com.g3dl.graphics.*;
import com.g3dl.graphics.g2d.*;

public class Button extends UI{
  private Rectangle r;
  private boolean isPressed=false;
  private long pressStartTime=0;
  private float touchX,touchY;

  public Button(){
    r=new Rectangle();
  }

  public Button(int width,int height){
    r=new Rectangle(width, height);
  }

  @Override
  public void resize(Screen screen){
    super.resize(screen);
    r.setScreen(screen);
  }

  @Override
  public void render(Camera cam){
    r.draw(cam);
  }

  @Override
  public boolean isClicked(){
    return !isPressed; // Returns true when released after being pressed inside bounds
  }

  @Override
  public boolean isLongPreesed(){
    return isPressed && (System.currentTimeMillis() - pressStartTime > 500); // Long press if held for 500ms+
  }

  @Override
  public boolean isDragged(){
    return isPressed && (Math.abs(touchX - r.getWidth() / 2) > 10 || Math.abs(touchY - r.getHeight() / 2) > 10);
  }

  @Override
  public void event(MotionEvent e){
    int action=e.getAction();
    float x=e.getX();
    float y=e.getY();

    switch(action){
      case MotionEvent.ACTION_DOWN:
        if(isInside(x,y)){
          isPressed=true;
          pressStartTime=System.currentTimeMillis();
          touchX=x;
          touchY=y;
        }
        break;

      case MotionEvent.ACTION_UP:
        if(isPressed&&isInside(x,y)){
          isPressed=false;
        }
        break;

      case MotionEvent.ACTION_MOVE:
        // Handle dragging
        break;
    }
  }

  private boolean isInside(float x,float y){
    float halfWidth=r.getWidth() / 2;
    float halfHeight=r.getHeight() / 2;
    return x >= -halfWidth && x <= halfWidth && y >= -halfHeight && y <= halfHeight;
  }
}
