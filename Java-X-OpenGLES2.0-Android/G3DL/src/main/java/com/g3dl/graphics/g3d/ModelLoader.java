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

import android.content.res.*;
import com.g3dl.math.*;
import java.io.*;
import java.util.*;

public class ModelLoader{
	public static Model loadFromOBJ(String externalPath){
		try{
			return OBJLoader(new FileReader(externalPath));
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}

	public static Model loadFromOBJ(String internalPath,AssetManager assetManager){
		try{
			return OBJLoader(new InputStreamReader(assetManager.open(internalPath)));
		}catch(IOException e){
			e.printStackTrace();
			return new Cube();
		}
	}

	private static Model OBJLoader(Reader obj)throws IOException{
		BufferedReader reader=new BufferedReader(obj);
		String line;
		ArrayList<Vector3f> vertices=new ArrayList<>(),normals=new ArrayList<>();
		ArrayList<Vector2f> textures=new ArrayList<>();
		ArrayList<Short> indices=new ArrayList<>();
		ArrayList<Vector3f> faces=new ArrayList<>();

		while((line=reader.readLine())!=null){
			String[]tokens=line.split("\\s+");
			switch(tokens[0]){
				case "v":
					vertices.add(new Vector3f(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2]),Float.parseFloat(tokens[3])));
					break;
				case "vt":
					textures.add(new Vector2f(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));
					break;
				case "vn":
					normals.add(new Vector3f(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2]),Float.parseFloat(tokens[3])));
					break;
				case "f":
					for(int i=1;i<tokens.length;i++)processFace(tokens[i],faces);
					/*processFace(tokens[1],faces);
					processFace(tokens[2],faces);
					processFace(tokens[3],faces);*/
					break;
			}
		}
		float[]vertexArr=new float[vertices.size()*3];
		int i=0;
		for(Vector3f pos:vertices){
			vertexArr[i*3]=pos.x;
			vertexArr[i*3+1]=pos.y;
			vertexArr[i*3+2]=pos.z;
			i++;
		}
		float[]texCoordArr=new float[vertices.size()*2];
		float[]normalArr=new float[vertices.size()*3];

		for(Vector3f face:faces){
			processVertex((int)face.x,(int)face.y,(int)face.z,textures,normals,indices,texCoordArr,normalArr);
		}

		short[]indicesArr=new short[indices.size()];
		for(int ii=0;ii<indices.size();ii++){
			indicesArr[ii]=indices.get(ii);
		}
		return new Model(vertexArr,texCoordArr,normalArr,indicesArr);
	}

	private static void processVertex(int pos,int texCoord,int normal,ArrayList<Vector2f>texCoordList,ArrayList<Vector3f>normalList,ArrayList<Short>indicesList,float[]texCoordArr,float[]normalArr){
		indicesList.add((short)pos);

		if(texCoord>=0){
			Vector2f texCoordVec=texCoordList.get(texCoord);
			texCoordArr[pos*2]=texCoordVec.x;
			texCoordArr[pos*2+1]=1-texCoordVec.y;
		}
		if(normal>=0){
			Vector3f normalVec=normalList.get(normal);
			normalArr[pos*3]=normalVec.x;
			normalArr[pos*3+1]=normalVec.y;
			normalArr[pos*3+2]=normalVec.z;
		}
	}

	private static void processFace(String token,ArrayList<Vector3f>faces){
		String[]lineToken=token.split("/");
		int length=lineToken.length;
		int pos=-1,coords=-1,normal=-1;
		pos=Integer.parseInt(lineToken[0])-1;
		if(length>1){
			String texCoord=lineToken[1];
			coords=texCoord.length()>0?Integer.parseInt(texCoord)-1:-1;
			if(length>2)normal=Integer.parseInt(lineToken[2])-1;
		}
		Vector3f faceVec=new Vector3f(pos,coords,normal);
		faces.add(faceVec);
	}
}
