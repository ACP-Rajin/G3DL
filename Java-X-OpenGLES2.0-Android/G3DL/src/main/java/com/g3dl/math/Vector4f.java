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

public class Vector4f{
	public static Vector4f zero=new Vector4f();

	public float x=0,y=0,z=0,w=0;

	public Vector4f(){}
	public Vector4f(float value){x=value;y=value;z=value;w=value;}
	public Vector4f(float x,float y,float z,float w){this.x=x;this.y=y;this.z=z;this.w=w;}

	public float[]toArray(){return new float[]{x,y,z,w};}

	public void setXYZW(float x,float y,float z,float w){
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}

	public void setXYZW(Vector4f vec4){
		x=vec4.x;
		y=vec4.y;
		z=vec4.z;
		w=vec4.w;
	}

	public void setXYZW(float xyzw){
		x=xyzw;
		y=xyzw;
		z=xyzw;
	}
}
