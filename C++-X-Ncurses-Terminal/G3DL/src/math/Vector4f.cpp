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

#include "../../include/math/Vector4f.hpp"

namespace g3dl_math{
  const Vector4f Vector4f::zero{0,0,0,0};

  constexpr Vector4f::Vector4f()=default;
  constexpr Vector4f::Vector4f(float xyzw):x(xyzw),y(xyzw),z(xyzw),w(xyzw){}
  constexpr Vector4f::Vector4f(float x,float y,float z,float w){this->x=x;this->y=y;this->z=z;this->w=w;}

  constexpr std::array<float,4>Vector4f::toArray()const{return{x,y,z,w};}

  void Vector4f::set(float x,float y,float z,float w){this->x=x;this->y=y;this->z=z;this->w=w;}
  void Vector4f::set(const Vector4f& vec4){x=vec4.x;y=vec4.y;z=vec4.z;w=vec4.w;}
  void Vector4f::set(float xyzw){x=xyzw;y=xyzw;z=xyzw;w=xyzw;}
}
