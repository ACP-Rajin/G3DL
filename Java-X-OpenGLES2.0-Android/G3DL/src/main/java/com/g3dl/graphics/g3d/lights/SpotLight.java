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

public class SpotLight extends Light{
	private Vector3f d=new Vector3f(1);
	private float innerCutoff=0;
	private float outerCutoff=0;

	public SpotLight(){}
	public SpotLight(Vector3f position,Vector3f direction,Vector3f color,float intensity,float innerAngle,float outerAngle){
		super(position,color,intensity);
		this.d.setXYZ(direction);
		setInnerCutoffAngle(innerAngle);
		setOuterCutoffAngle(outerAngle);
	}

    public void setDirection(float dx,float dy,float dz){d.setXYZ(dx,dy,dz);}
	public void setDirection(Vector3f direction){d.setXYZ(direction);}

	public void setDX(float dx){d.x=dx;}
	public void setDY(float dy){d.y=dy;}
	public void setDZ(float dz){d.z=dz;}

	public void setInnerCutoffAngle(float angleDegrees){this.innerCutoff=(float)Math.cos(Math.toRadians(angleDegrees));}
	public void setOuterCutoffAngle(float angleDegrees){this.outerCutoff=(float)Math.cos(Math.toRadians(angleDegrees));}

	public Vector3f getDirection(){return d;}
	public float getInnerCutoffAngle(){return innerCutoff;}
	public float getOuterCutoffAngle(){return outerCutoff;}
}
