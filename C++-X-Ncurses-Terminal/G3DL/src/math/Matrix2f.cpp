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

#include "../../include/math/Matrix2f.hpp"

namespace g3dl_math{
  Matrix2f::Matrix2f(){identity();}
  Matrix2f::Matrix2f(std::initializer_list<float>values){
    assert(values.size()==4);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix2f::identity(){val={1,0,0,1};}

  void Matrix2f::setValues(std::initializer_list<float>values){
    assert(values.size()==4);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix2f::setValue(int x,int y,float v){val[x*2+y]=v;}
  float Matrix2f::getValue(int x,int y)const{return val[x*2+y];}

  const float* Matrix2f::data()const{return val.data();}
}
