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
  struct Vector3i{
    static const Vector3i zero;
    int x=0,y=0,z=0;

    constexpr Vector3i();
    constexpr Vector3i(int xyz);
    constexpr Vector3i(int x,int y,int z);

    constexpr std::array<int,3>toArray()const;

    void set(int x,int y,int z);
    void set(const Vector3i& vec3);
    void set(int xyz);
  };
}
