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

package com.g3dl.graphics.g3d;

import android.opengl.*;
import com.g3dl.graphics.*;
import com.g3dl.math.*;
import java.nio.*;

public class Pyramid extends Model{
    private static float[]vertices={
        // Base
        -0.5f, 0.0f, -0.5f,
        0.5f, 0.0f, -0.5f,
        0.5f, 0.0f, 0.5f,
        -0.5f, 0.0f, 0.5f,
        // Apex
        0.0f, 0.5f, 0.0f
	},textures = {
        0.0f, 0.0f,  1.0f, 0.0f,  1.0f, 1.0f,  0.0f, 1.0f,
        0.5f, 0.5f,  
        0.0f, 1.0f,  0.5f, 0.0f,  1.0f, 1.0f,  
        0.0f, 1.0f,  0.5f, 0.0f,  1.0f, 1.0f,  
        0.0f, 1.0f,  0.5f, 0.0f,  1.0f, 1.0f,  
        0.0f, 1.0f,  0.5f, 0.0f,  1.0f, 1.0f  
    },normals = {
        0.0f, -1.0f, 0.0f,  0.0f, -1.0f, 0.0f,  0.0f, -1.0f, 0.0f,  0.0f, -1.0f, 0.0f,
        0.0f, 0.4472f, -0.8944f,  0.0f, 0.4472f, -0.8944f,  0.0f, 0.4472f, -0.8944f,
        0.8944f, 0.4472f, 0.0f,  0.8944f, 0.4472f, 0.0f,  0.8944f, 0.4472f, 0.0f,
        0.0f, 0.4472f, 0.8944f,  0.0f, 0.4472f, 0.8944f,  0.0f, 0.4472f, 0.8944f,
        -0.8944f, 0.4472f, 0.0f,  -0.8944f, 0.4472f, 0.0f,  -0.8944f, 0.4472f, 0.0f
    };

    private static short[]indices={
        // Base
        0, 1, 2,
        0, 2, 3,
        // Sides
        0, 4, 1,
        1, 4, 2,
        2, 4, 3,
        3, 4, 0
    };

	public Pyramid(){
		super(vertices,textures,normals,indices);
	}
}
/*
public class Pyramid{
	private static final String vertexShaderCode=
	"uniform mat4 uMVPMatrix;"+
	"attribute vec4 aPosition;"+
	"void main(){"+
	"  gl_Position=uMVPMatrix*aPosition;"+
	"}";

    private static final String fragmentShaderCode=
	"precision mediump float;"+
	"void main(){"+
	"  gl_FragColor=vec4(0.0,1.0,0.0,1.0);"+
	"}";

    private static final float[]vertices={
        // Base
        -1.0f, 0.0f, -1.0f,
        1.0f, 0.0f, -1.0f,
        1.0f, 0.0f, 1.0f,
        -1.0f, 0.0f, 1.0f,
        // Apex
        0.0f, 1.0f, 0.0f
    };

    private static final short[]indices={
        // Base
        0, 1, 2,
        0, 2, 3,
        // Sides
        0, 4, 1,
        1, 4, 2,
        2, 4, 3,
        3, 4, 0
    };

	private final int program,positionHandle,mvpMatrixHandle;
	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;

	private float[] modelMatrix=new float[16];
	private Vector3f p,r,s;

	public Pyramid(){
		p=new Vector3f(0,0,0);
		r=new Vector3f(0,0,0);
		s=new Vector3f(1,1,1);
		int vertexShader=ShaderStuff.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
		int fragmentShader=ShaderStuff.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

		program=GLES20.glCreateProgram();
		GLES20.glAttachShader(program,vertexShader);
		GLES20.glAttachShader(program,fragmentShader);
		GLES20.glLinkProgram(program);

		positionHandle=GLES20.glGetAttribLocation(program,"aPosition");
		mvpMatrixHandle=GLES20.glGetUniformLocation(program,"uMVPMatrix");

		ByteBuffer bb=ByteBuffer.allocateDirect(vertices.length*4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer=bb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		ByteBuffer dlb=ByteBuffer.allocateDirect(indices.length*2);
		dlb.order(ByteOrder.nativeOrder());
		indexBuffer=dlb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}

	public void draw(float[]viewProjectionMatrix){
		Matrix.setIdentityM(modelMatrix,0);
		Matrix.translateM(modelMatrix,0,p.x,p.y,p.z);
		Matrix.rotateM(modelMatrix,0,r.z,0,0,1);
		Matrix.rotateM(modelMatrix,0,r.y,0,1,0);
		Matrix.rotateM(modelMatrix,0,r.x,1,0,0);
		Matrix.scaleM(modelMatrix,0,s.x,s.y,s.z);

		float[] mvpMatrix=new float[16];
		Matrix.multiplyMM(mvpMatrix,0,viewProjectionMatrix,0,modelMatrix,0);

		GLES20.glUseProgram(program);

		GLES20.glVertexAttribPointer(positionHandle,3,GLES20.GL_FLOAT,false,0,vertexBuffer);
		GLES20.glEnableVertexAttribArray(positionHandle);

		GLES20.glUniformMatrix4fv(mvpMatrixHandle,1,false,mvpMatrix,0);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES,indices.length,GLES20.GL_UNSIGNED_SHORT,indexBuffer);

		GLES20.glDisableVertexAttribArray(positionHandle);
	}

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

	public void setScale(float scaleX,float scaleY,float scaleZ){s.setXYZ(scaleX,scaleY,scaleZ);}
	public void setScale(Vector3f scale){s.setXYZ(scale);}
	public void setScale(float scale){s.setXYZ(scale);}

	public void setScaleX(float scaleX){p.x=scaleX;}
	public void setScaleY(float scaleY){p.y=scaleY;}
	public void setScaleZ(float scaleZ){p.z=scaleZ;}
}*/
