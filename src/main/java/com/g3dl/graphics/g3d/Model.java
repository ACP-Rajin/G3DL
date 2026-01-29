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
import com.g3dl.*;
import com.g3dl.graphics.*;
import com.g3dl.graphics.g3d.lights.*;
import com.g3dl.graphics.shader.*;
import com.g3dl.math.*;
import java.nio.*;
import java.util.*;

public class Model{
	protected String vertexShaderCode=
	"uniform mat4 MVPMatrix;\n"+
	"uniform mat4 MMatrix;\n"+
	"uniform float pointSize;\n"+
	"uniform float time;\n"+
	"attribute vec4 position;\n"+
	"attribute vec2 uv;\n"+
	"attribute vec3 normal;\n"+
	"varying vec2 vUV;\n"+
	"varying vec3 vNormal;\n"+
	"void main(){\n"+
	"	gl_Position=MVPMatrix*position;\n"+
	"	gl_PointSize=pointSize;\n"+
	"	vUV=uv;\n"+
	"	vNormal=normalize(normal);\n"+
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
	"uniform sampler2D normalMap;\n"+
	"uniform sampler2D roughnessMap;\n"+
	"uniform sampler2D metallicMap;\n"+
	"uniform sampler2D ambientOcclusionMap;\n"+
	"uniform samplerCube environmentMap;\n"+
	"uniform vec3 uColor;\n"+
	"uniform float uAlpha;\n"+
	"uniform float uRoughness;\n"+
	"uniform float uMetallic;\n"+
	"uniform float uAmbientOcclusion;\n"+
	"uniform vec3 uEnvironmentColor;\n"+
	"uniform vec3 lightPositions[1];\n"+
	"uniform vec3 lightColors[1];\n"+
	"uniform vec3 viewPos;\n"+
	"varying vec2 vUV;\n"+
	"varying vec3 vNormal;\n"+
	"void main(){\n"+
	"	vec3 albedo=texture2D(texture,vUV).rgb;\n"+
	"	float alpha=texture2D(alphaMap,vUV).a+uAlpha;\n"+
	"	vec3 normal=texture2D(normalMap,vUV).rgb*2.0-1.0;\n"+
	"	float roughness=texture2D(roughnessMap,vUV).r+uRoughness;\n"+
	"	float metallic=texture2D(metallicMap,vUV).r+uMetallic;\n"+
	"	float ambientOcclusion=texture2D(ambientOcclusionMap,vUV).r+uAmbientOcclusion;\n"+
	"	vec4 environment=textureCube(environmentMap,normalize(vNormal));\n"+
	"	gl_FragColor=vec4(albedo,alpha);\n"+
	"}";
	
	protected float[]verticesArray,uvsArray,normalsArray;
	protected short[]indicesArray;

	protected FloatBuffer vertexBuffer,textureBuffer,normalBuffer;
	protected ShortBuffer indexBuffer;

	protected Vector3f p=new Vector3f(),r=new Vector3f(),s=new Vector3f(1);
	protected Matrix4f modelMatrix=new Matrix4f();

	protected Screen screen=new Screen(1,1);
	protected float pointSize=1;

	public int renderMode=GLES20.GL_TRIANGLES;

	protected int program,vertexShader,fragmentShader;

	protected int a_position,a_uv,a_normal;
	protected int u_pointSize,u_projectionMatrix,u_modelMatrix,u_resolution,u_time,u_camPos;
	//PBR
	protected int u_col,u_alpha,u_roughness,u_metallic,u_ambientOcclusion,u_environmentColor;
	protected Vector3f color=new Vector3f(1),environmentColor=new Vector3f(1);
	protected float alpha=1,roughness=0.2f,metallic=0,ambientOcclusion=0;

	protected int u_useTexture,u_useAlphaMap,u_useRoughnessMap,u_useMetallicMap,u_useAmbientOcclusion,u_useEnvironmentMap;
	protected boolean useTexture,useAlphaMap,useRoughnessMap,useMetallicMap,useAmbientOcclusionMap,useEnvironmentMap;

	protected int u_texture,u_alphaMap,u_normalMap,u_roughnessMap,u_metallicMap,u_ambientOcclusionMap,u_environmentMap;
	protected Texture tex,alphaMap,norMap,roughMap,metMap,ambientOcclusionMap;

