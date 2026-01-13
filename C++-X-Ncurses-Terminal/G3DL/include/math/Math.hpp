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

#pragma once

#include <cmath>
#include <cassert>

#include "Vector2f.hpp"
#include "Vector3f.hpp"
#include "Vector4f.hpp"

#include "Matrix2f.hpp"
#include "Matrix3f.hpp"
#include "Matrix4f.hpp"

#include "Vector2i.hpp"
#include "Vector3i.hpp"
#include "Vector4i.hpp"

#include "Matrix2i.hpp"
#include "Matrix3i.hpp"
#include "Matrix4i.hpp"

namespace g3dl_math{
  inline double sigmoid(double x);

  inline double tanh(double x);

  inline double tanhDerivative(double x);

  inline double sigmoidDerivative(double x);

  inline double random(double min,double max);

  // Equality for vectors
  inline bool equals(const Vector2f& a,const Vector2f& b);
  inline bool equals(const Vector3f& a,const Vector3f& b);
  inline bool equals(const Vector4f& a,const Vector4f& b);

  inline bool equals(const Vector2i& a,const Vector2i& b);
  inline bool equals(const Vector3i& a,const Vector3i& b);
  inline bool equals(const Vector4i& a,const Vector4i& b);

  // Distance functions
  inline double distance(const Vector2f& a,const Vector2f& b);
  inline double distance(const Vector3f& a,const Vector3f& b);
  inline double distance(const Vector4f& a,const Vector4f& b);

  inline int distance(const Vector2i& a,const Vector2i& b);
  inline int distance(const Vector3i& a,const Vector3i& b);
  inline int distance(const Vector4i& a,const Vector4i& b);

  // Normalize vectors in place
  inline void normalize(Vector2f& vec2);
  inline void normalize(Vector3f& vec3);
  inline void normalize(Vector4f& vec4);

  inline void normalize(Vector2i& vec2);
  inline void normalize(Vector3i& vec3);
  inline void normalize(Vector4i& vec4);

  // Vector addition
  inline Vector2f add(const Vector2f& a,const Vector2f& b);
  inline Vector3f add(const Vector3f& a,const Vector3f& b);
  inline Vector4f add(const Vector4f& a,const Vector4f& b);

  inline Vector2i add(const Vector2i& a,const Vector2i& b);
  inline Vector3i add(const Vector3i& a,const Vector3i& b);
  inline Vector4i add(const Vector4i& a,const Vector4i& b);

  // Vector subtraction
  inline Vector2f subtract(const Vector2f& a,const Vector2f& b);
  inline Vector3f subtract(const Vector3f& a,const Vector3f& b);
  inline Vector4f subtract(const Vector4f& a,const Vector4f& b);

  inline Vector2i subtract(const Vector2i& a,const Vector2i& b);
  inline Vector3i subtract(const Vector3i& a,const Vector3i& b);
  inline Vector4i subtract(const Vector4i& a,const Vector4i& b);

  // Matrix multiplication
  inline Matrix2f multiply(const Matrix2f& a,const Matrix2f& b);
  inline Matrix3f multiply(const Matrix3f& a,const Matrix3f& b);
  inline Matrix4f multiply(const Matrix4f& a,const Matrix4f& b);

  inline Matrix2i multiply(const Matrix2i& a,const Matrix2i& b);
  inline Matrix3i multiply(const Matrix3i& a,const Matrix3i& b);
  inline Matrix4i multiply(const Matrix4i& a,const Matrix4i& b);

  // Vector multiply
  inline Vector2f multiply(const Vector2f& vec2,float scalar);
  inline Vector3f multiply(const Vector3f& vec3,float scalar);
  inline Vector4f multiply(const Vector4f& vec4,float scalar);

  inline Vector2i multiply(const Vector2i& vec2,int scalar);
  inline Vector3i multiply(const Vector3i& vec3,int scalar);
  inline Vector4i multiply(const Vector4i& vec4,int scalar);

  // Vector divide by scalar  
  inline Vector2f divide(const Vector2f& vec2,float scalar);  
  inline Vector3f divide(const Vector3f& vec3,float scalar);  
  inline Vector4f divide(const Vector4f& vec4,float scalar);  

  inline Vector2i divide(const Vector2i& vec2,int scalar);  
  inline Vector3i divide(const Vector3i& vec3,int scalar);  
  inline Vector4i divide(const Vector4i& vec4,int scalar);  

  // Vector divide by scalar
  inline Vector2f divide(const Vector2f& vec2,float scalar);
  inline Vector3f divide(const Vector3f& vec3,float scalar);
  inline Vector4f divide(const Vector4f& vec4,float scalar);

  inline Vector2i divide(const Vector2i& vec2,int scalar);
  inline Vector3i divide(const Vector3i& vec3,int scalar);
  inline Vector4i divide(const Vector4i& vec4,int scalar);
}
