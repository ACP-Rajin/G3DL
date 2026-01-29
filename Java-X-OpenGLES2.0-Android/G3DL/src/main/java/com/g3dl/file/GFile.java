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

package com.g3dl.file;

import java.io.*;
import java.nio.ByteBuffer;

public class GFile{
	public static String localPath,externalFilesPath;
	private InputStream is;
	private ByteBuffer bb;
	private byte[]b;
	private String s;

	public GFile(InputStream inputStream){redirect(inputStream);}
	public GFile(String path){redirect(path);}
	public GFile(InputStream inputStream,int chunkSize){redirect(inputStream,chunkSize);}
	public GFile(String path,int chunkSize){redirect(path,chunkSize);}

	public void redirect(InputStream inputStream){
		is=inputStream;
		init(0);
	}
	public void redirect(String path){
		try{
			is=new FileInputStream(path);
			init(0);
		}catch(FileNotFoundException e){e.printStackTrace();}
	}
	public void redirect(InputStream inputStream,int chunkSize){
		is=inputStream;
		init(chunkSize);
	}
	public void redirect(String path,int chunkSize){
		try{
			is=new FileInputStream(path);
			init(chunkSize);
		}catch(FileNotFoundException e){e.printStackTrace();}
	}

	private void init(int cs){
		b=readAsBytes(is,cs);
		bb=ByteBuffer.wrap(b);
	}

	public InputStream getInputStream(){return new ByteArrayInputStream(b);}

	public ByteBuffer getByteBuffer(){return bb;}

	public static byte[]readAsBytes(String path){return readAsBytes(path,0);}
	public static byte[]readAsBytes(String path,int chunkSize){
		try{
			return readAsBytes(new FileInputStream(path),chunkSize);
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return null;
		}
	}
	public static byte[]readAsBytes(InputStream inputStream){return readAsBytes(inputStream,0);}
	public static byte[]readAsBytes(InputStream inputStream,int chunkSize){
		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
		byte[]data=new byte[chunkSize!=0?chunkSize:16384];
		int nRead;
		try{
			while((nRead=inputStream.read(data,0,data.length))!=-1)buffer.write(data,0,nRead);
			return buffer.toByteArray();
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}finally{try{inputStream.close();}catch(IOException e){e.printStackTrace();}}
	}

	public byte[]getBytes(){return b;}

	public static String readAsString(String path){
		try{
			return readAsString(new FileInputStream(path));
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return null;
		}
	}
	public static String readAsString(InputStream inputStream){
		StringBuilder content=new StringBuilder();
		BufferedReader br=null;
		try{
			br=new BufferedReader(new InputStreamReader(inputStream));
			char[]buffer=new char[8192];
			int numRead;
			while((numRead=br.read(buffer,0,buffer.length))!=-1)content.append(buffer,0,numRead);
		}catch(IOException e){e.printStackTrace();}finally{
			if(br!=null)try{br.close();}catch(IOException e){e.printStackTrace();}
			try{inputStream.close();}catch(IOException e){e.printStackTrace();}
		}
		return content.toString();
	}

	public static void writeToFile(String path,String content){
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(path));
			bw.write(content);
			bw.close();
		}catch(IOException e){}
	}
	public static void writeToFile(String path,byte[] content){
		try{
			FileOutputStream fos=new FileOutputStream(path);
			fos.write(content);
			fos.close();
		}catch(IOException e){}
	}

	public String readString(){
		if(s==null)s=readAsString(getInputStream());
		return s;
	}
}