	//Light
	//DirectionalLight
	protected int u_DLDirection,u_DLColor,u_DLIntensity,u_NumDirLights;
	protected ArrayList<DirectionalLight>dl=new ArrayList<>();
	protected int numDirLights=0,MAX_DIR_LIGHTS=4;
	//PointLight
	protected int u_PLPosition,u_PLColor,u_PLIntensity,u_PLRadius,u_NumPointLights;
	protected ArrayList<PointLight>pl=new ArrayList<>();
	protected int numPointLights=0,MAX_POINT_LIGHTS=8;
	//SpotLight
	protected int u_SLPosition,u_SLDirection,u_SLColor,u_SLIntensity,u_SLInnerCutoff,u_SLOuterCutoff,u_NumSpotLights;
	protected ArrayList<SpotLight>sl=new ArrayList<>();
	protected int numSpotLights=0,MAX_SPOT_LIGHTS=4;

	public Model(){init();}

	public Model(float[]vert,float[]tex,float[]norm,short[]ind){
		verticesArray=vert;
		uvsArray=tex;
		normalsArray=norm;
		indicesArray=ind;

		init();

		setVerticeArray(verticesArray);
		setTextureArray(uvsArray);
		setNormalArray(normalsArray);
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
		Matrix.translateM(modelMatrix.getValues(),0,p.x,p.y,p.z);
		Matrix.rotateM(modelMatrix.getValues(),0,r.z,0,0,1);
		Matrix.rotateM(modelMatrix.getValues(),0,r.y,0,1,0);
		Matrix.rotateM(modelMatrix.getValues(),0,r.x,1,0,0);
		Matrix.scaleM(modelMatrix.getValues(),0,s.x,s.y,s.z);

		GLES20.glUseProgram(program);

		ShaderStuff.enableAttributePointer(a_position,3,GLES20.GL_FLOAT,false,0,vertexBuffer);
		ShaderStuff.enableAttributePointer(a_uv,2,GLES20.GL_FLOAT,false,0,textureBuffer);
		ShaderStuff.enableAttributePointer(a_normal,3,GLES20.GL_FLOAT,false,0,normalBuffer);

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

		if(useRoughnessMap){
			ShaderStuff.bindAndUploadTexture(roughMap,GLES20.GL_TEXTURE2,GLES20.GL_RGBA,u_roughnessMap,GLES20.GL_TEXTURE_2D);
		}else{
			GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

			GLES20.glUniform1f(u_roughness,roughness);
		}

		if(useMetallicMap){
			ShaderStuff.bindAndUploadTexture(metMap,GLES20.GL_TEXTURE3,GLES20.GL_RGBA,u_metallicMap,GLES20.GL_TEXTURE_2D);
		}else{
			GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

			GLES20.glUniform1f(u_metallic,roughness);
		}

		if(useAmbientOcclusionMap){
			ShaderStuff.bindAndUploadTexture(ambientOcclusionMap,GLES20.GL_TEXTURE4,GLES20.GL_RGBA,u_roughnessMap,GLES20.GL_TEXTURE_2D);
		}else{
			GLES20.glActiveTexture(GLES20.GL_TEXTURE4);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);

			GLES20.glUniform1f(u_ambientOcclusion,ambientOcclusion);
		}

		if(useEnvironmentMap){
			/*GLES20.glActiveTexture(GLES20.GL_TEXTURE5);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP,environmentMap.getGlId());
			GLES20.glUniform1i(u_environmentMap,5);*/
		}else{
			/*GLES20.glActiveTexture(GLES20.GL_TEXTURE5);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP,0);*/

			GLES20.glUniform3f(u_environmentColor,environmentColor.x,environmentColor.y,environmentColor.z);
		}

		GLES20.glUniform1i(u_useTexture,(useTexture)?1:0);
		GLES20.glUniform1i(u_useAlphaMap,(useAlphaMap)?1:0);
		GLES20.glUniform1i(u_useRoughnessMap,(useRoughnessMap)?1:0);
		GLES20.glUniform1i(u_useMetallicMap,(useMetallicMap)?1:0);
		GLES20.glUniform1i(u_useAmbientOcclusion,(useAmbientOcclusionMap)?1:0);
		GLES20.glUniform1i(u_useEnvironmentMap,(useEnvironmentMap)?1:0);

