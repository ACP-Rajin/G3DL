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

package com.g3dl.graphics.g3d.lights;

import com.g3dl.math.*;

public class Light{
	private Vector3f p=new Vector3f();
	private Vector3f c=new Vector3f(1);
	private float intensity=0;

	public Light(){}
	public Light(Vector3f position,Vector3f rgb,float intensity){
		p=position;
		c=rgb;
		this.intensity=intensity;
	}

	public void setPos(float x,float y,float z){p.setXYZ(x,y,z);}
	public void setPos(Vector3f pos){p.setXYZ(pos);}

	public void setX(float x){p.x=x;}
	public void setY(float y){p.y=y;}
	public void setZ(float z){p.z=z;}

	public void setColor(float r,float g,float b){c.setXYZ(r,g,b);}
	public void setColor(Vector3f rgba){c.setXYZ(rgba);}

	public void setR(float r){c.x=r;}
	public void setG(float g){c.y=g;}
	public void setB(float b){c.z=b;}

	public void setIntensity(float intensity){this.intensity= intensity;}

	public Vector3f getPos(){return p;}
	public Vector3f getColor(){return c;}
	public float getIntensity(){return intensity;}
}
