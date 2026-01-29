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

package com.g3dl.math;

public class Vector3f{
	public static Vector3f zero=new Vector3f();

	public float x=0,y=0,z=0;
	

	public Vector3f(){}
	public Vector3f(float value){x=value;y=value;z=value;}
	public Vector3f(float x,float y,float z){this.x=x;this.y=y;this.z=z;}

	public float[]toArray(){return new float[]{x,y,z};}

	public void setXYZ(float x,float y,float z){
		this.x=x;
		this.y=y;
		this.z=z;
	}

	public void setXYZ(Vector3f vec3){
		x=vec3.x;
		y=vec3.y;
		z=vec3.z;
	}

	public void setXYZ(float xyz){
		x=xyz;
		y=xyz;
		z=xyz;
	}
}