		//Lighting
		//DirectionalLight
		numDirLights=Math.min(dl.size(),MAX_DIR_LIGHTS);
		float[]directionsDL=new float[numDirLights*3];
		float[]colorsDL=new float[numDirLights*3];
		float[]intensitiesDL=new float[numDirLights];
		for(int i=0;i<numDirLights;i++){
			System.arraycopy(dl.get(i).getDirection().toArray(),0,directionsDL,i*3,3);
			System.arraycopy(dl.get(i).getColor().toArray(),0,colorsDL,i*3,3);
			intensitiesDL[i]=dl.get(i).getIntensity();
		}
		GLES20.glUniform3fv(u_DLDirection,numDirLights,directionsDL,0);
		GLES20.glUniform3fv(u_DLColor,numDirLights,colorsDL,0);
		GLES20.glUniform1fv(u_DLIntensity,numDirLights,intensitiesDL,0);
		GLES20.glUniform1i(u_NumDirLights,numDirLights);
		//PointLight
		numPointLights=Math.min(pl.size(),MAX_POINT_LIGHTS);
		float[]positionsPL=new float[numPointLights*3];
		float[]colorsPL=new float[numPointLights*3];
		float[]intensitiesPL=new float[numPointLights];
		float[]radiiPL=new float[numPointLights];
		for(int i=0;i<numPointLights;i++){
			System.arraycopy(pl.get(i).getPos().toArray(),0,positionsPL,i*3,3);
			System.arraycopy(pl.get(i).getColor().toArray(),0,colorsPL,i*3,3);
			intensitiesPL[i]=pl.get(i).getIntensity();
			radiiPL[i]=pl.get(i).getRadius();
		}
		GLES20.glUniform3fv(u_PLPosition,numPointLights,positionsPL,0);
		GLES20.glUniform3fv(u_PLColor,numPointLights,colorsPL,0);
		GLES20.glUniform1fv(u_PLIntensity,numPointLights,intensitiesPL,0);
		GLES20.glUniform1fv(u_PLRadius,numPointLights,radiiPL,0);
		GLES20.glUniform1i(u_NumPointLights,numPointLights);
		//SpotLight
		numSpotLights=Math.min(sl.size(),MAX_SPOT_LIGHTS);
		float[]positionsSL=new float[numSpotLights*3];
		float[]directionsSL=new float[numSpotLights*3];
		float[]colorsSL=new float[numSpotLights*3];
		float[]intensitiesSL=new float[numSpotLights];
		float[]innerCutoffsSL=new float[numSpotLights];
		float[]outerCutoffsSL=new float[numSpotLights];
		for(int i=0;i<numSpotLights;i++){
			System.arraycopy(sl.get(i).getPos().toArray(),0,positionsSL,i*3,3);
			System.arraycopy(sl.get(i).getDirection().toArray(),0,directionsSL,i*3,3);
			System.arraycopy(sl.get(i).getColor().toArray(),0,colorsSL,i*3,3);
			intensitiesSL[i]=sl.get(i).getIntensity();
			innerCutoffsSL[i]=(float) Math.cos(Math.toRadians(sl.get(i).getInnerCutoffAngle()));
			outerCutoffsSL[i]=(float) Math.cos(Math.toRadians(sl.get(i).getOuterCutoffAngle()));
		}
		GLES20.glUniform3fv(u_SLPosition,numSpotLights,positionsSL,0);
		GLES20.glUniform3fv(u_SLDirection,numSpotLights,directionsSL,0);
		GLES20.glUniform3fv(u_SLColor,numSpotLights,colorsSL,0);
		GLES20.glUniform1fv(u_SLIntensity,numSpotLights,intensitiesSL,0);
		GLES20.glUniform1fv(u_SLInnerCutoff,numSpotLights,innerCutoffsSL,0);
		GLES20.glUniform1fv(u_SLOuterCutoff,numSpotLights,outerCutoffsSL,0);
		GLES20.glUniform1i(u_NumSpotLights,numSpotLights);

		GLES20.glDrawElements(renderMode,indicesArray.length,GLES20.GL_UNSIGNED_SHORT,indexBuffer);

