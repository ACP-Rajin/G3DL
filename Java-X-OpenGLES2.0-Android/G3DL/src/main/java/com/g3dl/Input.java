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

import android.view.MotionEvent;
import android.os.SystemClock;
import com.g3dl.math.*;

public class Input{
	private static final int MAX_TOUCHES=10;
	private static final long LONG_PRESS_TIME=500; // 500ms for long press
	private static final float DRAG_THRESHOLD=10;  // Min movement for drag
	private static final long DOUBLE_TAP_TIME=300; // 300ms max for double tap

	private boolean[]isTouched=new boolean[MAX_TOUCHES];
	private float[]touchX=new float[MAX_TOUCHES];
	private float[]touchY=new float[MAX_TOUCHES];
	private float[]prevX=new float[MAX_TOUCHES];
	private float[]prevY=new float[MAX_TOUCHES];
	private float[]deltaX=new float[MAX_TOUCHES];
	private float[]deltaY=new float[MAX_TOUCHES];
	private long[]touchStartTime=new long[MAX_TOUCHES];
	private long[]lastTapTime=new long[MAX_TOUCHES];

	public void processTouch(MotionEvent event){
		int action=event.getActionMasked();
		int pointerIndex=event.getActionIndex();
		int pointerId=event.getPointerId(pointerIndex);

		if(pointerId>=MAX_TOUCHES)return;

		switch(action){
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				isTouched[pointerId]=true;
				touchX[pointerId]=event.getX(pointerIndex);
				touchY[pointerId]=event.getY(pointerIndex);
				prevX[pointerId]=touchX[pointerId];
				prevY[pointerId]=touchY[pointerId];
				touchStartTime[pointerId]=SystemClock.uptimeMillis();
				deltaX[pointerId]=0;
				deltaY[pointerId]=0;
				break;
			case MotionEvent.ACTION_MOVE:
				for(int i=0;i<event.getPointerCount();i++){
					int id=event.getPointerId(i);
					if(id<MAX_TOUCHES){
						float newX=event.getX(i);
						float newY=event.getY(i);
						deltaX[id]=newX-touchX[id];
						deltaY[id]=newY-touchY[id];
						prevX[id]=touchX[id];
						prevY[id]=touchY[id];
						touchX[id]=newX;
						touchY[id]=newY;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL:
				isTouched[pointerId]=false;
				touchStartTime[pointerId]=0;
				touchX[pointerId]=-1;
				touchY[pointerId]=-1;
				deltaX[pointerId]=0;
				deltaY[pointerId]=0;
				break;
		}
	}
	public boolean isTouched(){return isTouched[0];}
	public boolean isTouched(int pointer){return pointer<MAX_TOUCHES&&isTouched[pointer];}

	public boolean isTapped(){return isTouched(0);}
	public boolean isTapped(int pointer){return!isTouched[pointer]&&(SystemClock.uptimeMillis()-touchStartTime[pointer])<LONG_PRESS_TIME&&!isDragged(pointer);}

	public boolean isLongPressed(){return isLongPressed(0);}
	public boolean isLongPressed(int pointer){return isTouched[pointer]&&(SystemClock.uptimeMillis()-touchStartTime[pointer]>LONG_PRESS_TIME);}

	public boolean isDragged(){return isDragged(0);}
	public boolean isDragged(int pointer){
		if(!isTouched[pointer])return false;
		float dx=Math.abs(getDeltaX(pointer));
		float dy=Math.abs(getDeltaY(pointer));
		return dx>DRAG_THRESHOLD||dy>DRAG_THRESHOLD;
	}

	public boolean isDoubleTapped(){return isDoubleTapped(0);}
	public boolean isDoubleTapped(int pointer){
		long now=SystemClock.uptimeMillis();
		if(isTouched[pointer]&&(now-lastTapTime[pointer])<DOUBLE_TAP_TIME){
			lastTapTime[pointer]=0; // Reset for this pointer
			return true;
		}
		if(isTouched[pointer])lastTapTime[pointer]=now;
		return false;
	}

	public SwipeDirection getSwipeDirection(){return getSwipeDirection(0);}
	public SwipeDirection getSwipeDirection(int pointer){
		if(!isDragged(pointer))return SwipeDirection.NONE;
		float dx=getDeltaX(pointer);
		float dy=getDeltaY(pointer);
		if(Math.abs(dx)<DRAG_THRESHOLD&&Math.abs(dy)<DRAG_THRESHOLD)return SwipeDirection.NONE;
		return(Math.abs(dx)>Math.abs(dy))?(dx>0?SwipeDirection.RIGHT:SwipeDirection.LEFT):(dy>0?SwipeDirection.DOWN:SwipeDirection.UP);
	}

	public float getX(){return touchX[0];}
	public float getX(int pointer){return isTouched[pointer]?touchX[pointer]:-1;}

	public float getY(){return touchY[0];}
	public float getY(int pointer){return isTouched[pointer]?touchY[pointer]:-1;}

	public float getDeltaX(){return getDeltaX(0);}
	public float getDeltaX(int pointer){
		if(pointer>=MAX_TOUCHES)return 0;
		float dx=deltaX[pointer];
		deltaX[pointer]=0; // Reset after reading
		return dx;
	}

	public float getDeltaY(){return getDeltaY(0);}
	public float getDeltaY(int pointer){
		if(pointer>=MAX_TOUCHES)return 0;
		float dy=deltaY[pointer];
		deltaY[pointer]=0; // Reset after reading
		return dy;
	}

	public float getPinchDistance(){
		if(!isTouched[0]||!isTouched[1])return-1;
		float dx=touchX[1]-touchX[0];
		float dy=touchY[1]-touchY[0];
		return(float)Math.sqrt(dx*dx+dy*dy);
	}

	public enum SwipeDirection{
		NONE,
		LEFT,
		RIGHT,
		UP,
		DOWN;
	}
}
