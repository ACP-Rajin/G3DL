/*
 * Copyright 2025 The G3DL Project Developers
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

#include "../../include/math/Math.hpp"

namespace g3dl_math{
  double sigmoid(double x){
    if(x<=-6.9067547786485534722)return 0.0;
    return 1.0/(1.0+std::exp(-x));
  }

  double tanh(double x){return std::tanh(x);}

  double tanhDerivative(double x){
    double t=std::tanh(x);
    return 1.0-t*t;
  }

  double sigmoidDerivative(double x){
    double sx=sigmoid(x);
    return sx*(1.0-sx);
  }

  double random(double min,double max){return((double)rand()/RAND_MAX)*(max-min)+min;}

  // Equality for vectors
  bool equals(const Vector2f& a,const Vector2f& b){return a.x==b.x&&a.y==b.y;}
  bool equals(const Vector3f& a,const Vector3f& b){return a.x==b.x&&a.y==b.y&&a.z==b.z;}
  bool equals(const Vector4f& a,const Vector4f& b){return a.x==b.x&&a.y==b.y&&a.z==b.z&&a.w==b.w;}

  bool equals(const Vector2i& a,const Vector2i& b){return a.x==b.x&&a.y==b.y;}
  bool equals(const Vector3i& a,const Vector3i& b){return a.x==b.x&&a.y==b.y&&a.z==b.z;}
  bool equals(const Vector4i& a,const Vector4i& b){return a.x==b.x&&a.y==b.y&&a.z==b.z&&a.w==b.w;}

  // Distance functions
  double distance(const Vector2f& a,const Vector2f& b){
    double dx=b.x-a.x,dy=b.y-a.y;
    return std::sqrt(dx*dx+dy*dy);
  }
  double distance(const Vector3f& a,const Vector3f& b){
    double dx=b.x-a.x,dy=b.y-a.y,dz=b.z-a.z;
    return std::sqrt(dx*dx+dy*dy+dz*dz);
  }
  double distance(const Vector4f& a,const Vector4f& b){
    double dx=b.x-a.x,dy=b.y-a.y,dz=b.z-a.z,dw=b.w-a.w;
    return std::sqrt(dx*dx+dy*dy+dz*dz+dw*dw);
  }

  int distance(const Vector2i& a,const Vector2i& b){
    int dx=b.x-a.x,dy=b.y-a.y;
    return std::sqrt(dx*dx+dy*dy);
  }
  int distance(const Vector3i& a,const Vector3i& b){
    int dx=b.x-a.x,dy=b.y-a.y,dz=b.z-a.z;
    return std::sqrt(dx*dx+dy*dy+dz*dz);
  }
  int distance(const Vector4i& a,const Vector4i& b){
    int dx=b.x-a.x,dy=b.y-a.y,dz=b.z-a.z,dw=b.w-a.w;
    return std::sqrt(dx*dx+dy*dy+dz*dz+dw*dw);
  }

  // Normalize vectors in place
  void normalize(Vector2f& vec2){
    double len=std::sqrt(vec2.x*vec2.x+vec2.y*vec2.y);
    if(len>0.0){
      float inv=static_cast<float>(1.0/len);
      vec2.x*=inv;
      vec2.y*=inv;
    }
  }
  void normalize(Vector3f& vec3){
    double len=std::sqrt(vec3.x*vec3.x+vec3.y*vec3.y+vec3.z*vec3.z);
    if(len>0.0){
      float inv=static_cast<float>(1.0/len);
      vec3.x*=inv;
      vec3.y*=inv;
      vec3.z*=inv;
    }
  }
  void normalize(Vector4f& vec4){
    double len=std::sqrt(vec4.x*vec4.x+vec4.y*vec4.y+vec4.z*vec4.z+vec4.w*vec4.w);
    if(len>0.0){
      float inv=static_cast<float>(1.0/len);
      vec4.x*=inv;
      vec4.y*=inv;
      vec4.z*=inv;
      vec4.w*=inv;
    }
  }

  void normalize(Vector2i& vec2){
    int len=std::sqrt(vec2.x*vec2.x+vec2.y*vec2.y);
    if(len>0.0){
      int inv=static_cast<int>(1.0/len);
      vec2.x*=inv;
      vec2.y*=inv;
    }
  }
  void normalize(Vector3i& vec3){
    int len=std::sqrt(vec3.x*vec3.x+vec3.y*vec3.y+vec3.z*vec3.z);
    if(len>0.0){
      int inv=static_cast<int>(1.0/len);
      vec3.x*=inv;
      vec3.y*=inv;
      vec3.z*=inv;
    }
  }
  void normalize(Vector4i& vec4){
    int len=std::sqrt(vec4.x*vec4.x+vec4.y*vec4.y+vec4.z*vec4.z+vec4.w*vec4.w);
    if(len>0.0){
      int inv=static_cast<int>(1.0/len);
      vec4.x*=inv;
      vec4.y*=inv;
      vec4.z*=inv;
      vec4.w*=inv;
    }
  }

  // Vector addition
  Vector2f add(const Vector2f& a,const Vector2f& b){return Vector2f(a.x+b.x,a.y+b.y);}
  Vector3f add(const Vector3f& a,const Vector3f& b){return Vector3f(a.x+b.x,a.y+b.y,a.z+b.z);}
  Vector4f add(const Vector4f& a,const Vector4f& b){return Vector4f(a.x+b.x,a.y+b.y,a.z+b.z,a.w+b.w);}

  Vector2i add(const Vector2i& a,const Vector2i& b){return Vector2i(a.x+b.x,a.y+b.y);}
  Vector3i add(const Vector3i& a,const Vector3i& b){return Vector3i(a.x+b.x,a.y+b.y,a.z+b.z);}
  Vector4i add(const Vector4i& a,const Vector4i& b){return Vector4i(a.x+b.x,a.y+b.y,a.z+b.z,a.w+b.w);}

  // Vector subtraction

  Vector2f subtract(const Vector2f& a,const Vector2f& b){return Vector2f(a.x-b.x,a.y-b.y);}
  Vector3f subtract(const Vector3f& a,const Vector3f& b){return Vector3f(a.x-b.x,a.y-b.y,a.z-b.z);}
  Vector4f subtract(const Vector4f& a,const Vector4f& b){return Vector4f(a.x-b.x,a.y-b.y,a.z-b.z,a.w-b.w);}

  Vector2i subtract(const Vector2i& a,const Vector2i& b){return Vector2i(a.x-b.x,a.y-b.y);}
  Vector3i subtract(const Vector3i& a,const Vector3i& b){return Vector3i(a.x-b.x,a.y-b.y,a.z-b.z);}
  Vector4i subtract(const Vector4i& a,const Vector4i& b){return Vector4i(a.x-b.x,a.y-b.y,a.z-b.z,a.w-b.w);}

  // Matrix multiplication
  Matrix2f multiply(const Matrix2f& a,const Matrix2f& b){
    Matrix2f r;
    for(int i=0;i<2;++i){
      float r0=0,r1=0;
      for(int j=0;j<2;++j){
        float v=a.getValue(i,j);
        r0+=b.getValue(j,0)*v;
        r1+=b.getValue(j,1)*v;
      }
      r.setValue(i,0,r0);
      r.setValue(i,1,r1);
    }
    return r;
  }
  Matrix3f multiply(const Matrix3f& a,const Matrix3f& b){
    Matrix3f r;
    for(int i=0;i<3;++i){
      float r0=0,r1=0,r2=0;
      for(int j=0;j<3;++j){
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
  Matrix4f multiply(const Matrix4f& a,const Matrix4f& b){
    Matrix4f r;
    for(int i=0;i<4;++i){
      float r0=0,r1=0,r2=0,r3=0;
      for(int j=0;j<4;++j){
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

  Matrix2i multiply(const Matrix2i& a,const Matrix2i& b){
    Matrix2i r;
    for(int i=0;i<2;++i){
      int r0=0,r1=0;
      for(int j=0;j<2;++j){
        int v=a.getValue(i,j);
        r0+=b.getValue(j,0)*v;
        r1+=b.getValue(j,1)*v;
      }
      r.setValue(i,0,r0);
      r.setValue(i,1,r1);
    }
    return r;
  }
  Matrix3i multiply(const Matrix3i& a,const Matrix3i& b){
    Matrix3i r;
    for(int i=0;i<3;++i){
      int r0=0,r1=0,r2=0;
      for(int j=0;j<3;++j){
        int v=a.getValue(i,j);
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
  Matrix4i multiply(const Matrix4i& a,const Matrix4i& b){
    Matrix4i r;
    for(int i=0;i<4;++i){
      int r0=0,r1=0,r2=0,r3=0;
      for(int j=0;j<4;++j){
        int v=a.getValue(i,j);
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

  // Vector multiply
  Vector2f multiply(const Vector2f& vec2,float scalar){return Vector2f(vec2.x*scalar,vec2.y*scalar);}
  Vector3f multiply(const Vector3f& vec3,float scalar){return Vector3f(vec3.x*scalar,vec3.y*scalar,vec3.z*scalar);}
  Vector4f multiply(const Vector4f& vec4,float scalar){return Vector4f(vec4.x*scalar,vec4.y*scalar,vec4.z*scalar,vec4.w *scalar);}

  Vector2i multiply(const Vector2i& vec2,int scalar){return Vector2i(vec2.x*scalar,vec2.y*scalar);}
  Vector3i multiply(const Vector3i& vec3,int scalar){return Vector3i(vec3.x*scalar,vec3.y*scalar,vec3.z*scalar);}
  Vector4i multiply(const Vector4i& vec4,int scalar){return Vector4i(vec4.x*scalar,vec4.y*scalar,vec4.z*scalar,vec4.w *scalar);}

  // Vector divide by scalar (throws assert if zero)

  Vector2f divide(const Vector2f& vec2,float scalar){
    assert(scalar!=0.0f&&"Division by zero");
    float inv=1.0f/scalar;
    return Vector2f(vec2.x*inv,vec2.y*inv);
  }
  Vector3f divide(const Vector3f& vec3,float scalar){
    assert(scalar!=0.0f&&"Division by zero");
    float inv=1.0f/scalar;
    return Vector3f(vec3.x*inv,vec3.y*inv,vec3.z*inv);
  }
  Vector4f divide(const Vector4f& vec4,float scalar){
    assert(scalar!=0.0f&&"Division by zero");
    float inv=1.0f/scalar;
    return Vector4f(vec4.x*inv,vec4.y*inv,vec4.z*inv,vec4.w*inv);
  }

  Vector2i divide(const Vector2i& vec2,int scalar){
    assert(scalar!=0.0f&&"Division by zero");
    int inv=1.0f/scalar;
    return Vector2i(vec2.x*inv,vec2.y*inv);
  }
  Vector3i divide(const Vector3i& vec3,int scalar){
    assert(scalar!=0.0f&&"Division by zero");
    int inv=1.0f/scalar;
    return Vector3i(vec3.x*inv,vec3.y*inv,vec3.z*inv);
  }
  Vector4i divide(const Vector4i& vec4,int scalar){
    assert(scalar!=0.0f&&"Division by zero");
    int inv=1.0f/scalar;
    return Vector4i(vec4.x*inv,vec4.y*inv,vec4.z*inv,vec4.w*inv);
  }
}
