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

#include "../../include/math/Matrix3i.hpp"

namespace g3dl_math{
  Matrix3i::Matrix3i(){identity();}
  Matrix3i::Matrix3i(std::initializer_list<int>values){
    assert(values.size()==9);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix3i::identity(){val={1,0,0,0,1,0,0,0,1};}

  void Matrix3i::setValues(std::initializer_list<int>values){
    assert(values.size()==9);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix3i::setValue(int x,int y,int v){val[x*3+y]=v;}
  int Matrix3i::getValue(int x,int y)const{return val[x*3+y];}

  const int* Matrix3i::data()const{return val.data();}
}
