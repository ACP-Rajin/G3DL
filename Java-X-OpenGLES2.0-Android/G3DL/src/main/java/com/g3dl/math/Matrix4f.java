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

public class Matrix4f{
	private float[]val=new float[16];

	public Matrix4f(){identity();}
	public Matrix4f(float...values){val=values;}

	public void identity(){setValues(1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1);}

	public void setValues(float...values){System.arraycopy(values,0,val,0,16);}
	public void setValue(int x,int y,float v){val[x*4+y]=v;}
	public float[]getValues(){return val;}
	public float getValue(int x,int y){return val[x*4+y];}
}
