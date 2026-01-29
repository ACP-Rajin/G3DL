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

package com.g3dl.graphics;

import com.g3dl.*;

public class Screen{
	private int w=0,h=0;

	public Screen(){}
	public Screen(int width,int height){w=width;h=height;}

	public void setWidth(int width){w=width;}
	public void setHeight(int height){h=height;}

	public int getWidth(){return w;}
	public int getHeight(){return h;}
}
