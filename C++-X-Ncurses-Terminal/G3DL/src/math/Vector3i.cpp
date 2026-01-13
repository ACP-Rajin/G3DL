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

#include "../../include/math/Vector3i.hpp"

namespace g3dl_math{
  const Vector3i Vector3i::zero{0,0,0};

  constexpr Vector3i::Vector3i()=default;
  constexpr Vector3i::Vector3i(int xyz):x(xyz),y(xyz),z(xyz){}
  constexpr Vector3i::Vector3i(int x,int y,int z){this->x=x;this->y=y;this->z=z;}

  constexpr std::array<int,3>Vector3i::toArray()const{return{x,y,z};}

  void Vector3i::set(int x,int y,int z){this->x=x;this->y=y;this->z=z;}
  void Vector3i::set(const Vector3i& vec3){x=vec3.x;y=vec3.y;z=vec3.z;}
  void Vector3i::set(int xyz){x=xyz;y=xyz;z=xyz;}
}
