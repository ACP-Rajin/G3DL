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

#include "../../include/math/Vector4i.hpp"

namespace g3dl_math{
  const Vector4i Vector4i::zero{0,0,0,0};

  constexpr Vector4i::Vector4i()=default;
  constexpr Vector4i::Vector4i(int xyzw):x(xyzw),y(xyzw),z(xyzw),w(xyzw){}
  constexpr Vector4i::Vector4i(int x,int y,int z,int w){this->x=x;this->y=y;this->z=z;this->w=w;}

  constexpr std::array<int,4>Vector4i::toArray()const{return{x,y,z,w};}

  void Vector4i::set(int x,int y,int z,int w){this->x=x;this->y=y;this->z=z;this->w=w;}
  void Vector4i::set(const Vector4i& vec4){x=vec4.x;y=vec4.y;z=vec4.z;w=vec4.w;}
  void Vector4i::set(int xyzw){x=xyzw;y=xyzw;z=xyzw;w=xyzw;}
}
