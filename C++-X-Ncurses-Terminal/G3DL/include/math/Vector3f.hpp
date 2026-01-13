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

#include <array>
#include <cassert>

namespace g3dl_math{
  struct Vector3f{
    static const Vector3f zero;
    float x=0,y=0,z=0;

    constexpr Vector3f();
    constexpr Vector3f(float xyz);
    constexpr Vector3f(float x,float y,float z);

    constexpr std::array<float,3>toArray()const;

    void set(float x,float y,float z);
    void set(const Vector3f& vec3);
    void set(float xyz);
  };
}