		GLES20.glDisableVertexAttribArray(a_position);
		GLES20.glDisableVertexAttribArray(a_uv);
		GLES20.glDisableVertexAttribArray(a_normal);
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
	public void setNormalArray(float...nArray){
		normalsArray=nArray;
		ByteBuffer nb=ByteBuffer.allocateDirect(normalsArray.length*4);
		nb.order(ByteOrder.nativeOrder());
		normalBuffer=nb.asFloatBuffer();
		normalBuffer.put(normalsArray);
		normalBuffer.position(0);
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
		a_normal=GLES20.glGetAttribLocation(program,"normal");
		u_pointSize=GLES20.glGetUniformLocation(program,"pointSize");
		u_projectionMatrix=GLES20.glGetUniformLocation(program,"projectionMatrix");
		u_modelMatrix=GLES20.glGetUniformLocation(program,"modelMatrix");
		u_resolution=GLES20.glGetUniformLocation(program,"resolution");
		u_time=GLES20.glGetUniformLocation(program,"time");
		u_camPos=GLES20.glGetUniformLocation(program,"camPos");

		//PBR
		u_col=GLES20.glGetUniformLocation(program,"uColor");
		u_alpha=GLES20.glGetUniformLocation(program,"uAlpha");
		u_roughness=GLES20.glGetUniformLocation(program,"uRoughness");
		u_metallic=GLES20.glGetUniformLocation(program,"uMetallic");
		u_ambientOcclusion=GLES20.glGetUniformLocation(program,"uAmbientOcclusion");
		u_environmentColor=GLES20.glGetUniformLocation(program,"uEnvironmentColor");

		u_useTexture=GLES20.glGetUniformLocation(program,"useTexture");
		u_useAlphaMap=GLES20.glGetUniformLocation(program,"useAlphaMap");
		u_useRoughnessMap=GLES20.glGetUniformLocation(program,"useRoughnessMap");
		u_useMetallicMap=GLES20.glGetUniformLocation(program,"useMetallicMap");
		u_useAmbientOcclusion=GLES20.glGetUniformLocation(program,"useAmbientOcclusion");
		u_useEnvironmentMap=GLES20.glGetUniformLocation(program,"useEnvironmentMap");

		u_texture=GLES20.glGetUniformLocation(program,"texture");
		u_alphaMap=GLES20.glGetUniformLocation(program,"alphaMap");
		u_normalMap=GLES20.glGetUniformLocation(program,"normalMap");
		u_roughnessMap=GLES20.glGetUniformLocation(program,"roughnessMap");
		u_metallicMap=GLES20.glGetUniformLocation(program,"metallicMap");
		u_ambientOcclusionMap=GLES20.glGetUniformLocation(program,"ambientOcclusionMap");
		u_environmentMap=GLES20.glGetUniformLocation(program,"environmentMap");
		//Lighting
		//DirectionalLight
		u_DLDirection=GLES20.glGetUniformLocation(program,"dirLightDirection");
		u_DLColor=GLES20.glGetUniformLocation(program,"dirLightColor");
		u_DLIntensity=GLES20.glGetUniformLocation(program,"dirLightIntensity");
		u_NumDirLights=GLES20.glGetUniformLocation(program,"numDirLights");
		//PointLight
		u_PLPosition=GLES20.glGetUniformLocation(program,"pointLightPosition");
		u_PLColor=GLES20.glGetUniformLocation(program,"pointLightColor");
		u_PLIntensity=GLES20.glGetUniformLocation(program,"pointLightIntensity");
		u_PLRadius=GLES20.glGetUniformLocation(program,"pointLightRadius");
		u_NumPointLights=GLES20.glGetUniformLocation(program,"numPointLights");
		//SpotLight
		u_SLPosition=GLES20.glGetUniformLocation(program,"spotLightPosition");
		u_SLDirection=GLES20.glGetUniformLocation(program,"spotLightDirection");
		u_SLColor=GLES20.glGetUniformLocation(program,"spotLightColor");
		u_SLIntensity=GLES20.glGetUniformLocation(program,"spotLightIntensity");
		u_SLInnerCutoff=GLES20.glGetUniformLocation(program,"spotLightInnerCutoff");
		u_SLOuterCutoff=GLES20.glGetUniformLocation(program,"spotLightOuterCutoff");
		u_NumSpotLights=GLES20.glGetUniformLocation(program,"numSpotLights");
	}

	public void dispose(){
		ShaderStuff.detachShader(program,vertexShader,fragmentShader);
		GLES20.glDeleteShader(vertexShader);
		GLES20.glDeleteShader(fragmentShader);
		GLES20.glDeleteProgram(program);

		vertexBuffer=null;
		textureBuffer=null;
		normalBuffer=null;
		tex=null;
		norMap=null;
		roughMap=null;
		metMap=null;
	}

	public void setPos(float x,float y,float z){p.setXYZ(x,y,z);}
	public void setPos(Vector3f pos){p.setXYZ(pos);}

	public void setX(float x){p.x=x;}
	public void setY(float y){p.y=y;}
	public void setZ(float z){p.z=z;}

	public void setRotation(float xAngle,float yAngle,float zAngle){r.setXYZ(xAngle,yAngle,zAngle);}
	public void setRotation(Vector3f rot){r.setXYZ(rot);}

	public void setRotationX(float xAng){r.x=xAng;}
	public void setRotationY(float yAng){r.y=yAng;}
	public void setRotationZ(float zAng){r.z=zAng;}

