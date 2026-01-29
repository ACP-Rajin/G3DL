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

public class DirectionalLight extends Light{
	private Vector3f d=new Vector3f(1);

	public DirectionalLight(){}
	public DirectionalLight(Vector3f direction,Vector3f color,float intensity){
		super(new Vector3f(),color,intensity);
		this.d=direction;
	}

	public void setDirection(float dx,float dy,float dz){d.setXYZ(dx,dy,dz);}
	public void setDirection(Vector3f direction){d.setXYZ(direction);}

	public void setDX(float dx){d.x=dx;}
	public void setDY(float dy){d.y=dy;}
	public void setDZ(float dz){d.z=dz;}

	public Vector3f getDirection(){return d;}
}
