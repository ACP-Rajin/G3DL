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

#include "../../include/math/Vector2f.hpp"

namespace g3dl_math{
  const Vector2f Vector2f::zero{0,0};

  constexpr Vector2f::Vector2f()=default;
  constexpr Vector2f::Vector2f(float xy):x(xy),y(xy){}
  constexpr Vector2f::Vector2f(float x,float y){this->x=x;this->y=y;}

  constexpr std::array<float,2>Vector2f::toArray()const{return{x,y};}

  void Vector2f::set(float x,float y){this->x=x;this->y=y;}
  void Vector2f::set(const Vector2f& v){x=v.x;y=v.y;}
  void Vector2f::set(float xy){x=xy;y=xy;}
}
