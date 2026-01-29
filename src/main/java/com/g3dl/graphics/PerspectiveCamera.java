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

import android.opengl.*;
import com.g3dl.math.*;
import com.g3dl.*;

public class PerspectiveCamera extends Camera{
	private float fov=3.6f,near=0.1f,far=100f;

	@Override
	public void update(){
		Matrix.perspectiveM(projectionMatrix.getValues(),0,fov,(float)screen.getWidth()/(float)screen.getHeight(),near,far);
		Matrix.setIdentityM(viewMatrix.getValues(),0);

		Matrix.rotateM(viewMatrix.getValues(),0,r.x,1,0,0);
		Matrix.rotateM(viewMatrix.getValues(),0,r.y,0,1,0);
		Matrix.rotateM(viewMatrix.getValues(),0,r.z,0,0,1);

		Matrix.translateM(viewMatrix.getValues(),0,p.x,p.y,p.z);

		Matrix.multiplyMM(modelViewProjectionMatrix.getValues(),0,projectionMatrix.getValues(),0,viewMatrix.getValues(),0);
	}

	@Override
	public void lookAt(Vector3f position){
		lookAt.setXYZ(position);
		Matrix.perspectiveM(projectionMatrix.getValues(),0,fov,(float)screen.getWidth()/(float)screen.getHeight(),near,far);
		Matrix.setIdentityM(viewMatrix.getValues(),0);

		Matrix.setLookAtM(viewMatrix.getValues(),0,p.x,p.y,p.z,position.x,position.y,position.z,up.x,up.y,up.z);

		Matrix.multiplyMM(modelViewProjectionMatrix.getValues(),0,projectionMatrix.getValues(),0,viewMatrix.getValues(),0);
	}

	public void setFOV(float fov){this.fov=fov;}
	public float getFOV(){return fov;}
}
