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

#include "../../include/math/Vector3f.hpp"

namespace g3dl_math{
  const Vector3f Vector3f::zero{0,0,0};

  constexpr Vector3f::Vector3f()=default;
  constexpr Vector3f::Vector3f(float xyz):x(xyz),y(xyz),z(xyz){}
  constexpr Vector3f::Vector3f(float x,float y,float z){this->x=x;this->y=y;this->z=z;}

  constexpr std::array<float,3>Vector3f::toArray()const{return{x,y,z};}

  void Vector3f::set(float x,float y,float z){this->x=x;this->y=y;this->z=z;}
  void Vector3f::set(const Vector3f& vec3){x=vec3.x;y=vec3.y;z=vec3.z;}
  void Vector3f::set(float xyz){x=xyz;y=xyz;z=xyz;}
}
