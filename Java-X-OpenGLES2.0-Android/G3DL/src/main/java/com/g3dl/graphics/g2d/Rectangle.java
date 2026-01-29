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

public class Rectangle extends Sprite{
	private float w=1,h=1;

	public Rectangle(){
		super();
		initRectangle();
	}

	public Rectangle(float width,float height){
		super();
		w=width;
		h=height;
		initRectangle();
	}

	private void initRectangle(){
		setVerticeArray(
			-w/2,-h/2,//Bottom-left
			w/2,-h/2,//Bottom-right
			w/2,h/2,//Top-right
			-w/2,h/2//Top-left
		);
		setTextureArray(
			0.0f, 1.0f,//Bottom-left
			1.0f, 1.0f,//Bottom-right
			1.0f, 0.0f,//Top-right
			0.0f, 0.0f//Top-left
		);
		setIndiceArray(new short[]{
			0,1,2,
			0,2,3
		});
	}

	public void setSize(float width,float height){
		w=width;
		h=height;
		initRectangle();
	}

	public float getWidth(){return w;}
	public float getHeight(){return h;}
}
