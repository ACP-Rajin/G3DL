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

import com.g3dl.math.*;
import com.g3dl.*;

public abstract class Camera{
	protected Matrix4f modelViewProjectionMatrix=new Matrix4f(),projectionMatrix=new Matrix4f(),viewMatrix=new Matrix4f();
	protected Screen screen=G3D.glScreen; // new Screen(1,1);
	protected float near=0.1f,far=100.0f;
	protected Vector3f p=new Vector3f(),r=new Vector3f(),lookAt=new Vector3f(),up=new Vector3f(0,1,0);

	public abstract void update()

	public void resize(Screen screen){this.screen=screen;}

	public abstract void lookAt(Vector3f position)

	public void setPos(float x,float y,float z){p.setXYZ(x,y,z);}
	public void setPos(Vector3f pos){p.setXYZ(pos);}

	public void setX(float x){p.x=x;}
	public void setY(float y){p.y=y;}
	public void setZ(float z){p.z=z;}

	public void setRot(float xAngle,float yAngle,float zAngle){r.setXYZ(xAngle,yAngle,zAngle);}
	public void setRot(Vector3f rot){r.setXYZ(rot);}

	public void setRotationX(float xAng){r.x=xAng;}
	public void setRotationY(float yAng){r.y=yAng;}
	public void setRotationZ(float zAng){r.z=zAng;}

	public void setNear(float value){near=value;}
	public void setFar(float value){far=value;}

	public Vector3f getPos(){return p;}
	public Vector3f getRot(){return r;}
	public float getNear(){return near;}
	public float getFar(){return far;}

	public float[]getProjectionMatrix(){return modelViewProjectionMatrix.getValues();}
}
