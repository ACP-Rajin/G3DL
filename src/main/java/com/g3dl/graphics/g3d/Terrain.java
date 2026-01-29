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

import android.graphics.*;
import com.g3dl.graphics.*;
import com.g3dl.graphics.g3d.*;
import com.g3dl.math.*;

public class Terrain extends Model{
	private Texture tbm;
	private int r,c;
	private float wX,wZ,m;

	public Terrain(){
		tbm=new Texture(null);
		generate(1,1,1,1,1);
	}
	public Terrain(int rows,int columns,float widthX,float widthZ,float magnitude){
		tbm=new Texture(null);
		generate(rows,columns,widthX,widthZ,magnitude);
	}
	public Terrain(int rows,int columns,float widthX,float widthZ,Texture heightMap,float magnitude){
		tbm=heightMap;
		generate(rows,columns,widthX,widthZ,magnitude);
	}

	public void generate(int rows,int columns,float widthX,float widthZ,float magnitude){
		if(r!=rows||c!=columns||wX!=widthX||wZ!=widthZ||m!=magnitude){
			init(rows,columns,widthX,widthZ,magnitude);
			r=rows;c=columns;wX=widthX;wZ=widthZ;m=magnitude;
		}
	}

	private void init(int rows,int columns,float widthX,float widthZ,float magnitude){
		Bitmap hbm=tbm.getBitmap();
		int vertexCount=(rows+1)*(columns+1),triangleCount=rows*columns*2,indicesCount=triangleCount*3,pointer=0,vertPointer=0,texPointer=0,hMWidth=(hbm==null)?0:hbm.getWidth(),hMHeight=(hbm==null)?0:hbm.getHeight();

		float[]vertices=new float[vertexCount*3],texturs=new float[vertexCount*2],normals=new float[vertexCount*3];
        short[]indices=new short[indicesCount];

		Vector2f d=new Vector2f(widthX/columns,widthZ/rows);

		for(int i=0;i<=rows;i++){
			float rowFactor=(float)i/rows;
			for(int j=0;j<=columns;j++){
				float columnFactor=(float)j/columns;
				Vector2f points=new Vector2f(j*d.x-widthX/2,i*d.y-widthZ/2);

				float height=0;
				if(hbm!=null){
					Vector2f map=new Vector2f(Math.round(columnFactor*(hMWidth-1)),Math.round(rowFactor*(hMHeight-1)));
					int pixel=hbm.getPixel((int)map.x,(int)map.y);
					height=((pixel>>16)&0xff)/255.0f*magnitude;
				}

				vertices[vertPointer*3]=points.x;
				vertices[vertPointer*3+1]=height;
				vertices[vertPointer*3+2]=points.y;

				normals[vertPointer*3]=0;
				normals[vertPointer*3+1]=1;
				normals[vertPointer*3+2]=0;

				texturs[texPointer*2]=columnFactor;
				texturs[texPointer*2+1]=rowFactor;

				vertPointer++;
				texPointer++;
			}
		}

		for(int z=0;z<rows;z++){
			for(int x=0;x<columns;x++){
				int topLeft=(z*(columns+1))+x,topRight=topLeft+1,bottomLeft=((z+1)*(columns+1))+x,bottomRight=bottomLeft+1;

				indices[pointer++]=(short)topLeft;
				indices[pointer++]=(short)bottomLeft;
				indices[pointer++]=(short)topRight;

				indices[pointer++]=(short)topRight;
				indices[pointer++]=(short)bottomLeft;
				indices[pointer++]=(short)bottomRight;
			}
		}

		setVerticeArray(vertices);
		setTextureArray(texturs);
		setNormalArray(normals);
		setIndiceArray(indices);
	}

	public void setHeightMap(Texture heightMap){
		if(tbm.getBitmap()!=null&&tbm.getBitmap().sameAs(heightMap.getBitmap()))return;
		tbm=heightMap;
		generate(r,c,wX,wZ,m);
	}

	public Texture getHeightMap(){return tbm;}
}
