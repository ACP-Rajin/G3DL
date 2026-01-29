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

public class Vector2f{
	public static Vector2f zero=new Vector2f();

	public float x=0,y=0;

	public Vector2f(){}
	public Vector2f(float value){x=value;y=value;}
	public Vector2f(float x,float y){this.x=x;this.y=y;}

	public float[]toArray(){return new float[]{x,y};}

	public void setXY(float x,float y){
		this.x=x;
		this.y=y;
	}

	public void setXY(Vector2f vec2){
		x=vec2.x;
		y=vec2.y;
	}

	public void setXY(float xy){
		x=xy;
		y=xy;
	}
}
