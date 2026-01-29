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

package com.g3dl.math;

import com.g3dl.*;

public class Math2{
	public static double sigmoid(double x){return(x<=-6.9067547786485534722)?0:1/(1+Math.exp(-x));}

	public static double sigmoidDerivative(double x){
		double sigmoidX=sigmoid(x);
		return sigmoidX*(1.0f-sigmoidX);
	}

	public static double random(double max,double min){return(Math.random()*(max-min))+min;}

	public static boolean equals(Vector2f a,Vector2f b){return(a.x==b.x&&a.y==b.y);}
	public static boolean equals(Vector3f a,Vector3f b){return(a.x==b.x&&a.y==b.y&&a.z==b.z);}
	public static boolean equals(Vector4f a,Vector4f b){return(a.x==b.x&&a.y==b.y&&a.z==b.z&&a.w==b.w);}

	public static double distance(Vector2f a,Vector2f b){
		double v0=b.x-a.x,v1=b.y-a.y;
		return Math.sqrt(v0*v0+v1*v1);
	}
	public static double distance(Vector3f a,Vector3f b){
		double v0=b.x-a.x,v1=b.y-a.y,v2=b.z-a.z;
		return Math.sqrt(v0*v0+v1*v1+v2*v2);
	}
	public static double distance(Vector4f a,Vector4f b){
		double v0=b.x-a.x,v1=b.y-a.y,v2=b.z-a.z,v3=b.w-a.w;
		return Math.sqrt(v0*v0+v1*v1+v2*v2+v3*v3);
	}

	public static void normalize(Vector2f vec2){
		double length=Math.sqrt(vec2.x*vec2.x+vec2.y*vec2.y);
		if(length!=0.0){
			float s=1.0f/(float)length;
			vec2.x*=s;
			vec2.y*=s;
		}
	}
	public static void normalize(Vector3f vec3){
		double length=Math.sqrt(vec3.x*vec3.x+vec3.y*vec3.y+vec3.z*vec3.z);
		if(length!=0.0){
			float s=1.0f/(float)length;
			vec3.x*=s;
			vec3.y*=s;
			vec3.z*=s;
		}
	}
	public static void normalize(Vector4f vec4){
		double length=Math.sqrt(vec4.x*vec4.x+vec4.y*vec4.y+vec4.z*vec4.z+vec4.w*vec4.w);
		if(length!=0.0){
			double s=1.0/length;
			vec4.x*=s;
			vec4.y*=s;
			vec4.z*=s;
			vec4.w*=s;
		}
	}

	public static Vector2f add(Vector2f a,Vector2f b){return new Vector2f(a.x+b.x,a.y+b.y);}
	public static Vector3f add(Vector3f a,Vector3f b){return new Vector3f(a.x+b.x,a.y+b.y,a.z+b.z);}
	public static Vector4f add(Vector4f a,Vector4f b){return new Vector4f(a.x+b.x,a.y+b.y,a.z+b.z,a.w+b.w);}

	public static Vector2f subtract(Vector2f a,Vector2f b){return new Vector2f(a.x-b.x,a.y-b.y);}
	public static Vector3f subtract(Vector3f a,Vector3f b){return new Vector3f(a.x-b.x,a.y-b.y,a.z-b.z);}
	public static Vector4f subtract(Vector4f a,Vector4f b){return new Vector4f(a.x-b.x,a.y-b.y,a.z-b.z,a.w-b.w);}

	public static Matrix2f multiply(Matrix2f a,Matrix2f b){
		Matrix2f r=new Matrix2f();
		for(int i=0;i<2;i++){
			float r0=0;
			float r1=0;
			for(int j=0;j<2;j++){
				float v=a.getValue(i,j);
				r0+=b.getValue(j,0)*v;
				r1+=b.getValue(j,1)*v;
			}
			r.setValue(i,0,r0);
			r.setValue(i,1,r1);
		}
		return r;
	}
	public static Matrix3f multiply(Matrix3f a,Matrix3f b){
		Matrix3f r=new Matrix3f();
		for(int i=0;i<3;i++){
			float r0=0;
			float r1=0;
			float r2=0;
			for(int j=0;j<3;j++){
				float v=a.getValue(i,j);
				r0+=b.getValue(j,0)*v;
				r1+=b.getValue(j,1)*v;
				r2+=b.getValue(j,2)*v;
			}
			r.setValue(i,0,r0);
			r.setValue(i,1,r1);
			r.setValue(i,2,r2);
		}
		return r;
	}
	public static Matrix4f multiply(Matrix4f a,Matrix4f b){
		Matrix4f r=new Matrix4f();
		for(int i=0;i<4;i++){
			float r0=0;
			float r1=0;
			float r2=0;
			float r3=0;
			for(int j=0;j<4;j++){
				float v=a.getValue(i,j);
				r0+=b.getValue(j,0)*v;
				r1+=b.getValue(j,1)*v;
				r2+=b.getValue(j,2)*v;
				r3+=b.getValue(j,3)*v;
			}
			r.setValue(i,0,r0);
			r.setValue(i,1,r1);
			r.setValue(i,2,r2);
			r.setValue(i,3,r3);
		}
		return r;
	}

	public static Vector2f multiply(Vector2f vec2,float scalar){return new Vector2f(vec2.x*scalar,vec2.y*scalar);}
	public static Vector3f multiply(Vector3f vec3,float scalar){return new Vector3f(vec3.x*scalar,vec3.y*scalar,vec3.z*scalar);}
	public static Vector4f multiply(Vector4f vec4,float scalar){return new Vector4f(vec4.x*scalar,vec4.y*scalar,vec4.z*scalar,vec4.w*scalar);}

	public static Vector2f divide(Vector2f vec2,float scalar){
		if(scalar!=0){
			float invScalar=1.0f/scalar;
			return new Vector2f(vec2.x*invScalar,vec2.y*invScalar);
		}else{throw new ArithmeticException("Division by zero");}
	}
	public static Vector3f divide(Vector3f vec3,float scalar){
		if(scalar!=0){
			float invScalar=1.0f/scalar;
			return new Vector3f(vec3.x*invScalar,vec3.y*invScalar,vec3.z*invScalar);
		}else{throw new ArithmeticException("Division by zero");}
	}
	public static Vector4f divide(Vector4f vec4,float scalar){
		if(scalar!=0){
			float invScalar=1.0f/scalar;
			return new Vector4f(vec4.x*invScalar,vec4.y*invScalar,vec4.z*invScalar,vec4.w*invScalar);
		}else{throw new ArithmeticException("Division by zero");}
	}

	public static float pixelToDp(float px){return px/(G3D.context.getResources().getDisplayMetrics().densityDpi/160f);}
	public static float pixelToPt(float px){return px/(G3D.context.getResources().getDisplayMetrics().xdpi/72f);}
	public static float dpToPt(float dp){return pixelToPt(dpToPixel(dp));}
	public static float dpToPixel(float dp){return dp*(G3D.context.getResources().getDisplayMetrics().densityDpi/160f);}
	public static float ptToPixel(float pt){return pt*(G3D.context.getResources().getDisplayMetrics().xdpi/72f);}
	public static float ptToDp(float pt){return pixelToDp(ptToPixel(pt));}
}
