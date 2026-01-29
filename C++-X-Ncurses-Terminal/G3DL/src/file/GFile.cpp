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

#include "../../include/file/GFile.hpp"

std::string GFile::localPath="";
std::string GFile::externalFilesPath="";

GFile::GFile() : b{},s{} {}
GFile::GFile(std::istream& inputStream){redirect(inputStream);}
GFile::GFile(const std::string& path){redirect(path);}
GFile::GFile(std::istream& inputStream,int chunkSize){redirect(inputStream,chunkSize);}
GFile::GFile(const std::string& path,int chunkSize){redirect(path,chunkSize);}

void GFile::redirect(std::istream& inputStream){redirect(inputStream,0);}
void GFile::redirect(const std::string& path){redirect(path,0);}
void GFile::redirect(std::istream& inputStream,int chunkSize){
  try{
    b=readAsBytes(inputStream,chunkSize);
  }catch(const std::exception& e){
    std::cerr << e.what() << std::endl;
  }
}
void GFile::redirect(const std::string& path,int chunkSize){
  try{
    std::ifstream file(path,std::ios::binary);
    if(!file)throw std::runtime_error("File not found");
    b=readAsBytes(file,chunkSize);
  }catch(const std::exception& e){
    std::cerr << e.what() << std::endl;
  }
}

std::unique_ptr<std::istream>GFile::getInputStream(){return std::make_unique<std::stringstream>(std::string(b.begin(),b.end()),std::ios::in|std::ios::binary);}

std::vector<char> GFile::readAsBytes(const std::string& path){return readAsBytes(path,0);}
std::vector<char> GFile::readAsBytes(const std::string& path,int chunkSize){
  try{
    std::ifstream file(path,std::ios::binary);
    if(!file)throw std::runtime_error("File not found");
    return readAsBytes(file,chunkSize);
  }catch(const std::exception& e){
    std::cerr << e.what() << std::endl;
    return{};
  }
}
std::vector<char> GFile::readAsBytes(std::istream& inputStream){return readAsBytes(inputStream,0);}
std::vector<char> GFile::readAsBytes(std::istream& inputStream,int chunkSize){
  std::vector<char>buffer;
  size_t size=(chunkSize!=0)?static_cast<size_t>(chunkSize):16384;
  std::vector<char> data(size);
  std::streamsize nRead;
  while((nRead=inputStream.read(data.data(),size).gcount())>0)buffer.insert(buffer.end(),data.begin(),data.begin()+nRead);
  return buffer;
}

const std::vector<char>& GFile::getBytes(){return b;}

std::string GFile::readAsString(const std::string& path){
  try{
    std::ifstream file(path);
    if(!file)throw std::runtime_error("File not found");
    return readAsString(file);
  }catch(const std::exception& e){
    std::cerr << e.what() << std::endl;
    return "";
  }
}
std::string GFile::readAsString(std::istream& inputStream){
  std::stringstream content;
  content << inputStream.rdbuf();
  return content.str();
}

void GFile::writeToFile(const std::string& path,const std::string& content){
  try{
    std::ofstream file(path);
    file << content;
  }catch(const std::exception&){}
}
void GFile::writeToFile(const std::string& path,const std::vector<char>& content){
  try{
    std::ofstream file(path,std::ios::binary);
    file.write(content.data(),content.size());
  }catch(const std::exception&){}
}

std::string GFile::readString(){
  if(s.empty())s=readAsString(*getInputStream());
  return s;
}
