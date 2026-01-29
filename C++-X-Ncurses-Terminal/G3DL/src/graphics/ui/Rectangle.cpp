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

#include "../../../include/graphics/ui/Rectangle.hpp"

Rectangle::Rectangle(){
  size_m.set(1);
  highlighted_m=false;
  character="█";
  hasBorder=false;

  setBorderChars("┌","┐","└","┘","─","─","│","│");
}
Rectangle::Rectangle(const Vector2i& position,const Vector2i& size){
  pos_m.set(position);
  size_m.set(size);
  highlighted_m=false;
  character="█";
  hasBorder=false;

  setBorderChars("┌","┐","└","┘","─","─","│","│");
}

void Rectangle::draw(WINDOW* window){
  int startX=pos_m.x;
  int startY=pos_m.y;
  int width=size_m.x;
  int height=size_m.y;

  wattron(window,COLOR_PAIR(colorPairID_m));
  if(highlighted_m)wattron(window,A_REVERSE);

  // --- Draw with or without border ---
  if(hasBorder && width>=2 && height>=2){
    // Top
    mvwprintw(window,startY,startX,"%s",bord.tl.c_str());
    for(int i=1;i<width-1;i++)mvwprintw(window,startY,startX+i,"%s",bord.th.c_str());
    mvwprintw(window,startY,startX+width-1,"%s",bord.tr.c_str());

    // Middle rows
    for(int j=1;j<height-1;j++){
      mvwprintw(window,startY+j,startX,"%s",bord.lv.c_str());
      //fill inside rect
      if(character!=" " && character!="")
        for(int i=1;i<width-1;i++)
          mvwprintw(window,startY+j,startX+i,"%s",character.c_str());
      mvwprintw(window,startY+j,startX+width-1,"%s",bord.rv.c_str());
    }

    // Bottom
    mvwprintw(window,startY+height-1,startX,"%s",bord.bl.c_str());
    for(int i=1;i<width-1;i++)mvwprintw(window,startY+height-1,startX+i,"%s",bord.bh.c_str());
    mvwprintw(window,startY+height-1,startX+width-1,"%s",bord.br.c_str());

  }else{
    // No border: fill entire rect
    if(character!="")
      for(int j=0;j<height;j++)
        for(int i=0;i<width;i++)
          mvwprintw(window,startY+j,startX+i,"%s",character.c_str());
  }

  // --- Restore attributes ---
  if(highlighted_m)wattroff(window,A_REVERSE);
  wattroff(window,COLOR_PAIR(colorPairID_m));
}

void Rectangle::setFillCharacter(std::string fillCharacter){character=fillCharacter;}
void Rectangle::setHasBorder(bool hasBorder){this->hasBorder=hasBorder;}
void Rectangle::setBorderChars(const std::string& top_left,const std::string& top_right,const std::string& bottom_left,const std::string& bottom_right,const std::string& top_horizontal,const std::string& bottom_horizontal,const std::string& left_vertical,const std::string& right_vertical){
  bord.tl=top_left;
  bord.tr=top_right;
  bord.bl=bottom_left;
  bord.br=bottom_right;
  bord.th=top_horizontal;
  bord.bh=bottom_horizontal;
  bord.lv=left_vertical;
  bord.rv=right_vertical;
}
void Rectangle::setBorderChars(Border& border){
  bord.tl=border.tl;
  bord.tr=border.tr;
  bord.bl=border.bl;
  bord.br=border.br;
  bord.th=border.th;
  bord.bh=border.bh;
  bord.lv=border.lv;
  bord.rv=border.rv;
}

std::string Rectangle::getFillCharacter()const{return character;}
bool Rectangle::getHasBorder()const{return hasBorder;}
Rectangle::Border Rectangle::getBorder()const{return bord;}
