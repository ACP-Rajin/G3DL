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

#include "../../include/math/Matrix4f.hpp"

namespace g3dl_math{
  Matrix4f::Matrix4f(){identity();}
  Matrix4f::Matrix4f(std::initializer_list<float>values){
    assert(values.size()==16);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix4f::identity(){val={1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};}

  void Matrix4f::setValues(std::initializer_list<float>values){
    assert(values.size()==16);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix4f::setValue(int x,int y,float v){val[x*4+y]=v;}
  float Matrix4f::getValue(int x,int y)const{return val[x*4+y];}

  const float* Matrix4f::data()const{return val.data();}
}
