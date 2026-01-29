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

package com.g3dl.graphics.g2d;

import android.opengl.*;
import com.g3dl.*;
import com.g3dl.graphics.*;
import com.g3dl.graphics.shader.*;
import com.g3dl.math.*;
import java.nio.*;

public class Sprite{
	protected String vertexShaderCode=
	"uniform mat4 MVPMatrix;\n"+
	"uniform mat4 MMatrix;\n"+
	"uniform float pointSize;\n"+
	"uniform float time;\n"+
	"attribute vec4 position;\n"+
	"attribute vec2 uv;\n"+
	"varying vec2 vUV;\n"+
	"void main(){\n"+
	"	gl_Position=MVPMatrix*position;\n"+
	"	gl_PointSize=pointSize;\n"+
	"	vUV=uv;\n"+
	"}",fragmentShaderCode=
	"#ifdef GL_FRAGMENT_PRECISION_HIGH\n"+
	"precision highp float;\n"+
	"#else\n"+
	"precision mediump float;\n"+
	"#endif\n"+
	"uniform float time;\n"+
	"uniform vec2 resolution;\n"+
	"uniform sampler2D texture;\n"+
	"uniform sampler2D alphaMap;\n"+
	"uniform vec3 uColor;\n"+
	"uniform float uAlpha;\n"+
	"uniform vec3 viewPos;\n"+
	"varying vec2 vUV;\n"+
	"void main(){\n"+
	"	vec3 albedo=texture2D(texture,vUV).rgb;\n"+
	"	float alpha=texture2D(alphaMap,vUV).a+uAlpha;\n"+
	"	gl_FragColor=vec4(1.0);//vec4(albedo,alpha);\n"+
	"}";

	protected float[]verticesArray,uvsArray;
	protected short[]indicesArray;

	protected FloatBuffer vertexBuffer,textureBuffer;
	protected ShortBuffer indexBuffer;

	protected Vector2f p=new Vector2f(),s=new Vector2f(1);
	protected Vector3f r=new Vector3f();
	protected Matrix4f modelMatrix=new Matrix4f();

	protected Screen screen=G3D.glScreen; // new Screen(1,1);
	protected float pointSize=1;

	public int renderMode=GLES20.GL_TRIANGLES;

	protected int program,vertexShader,fragmentShader;

	protected int a_position,a_uv;
	protected int u_pointSize,u_projectionMatrix,u_modelMatrix,u_resolution,u_time,u_camPos;

	protected int u_col,u_alpha;
	protected Vector3f color=new Vector3f(1);
	protected float alpha=1;

	protected int u_useTexture,u_useAlphaMap;
	protected boolean useTexture,useAlphaMap;

	protected int u_texture,u_alphaMap;
	protected Texture tex,alphaMap;

	public Sprite(){init();}
	public Sprite(float[]vert,float[]tex,short[]ind){
		verticesArray=vert;
		uvsArray=tex;
		indicesArray=ind;

		init();

		setVerticeArray(verticesArray);
		setTextureArray(uvsArray);
		setIndiceArray(indicesArray);
	}

	private void init(){
		vertexShader=ShaderStuff.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
		fragmentShader=ShaderStuff.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

		program=GLES20.glCreateProgram();
		ShaderStuff.attachShader(program,vertexShader,fragmentShader);

		initLocation();
	}

	public void draw(Camera cam){
		modelMatrix.identity();
		Matrix.translateM(modelMatrix.getValues(),0,p.x,p.y,0);
		Matrix.rotateM(modelMatrix.getValues(),0,r.z,0,0,1);
		Matrix.rotateM(modelMatrix.getValues(),0,r.y,0,1,0);
		Matrix.rotateM(modelMatrix.getValues(),0,r.x,1,0,0);
		Matrix.scaleM(modelMatrix.getValues(),0,s.x,s.y,1);

		GLES20.glUseProgram(program);

		ShaderStuff.enableAttributePointer(a_position,2,GLES20.GL_FLOAT,false,0,vertexBuffer);
		ShaderStuff.enableAttributePointer(a_uv,2,GLES20.GL_FLOAT,false,0,textureBuffer);

		GLES20.glUniform1f(u_pointSize,pointSize);
		GLES20.glUniformMatrix4fv(u_projectionMatrix,1,false,cam.getProjectionMatrix(),0);
		GLES20.glUniformMatrix4fv(u_modelMatrix,1,false,modelMatrix.getValues(),0);
		GLES20.glUniform2f(u_resolution,screen.getWidth(),screen.getHeight());
		GLES20.glUniform1f(u_time,G3D.renderer.getTime());

		GLES20.glUniform3f(u_camPos,cam.getPos().x,cam.getPos().y,cam.getPos().z);

		if(useTexture){
			ShaderStuff.bindAndUploadTexture(tex,GLES20.GL_TEXTURE0,GLES20.GL_RGBA,u_texture,GLES20.GL_TEXTURE_2D);
		}else{
			GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

			GLES20.glUniform3f(u_col,color.x,color.y,color.z);
		}

		if(useAlphaMap){
			ShaderStuff.bindAndUploadTexture(alphaMap,GLES20.GL_TEXTURE1,GLES20.GL_RGBA,u_alphaMap,GLES20.GL_TEXTURE_2D);
		}else{
			GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

			GLES20.glUniform1f(u_alpha,alpha);
		}

		GLES20.glUniform1i(u_useTexture,(useTexture)?1:0);
		GLES20.glUniform1i(u_useAlphaMap,(useAlphaMap)?1:0);

		GLES20.glDrawElements(renderMode,indicesArray.length,GLES20.GL_UNSIGNED_SHORT,indexBuffer);

		GLES20.glDisableVertexAttribArray(a_position);
		GLES20.glDisableVertexAttribArray(a_uv);
	}

