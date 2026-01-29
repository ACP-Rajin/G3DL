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

import android.view.*;
import com.g3dl.math.*;
import com.g3dl.*;

public abstract class UI{
	protected Vector2f p=new Vector2f(),s=new Vector2f(1);
	protected Vector3f r=new Vector3f(),color=new Vector3f(1);
	protected float alpha=1;
	protected Screen screen=G3D.glScreen; // = new Screen(1,1);

	public void resize(Screen screen){this.screen=screen;}

	public abstract void render(Camera cam)
	public abstract boolean isClicked()
	public abstract boolean isLongPreesed()
	public abstract boolean isDragged()

	public abstract void event(MotionEvent e)

	public void setPos(float x,float y){p.setXY(x,y);}
	public void setPos(Vector2f pos){p.setXY(pos);}

	public void setX(float x){p.x=x;}
	public void setY(float y){p.y=y;}

	public void setRot(float xAngle,float yAngle,float zAngle){r.setXYZ(xAngle,yAngle,zAngle);}
	public void setRot(Vector3f rot){r.setXYZ(rot);}

	public void setRotationX(float xAng){r.x=xAng;}
	public void setRotationY(float yAng){r.y=yAng;}
	public void setRotationZ(float zAng){r.y=zAng;}

	public void setScale(float scaleX,float scaleY){s.setXY(scaleX,scaleY);}
	public void setScale(Vector2f scale){s.setXY(scale);}
	public void setScale(float scale){s.setXY(scale);}

	public void setScaleX(float scaleX){s.x=scaleX;}
	public void setScaleY(float scaleY){s.y=scaleY;}

	public void setColor(float r,float g,float b){color.setXYZ(r,g,b);}
	public void setColor(Vector3f rgb){color.setXYZ(rgb);}

	public void setR(float r){color.x=r;}
	public void setG(float g){color.y=g;}
	public void setB(float b){color.z=b;}
	public void setA(float a){alpha=a;}

	public Vector2f getPosition(){return p;}
	public Vector3f getRotation(){return r;}
	public Vector2f getScale(){return s;}

	public void setScreen(Screen screen){this.screen=screen;}
	public Screen getScreen(){return screen;}
}
