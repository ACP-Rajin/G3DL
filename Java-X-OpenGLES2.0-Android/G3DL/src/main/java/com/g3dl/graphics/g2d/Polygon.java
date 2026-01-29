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

import java.nio.*;

public class Polygon extends Sprite{

	public Polygon(float...vertices){
		setVerticeArray(vertices);
		setIndiceArray(generateIndices(vertices.length/2));
		setTextureArray(generateUVs(vertices));
	}

	// Existing method to generate indices for a polygon
	private short[]generateIndices(int vertexCount){
		short[]indices=new short[(vertexCount-2)*3];
		for(int i=0;i<vertexCount-2;i++){
			indices[i*3]=0;
			indices[i*3+1]=(short)(i+1);
			indices[i*3+2]=(short)(i+2);
		}
		return indices;
	}

	// Existing method to generate UV coordinates for a polygon
	private float[]generateUVs(float[]vertices){
		float minX=Float.MAX_VALUE,maxX=Float.MIN_VALUE;
		float minY=Float.MAX_VALUE,maxY=Float.MIN_VALUE;

		// Find bounds
		for(int i=0;i<vertices.length;i+=2){
			if(vertices[i]<minX)minX=vertices[i];
			if(vertices[i]>maxX)maxX=vertices[i];
			if(vertices[i+1]<minY)minY=vertices[i+1];
			if(vertices[i+1]>maxY)maxY=vertices[i+1];
		}

		// Generate UVs
		float[]uvs=new float[vertices.length];
		for(int i=0;i<vertices.length;i+=2){
			uvs[i]=(vertices[i]-minX)/(maxX-minX);// U
			uvs[i+1]=1-((vertices[i+1]-minY)/(maxY-minY));// V
		}
		return uvs;
	}
}