	public void setVerticeArray(float...vArray){
		verticesArray=vArray;
		ByteBuffer vb=ByteBuffer.allocateDirect(verticesArray.length*4);
		vb.order(ByteOrder.nativeOrder());
		vertexBuffer=vb.asFloatBuffer();
		vertexBuffer.put(verticesArray);
		vertexBuffer.position(0);
	}
	public void setTextureArray(float...tArray){
		uvsArray=tArray;
		ByteBuffer tb=ByteBuffer.allocateDirect(uvsArray.length*4);
		tb.order(ByteOrder.nativeOrder());
		textureBuffer=tb.asFloatBuffer();
		textureBuffer.put(uvsArray);
		textureBuffer.position(0);
	}
	public void setIndiceArray(short...iArray){
		indicesArray=iArray;
		ByteBuffer ib=ByteBuffer.allocateDirect(indicesArray.length*2);
		ib.order(ByteOrder.nativeOrder());
		indexBuffer=ib.asShortBuffer();
		indexBuffer.put(indicesArray);
		indexBuffer.position(0);
	}

	public void setShader(String vertexCode,String fragmentCode){
		vertexShaderCode=vertexCode;
		fragmentShaderCode=fragmentCode;
		int vsc=ShaderStuff.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
		int fsc=ShaderStuff.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
		ShaderStuff.detachShader(program,vertexShader,fragmentShader);
		ShaderStuff.attachShader(program,vsc,fsc);
		GLES20.glDeleteShader(vertexShader);
		GLES20.glDeleteShader(fragmentShader);
		vertexShader=vsc;
		fragmentShader=fsc;
		initLocation();
	}

	private void initLocation(){
		a_position=GLES20.glGetAttribLocation(program,"position");
		a_uv=GLES20.glGetAttribLocation(program,"uv");
		u_pointSize=GLES20.glGetUniformLocation(program,"pointSize");
		u_projectionMatrix=GLES20.glGetUniformLocation(program,"projectionMatrix");
		u_modelMatrix=GLES20.glGetUniformLocation(program,"modelMatrix");
		u_resolution=GLES20.glGetUniformLocation(program,"resolution");
		u_time=GLES20.glGetUniformLocation(program,"time");
		u_camPos=GLES20.glGetUniformLocation(program,"camPos");

		u_col=GLES20.glGetUniformLocation(program,"uColor");
		u_alpha=GLES20.glGetUniformLocation(program,"uAlpha");

		u_useTexture=GLES20.glGetUniformLocation(program,"useTexture");
		u_useAlphaMap=GLES20.glGetUniformLocation(program,"useAlphaMap");

		u_texture=GLES20.glGetUniformLocation(program,"texture");
		u_alphaMap=GLES20.glGetUniformLocation(program,"alphaMap");
	}

	public void dispose(){
		ShaderStuff.detachShader(program,vertexShader,fragmentShader);
		GLES20.glDeleteShader(vertexShader);
		GLES20.glDeleteShader(fragmentShader);
		GLES20.glDeleteProgram(program);

		vertexBuffer=null;
		textureBuffer=null;
		tex=null;
	}

	public void setPos(float x,float y){p.setXY(x,y);}
	public void setPos(Vector2f pos){p.setXY(pos);}

	public void setX(float x){p.x=x;}
	public void setY(float y){p.y=y;}

	public void setRotation(float xAngle,float yAngle,float zAngle){r.setXYZ(xAngle,yAngle,zAngle);}
	public void setRotation(Vector3f rot){r.setXYZ(rot);}

	public void setRotationX(float xAng){r.x=xAng;}
	public void setRotationY(float yAng){r.y=yAng;}
	public void setRotationZ(float zAng){r.z=zAng;}

	public void setScale(float scaleX,float scaleY){s.setXY(scaleX,scaleY);}
	public void setScale(Vector2f scale){s.setXY(scale);}
	public void setScale(float scale){s.setXY(scale);}

	public void setScaleX(float scaleX){s.x=scaleX;}
	public void setScaleY(float scaleY){s.y=scaleY;}

	public void setColor(float r,float g,float b){color.setXYZ(r,g,b);}
	public void setColor(Vector3f rgb){color.setXYZ(rgb);}

	public void setR(float r){color.x=r;}
	public void setG(float g){color.y=g;}
	public void setB(float b){color.z=b;}
	public void setA(float a){alpha=a;}

	public void useTexture(boolean useTexture){this.useTexture=useTexture;}
	public void useAlphaMap(boolean useAlphaMap){this.useAlphaMap=useAlphaMap;}

	public void setTexture(Texture texture){this.tex=texture;}
	public void setAlphaMap(Texture alphaMap){this.alphaMap=alphaMap;}

	public Vector3f getColor(){return color;}

	public float getR(){return color.x;}
	public float getG(){return color.y;}
	public float getB(){return color.z;}
	public float getA(){return alpha;}

	public boolean isUseTexture(){return useTexture;}
	public boolean isUseAlphaMap(){return useAlphaMap;}

	public Texture getTexture(){return tex;}
	public Texture getAlphaMap(){return alphaMap;}

	public Vector2f getPosition(){return p;}
	public Vector3f getRotation(){return r;}
	public Vector2f getScale(){return s;}

	public void setPointSize(float size){pointSize=size;}

	public void setScreen(Screen screen){this.screen=screen;}
	public Screen getScreen(){return screen;}

	public int getProgram(){return program;}

	public float getPointSize(){return pointSize;}
}
