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

public class Program{
	private final int programId;
	private Shader vs,fs;
	private OnInitLocationListener oill;

	public Program(Shader vertexShader,Shader fragmentShader){
		programId=GLES20.glCreateProgram();
		vs=vertexShader;
		fs=fragmentShader;
		ShaderStuff.attachShader(programId,vs.getId(),fs.getId());

		setOnInitLocationListener(new OnInitLocationListener(){
			@Override
			public void onInitLocation(){
				// TODO: Implement this method
			}
		});

		initLocation();
	}

	public void addAttribute(Attribute attribute){
		// TODO: Implement this method
	}

	public void addUniform(Uniform uniform){
		// TODO: Implement this method
	}

	public void setOnInitLocationListener(OnInitLocationListener listener){oill=listener;}

	public void initLocation(){oill.onInitLocation();}

	public void replaceShader(Shader vertexShader,Shader fragmentShader){
		ShaderStuff.detachShader(programId,vs.getId(),fs.getId());
		vs.delete();
		fs.delete();
		vs=vertexShader;
		fs=fragmentShader;
		ShaderStuff.attachShader(programId,vs.getId(),fs.getId());
		oill.onInitLocation();
	}

	public interface OnInitLocationListener{void onInitLocation();}

	public void useProgram(){GLES20.glUseProgram(programId);}

	public int getId(){return programId;}
}
