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

#include "../../include/graphics/DefaultRenderer.hpp"

void DefaultRenderer::init(const GameInterface& gameInterface){
  m_gameInterface=const_cast<GameInterface*>(&gameInterface);
}

void DefaultRenderer::onSurfaceCreated(){
  // Initiating Ncurses UI
  setlocale(LC_ALL,"");
  initscr();
  start_color();
  use_default_colors();
  noecho();
  cbreak();
  curs_set(0);
  keypad(stdscr,TRUE);

  timeout(50);

  getmaxyx(stdscr,G3D::screen.setHeight(),G3D::screen.setWidth());

  m_gameInterface->create();
}

void DefaultRenderer::onSurfaceChanged(){
  getmaxyx(stdscr,G3D::screen.setHeight(),G3D::screen.setWidth());
  wresize(stdscr,G3D::screen.setHeight(),G3D::screen.setWidth());
  mvwin(stdscr,0,0);

  m_gameInterface->resize(G3D::screen);
}

void DefaultRenderer::onDrawFrame(){
  while(G3D::running){
    werase(stdscr);

    getmaxyx(stdscr,G3D::screen.setHeight(),G3D::screen.setWidth());

    m_gameInterface->render();
    G3D::inputCharecter=wgetch(stdscr);

    wrefresh(stdscr);

    switch(G3D::inputCharecter){
      case KEY_RESIZE: // Refresh
        onSurfaceChanged();
        break;
    }
  }
}

void DefaultRenderer::onDispose(){
  m_gameInterface->onDespose();

  // Clear ncurses UI
  delwin(stdscr);
  endwin();
}
