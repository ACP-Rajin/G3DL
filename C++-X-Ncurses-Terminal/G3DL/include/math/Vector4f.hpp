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
  struct Vector4f{
    static const Vector4f zero;
    float x=0,y=0,z=0,w=0;

    constexpr Vector4f();
    constexpr Vector4f(float xyzw);
    constexpr Vector4f(float x,float y,float z,float w);

    constexpr std::array<float,4>toArray()const;

    void set(float x,float y,float z,float w);
    void set(const Vector4f& vec4);
    void set(float xyzw);
  };
}
