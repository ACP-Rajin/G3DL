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
  struct Matrix3i{
    std::array<int,9>val{};

    Matrix3i();
    Matrix3i(std::initializer_list<int>values);

    void identity();

    void setValues(std::initializer_list<int>values);

    void setValue(int x,int y,int v);
    int getValue(int x,int y)const;

    const int* data()const;
  };
}
