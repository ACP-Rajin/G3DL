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

#include "../../include/math/Matrix2i.hpp"

namespace g3dl_math{
  Matrix2i::Matrix2i(){identity();}
  Matrix2i::Matrix2i(std::initializer_list<int>values){
    assert(values.size()==4);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix2i::identity(){val={1,0,0,1};}

  void Matrix2i::setValues(std::initializer_list<int>values){
    assert(values.size()==4);
    std::copy(values.begin(),values.end(),val.begin());
  }

  void Matrix2i::setValue(int x,int y,int v){val[x*2+y]=v;}
  int Matrix2i::getValue(int x,int y)const{return val[x*2+y];}

  const int* Matrix2i::data()const{return val.data();}
}
