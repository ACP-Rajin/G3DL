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

#include "../include/G3DActivity.hpp"

G3DActivity::G3DActivity() : m_renderer(std::make_unique<DefaultRenderer>()){G3D::renderer=m_renderer.get();}
G3DActivity::G3DActivity(std::unique_ptr<GRenderer> renderer) : m_renderer(std::move(renderer)){G3D::renderer=m_renderer.get();}

void G3DActivity::init(std::unique_ptr<GameInterface> gameInterface){
  m_gameInterface=std::move(gameInterface);
  if(m_gameInterface)m_renderer->init(*m_gameInterface);

  // start rendering
  G3D::running=true;
  m_renderer->onSurfaceCreated();
  m_renderer->onDrawFrame();
}

void G3DActivity::setRenderer(std::unique_ptr<GRenderer> renderer){
  m_renderer=std::move(renderer);

  if(m_renderer){
    G3D::renderer=m_renderer.get();
  }else{
    G3D::renderer=nullptr;
  }
}

void G3DActivity::onDespose(){
  G3D::running=false; 
  m_renderer->onDispose();
}