	public void setScale(float scaleX,float scaleY,float scaleZ){s.setXYZ(scaleX,scaleY,scaleZ);}
	public void setScale(Vector3f scale){s.setXYZ(scale);}
	public void setScale(float scale){s.setXYZ(scale);}

	public void setScaleX(float scaleX){s.x=scaleX;}
	public void setScaleY(float scaleY){s.y=scaleY;}
	public void setScaleZ(float scaleZ){s.z=scaleZ;}

	public void setColor(float r,float g,float b){color.setXYZ(r,g,b);}
	public void setColor(Vector3f rgb){color.setXYZ(rgb);}

	public void setR(float r){color.x=r;}
	public void setG(float g){color.y=g;}
	public void setB(float b){color.z=b;}
	public void setA(float a){alpha=a;}

	public void setRoughness(float roughness){this.roughness=roughness;}
	public void setMetallic(float metallic){this.metallic=metallic;}
	public void setAmbientOcclusion(float ambientOcclusion){this.ambientOcclusion=ambientOcclusion;}
	public void setEnvironmentColor(Vector3f environmentColor){this.environmentColor=environmentColor;}

	public void useTexture(boolean useTexture){this.useTexture=useTexture;}
	public void useAlphaMap(boolean useAlphaMap){this.useAlphaMap=useAlphaMap;}
	public void useRoughnessMap(boolean useRoughnessMap){this.useRoughnessMap=useRoughnessMap;}
	public void useMetallicMap(boolean useMetallicMap){this.useMetallicMap=useMetallicMap;}
	public void useAmbientOcclusionMap(boolean useAmbientOcclusionMap){this.useAmbientOcclusionMap=useAmbientOcclusionMap;}
	public void useEnvironmentMap(boolean useEnvironmentMap){this.useEnvironmentMap=useEnvironmentMap;}

	public void setTexture(Texture texture){this.tex=texture;}
	public void setAlphaMap(Texture alphaMap){this.alphaMap=alphaMap;}
	public void setNormalMap(Texture normalMap){this.norMap=normalMap;}
	public void setRoughnessMap(Texture roughnessMap){this.roughMap=roughnessMap;}
	public void setMetallicMap(Texture metallicMap){this.metMap=metallicMap;}
	public void setAmbientOcclusionMap(Texture ambientOcclusionMap){this.ambientOcclusionMap=ambientOcclusionMap;}

	public Vector3f getColor(){return color;}

	public float getR(){return color.x;}
	public float getG(){return color.y;}
	public float getB(){return color.z;}
	public float getA(){return alpha;}

	public float getRoughness(){return roughness;}
	public float getMetallic(){return metallic;}
	public float getAmbientOcclusion(){return ambientOcclusion;}
	public Vector3f getEnvironmentColor(){return environmentColor;}

	public boolean isUseTexture(){return useTexture;}
	public boolean isUseAlphaMap(){return useAlphaMap;}
	public boolean isUseRoughnessMap(){return useRoughnessMap;}
	public boolean isUseMetallicMap(){return useMetallicMap;}
	public boolean isUseAmbientOcclusionMap(){return useAmbientOcclusionMap;}
	public boolean isUseEnvironmentMap(){return useEnvironmentMap;}

	public Texture getTexture(){return tex;}
	public Texture getAlphaMap(){return alphaMap;}
	public Texture getNormalMap(){return norMap;}
	public Texture getRoughnessMap(){return roughMap;}
	public Texture getMetallicMap(){return metMap;}
	public Texture getAmbientOcclusionMap(){return ambientOcclusionMap;}

	public Vector3f getPosition(){return p;}
	public Vector3f getRotation(){return r;}
	public Vector3f getScale(){return s;}

	public void setPointSize(float size){pointSize=size;}

	public void setScreen(Screen screen){this.screen=screen;}
	public Screen getScreen(){return screen;}

	public void addDirectionalLight(DirectionalLight directionalLight){dl.add(directionalLight);}
	public void addPointLight(PointLight pointLight){pl.add(pointLight);}
	public void addSpotLight(SpotLight spotLight){sl.add(spotLight);}

	public void removeDirectionalLight(DirectionalLight directionalLight){dl.remove(directionalLight);}
	public void removePointLight(PointLight pointLight){pl.remove(pointLight);}
	public void removeSpotLight(SpotLight spotLight){sl.remove(spotLight);}

	public int getProgram(){return program;}

	public float getPointSize(){return pointSize;}
}
