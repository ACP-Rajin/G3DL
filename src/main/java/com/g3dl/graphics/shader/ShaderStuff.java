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

package com.g3dl.graphics.shader;

import android.opengl.*;
import com.g3dl.graphics.*;
import java.nio.*;

public class ShaderStuff{
	public static int loadShader(int type,String shaderCode){
		int shader=GLES20.glCreateShader(type);
		if(shader==0)throw new RuntimeException("Error creating shader");

		GLES20.glShaderSource(shader,shaderCode);
		GLES20.glCompileShader(shader);

		int[]compileStatus=new int[1];
		GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,compileStatus,0);

		if(compileStatus[0]==0){
			String error=GLES20.glGetShaderInfoLog(shader);
			GLES20.glDeleteShader(shader);
			throw new RuntimeException("Shader compilation failed: "+error);
		}
		return shader;
	}

	public static void attachShader(int program,int vertexShader,int fragmentShader){
		GLES20.glAttachShader(program,vertexShader);
		GLES20.glAttachShader(program,fragmentShader);
		GLES20.glLinkProgram(program);
	}

	public static void detachShader(int program,int vertexShader,int fragmentShader){
		GLES20.glDetachShader(program,vertexShader);
		GLES20.glDetachShader(program,fragmentShader);
	}

	public static void enableAttributePointer(int index,int size,int type,boolean normalized,int stride,Buffer buffer){
		GLES20.glVertexAttribPointer(index,size,type,normalized,stride,buffer);
		GLES20.glEnableVertexAttribArray(index);
	}

	public static void glDisableVertexAttribArrays(int[] indexes){
		for(int i=0;i<indexes.length;i++){
			GLES20.glDisableVertexAttribArray(indexes[i]);
		}
	}

	public static void bindAndUploadTexture(Texture texture,int textureUnit,int format,int uniformLocation,int target){
		if(texture!=null&&texture.getBitmap()!=null){
			GLES20.glActiveTexture(textureUnit);
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,texture.getGlId());
			if(!texture.getBitmap().isRecycled()){
				GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,format,texture.getBitmap(),0);
				texture.getBitmap().recycle();
			}
			GLES20.glUniform1i(uniformLocation,textureUnit);
		}
	}
}
