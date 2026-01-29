/*
 * Copyright 2026 The G3DL Project Developers
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

#include "../../include/graphics/Screen.hpp"

Screen::Screen(){}
Screen::Screen(uint32_t width,uint32_t height){
  m_width=width;
  m_height=height;
}

void Screen::set(const Screen& screen){
  m_width=screen.getWidth();
  m_height=screen.getHeight();
}
void Screen::set(uint32_t width,uint32_t height){
  m_width=width;
  m_height=height;
}

void Screen::setWidth(uint32_t width){m_width=width;}
void Screen::setHeight(uint32_t height){m_height=height;}

// Don't Mind this
uint32_t& Screen::setWidth(){return m_width;}
uint32_t& Screen::setHeight(){return m_height;}

uint32_t Screen::getWidth()const{return m_width;}
uint32_t Screen::getHeight()const{return m_height;}
