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

#include "../../include/math/Matrix3f.hpp"

namespace g3dl_math{
  Matrix3f::Matrix3f(){identity();}
  Matrix3f::Matrix3f(std::initializer_list<float>values){
    assert(values.size()==9);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix3f::identity(){val={1,0,0,0,1,0,0,0,1};}

  void Matrix3f::setValues(std::initializer_list<float>values){
    assert(values.size()==9);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix3f::setValue(int x,int y,float v){val[x*3+y]=v;}
  float Matrix3f::getValue(int x,int y)const{return val[x*3+y];}

  const float* Matrix3f::data()const{return val.data();}
}
