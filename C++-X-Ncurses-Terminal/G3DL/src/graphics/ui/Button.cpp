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

#include "../../../include/graphics/ui/Button.hpp"

Button::Button(){
  rect.setColorPairID(colorPairID_m);
  rect.setFillCharacter(" ");
  rect.setHasBorder(true);

  size_m.set(1);
  highlighted_m=false;
}
Button::Button(const Vector2i& position,const Vector2i& size,const std::string& text):rect(position,size){
  rect.setColorPairID(colorPairID_m);
  rect.setFillCharacter(" ");
  rect.setHasBorder(true);

  pos_m.set(position);
  size_m.set(size);
  highlighted_m=false;
  label=text;
}

void Button::draw(WINDOW* window){
  int startX=pos_m.x;
  int startY=pos_m.y;
  int width=size_m.x;
  int height=size_m.y;
  rect.setPosition(pos_m);
  rect.setSize(size_m);
  // rect.setColor(borderColor);

  rect.draw(window);

  // --- Draw label ---
  wattron(window,COLOR_PAIR(colorPairID_m));
  if(highlighted_m)wattron(window,A_REVERSE);

  std::string clipped=label.substr(0,width-2); // prevent overflow
  int text_x=startX+(width-(int)clipped.size())/2;
  int text_y=startY+height/2;

  wattron(window,COLOR_PAIR(colorPairID_m));
  mvwprintw(window,text_y,text_x,"%s",clipped.c_str());
  wattroff(window,COLOR_PAIR(colorPairID_m));

  if(highlighted_m)wattroff(window,A_REVERSE);
  wattroff(window,COLOR_PAIR(colorPairID_m));
}

void Button::setLabel(const std::string &label){this->label=label;}

std::string Button::getLabel()const{return label;}
Rectangle Button::getRectangle()const{return rect;}
