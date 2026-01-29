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

package com.g3dl.graphics.ui;

import android.view.*;
import com.g3dl.*;
import com.g3dl.file.*;
import com.g3dl.graphics.*;
import com.g3dl.graphics.g2d.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import org.json.*;
import android.opengl.*;

public class Text extends UI{
  private Font font;
  private Texture texture;
  private String text="";
  private List<Sprite> glyphSprites=new ArrayList<>();

  public Text(Font font,Texture texture){
      this.font=font;
      this.texture=texture;
  }

  public void setText(String newText){
      this.text=newText;
      generateGlyphSprites();
  }

  private void generateGlyphSprites(){
    glyphSprites.clear();
    float xCursor=p.x;
    float yCursor=p.y;

    for(char c : text.toCharArray()){
      Font.Glyph glyph=font.glyphs.get(c);
      if(glyph==null)continue;

      Sprite glyphSprite=new Sprite();

      // Define quad vertices (relative to glyph position)
      float x0=xCursor + glyph.xOffset;
      float y0=yCursor - glyph.yOffset;
      float x1=x0 + glyph.width;
      float y1=y0 + glyph.height;

      float[]verticesArray={
        x0,y1, // Top-left
        x1,y1, // Top-right
        x1,y0, // Bottom-right
        x0,y0  // Bottom-left
      };

      // Define texture coordinates
      float u1=glyph.x / (float) texture.getWidth();
      float v1=glyph.y / (float) texture.getHeight();
      float u2=(glyph.x + glyph.width) / (float) texture.getWidth();
      float v2=(glyph.y + glyph.height) / (float) texture.getHeight();

      float[]textureArray={
        u1,v1, // Top-left
        u2,v1, // Top-right
        u2,v2, // Bottom-right
        u1,v2  // Bottom-left
      };

      // Define indices for two triangles
      short[]indicesArray={0,1,2,0,2,3};

      // Set up the glyph sprite
      glyphSprite.setVerticeArray(verticesArray);
      glyphSprite.setTextureArray(textureArray);
      glyphSprite.setIndiceArray(indicesArray);
      glyphSprite.setTexture(texture);
      //glyphSprite.useTexture(true);
      glyphSprite.setColor(color);
      glyphSprite.renderMode=GLES20.GL_POINTS;
      glyphSprite.setPointSize(10);
      glyphSprites.add(glyphSprite);
      xCursor += glyph.xAdvance;
    }
  }

  @Override
  public void render(Camera cam){
    for(Sprite glyphSprite : glyphSprites)
      glyphSprite.draw(cam);
  }

  @Override
  public boolean isClicked(){return false;}
  @Override
  public boolean isLongPreesed(){return false;}
  @Override
  public boolean isDragged(){return false;}
  @Override
  public void event(MotionEvent e){}

  // =================== FONT CLASS ===================
  //original
  public static class Font{
    public Map<Character,Glyph> glyphs=new HashMap<>();

    public Font(String jsonPath){
      String jsonString=loadJSONFromAssets(jsonPath);
      if(jsonString==null)return;

      parseGlyphs(jsonString);
    }

    private void parseGlyphs(String jsonString){
      try{
        JSONObject jsonObject=new JSONObject(jsonString);

        for(Iterator<String>it=jsonObject.keys();it.hasNext();){
          String key=it.next();
          if(key.length()!=1)continue; // Ensure it's a single character

          char character=key.charAt(0);
          JSONObject glyphData=jsonObject.getJSONObject(key);

          Glyph glyph=new Glyph(
            glyphData.optInt("x",0),
            glyphData.optInt("y",0),
            glyphData.optInt("width",0),
            glyphData.optInt("height",0),
            glyphData.optInt("offset_x",0),
            glyphData.optInt("offset_y",0),
            glyphData.optInt("advance_x",0)
          );
          glyphs.put(character,glyph);
        }
      }catch(JSONException e){
        e.printStackTrace();
      }
    }

    public static String loadJSONFromAssets(String path){
      try{
        InputStream is=G3D.context.getAssets().open(path);
        int size=is.available();
        byte[]buffer=new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer,StandardCharsets.UTF_8);
      }catch(Exception e){
        e.printStackTrace();
        return null;
      }
    }

    public static class Glyph{
      public int x,y,width,height,xOffset,yOffset,xAdvance;

      public Glyph(int x,int y,int width,int height,int xOffset,int yOffset,int xAdvance){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.xOffset=xOffset;
        this.yOffset=yOffset;
        this.xAdvance=xAdvance;
      }
    }
  }
  /*
  public static class Font{
    public Map<Character,Glyph>glyphs=new HashMap<>();

    public Font(String jsonPath){
      String jsonString=loadJSONFromAssets(jsonPath);
      if(jsonString==null)return;

      parseJson(jsonString);
    }

    private void parseJson(String jsonString){
      jsonString=jsonString.trim();
      if(jsonString.startsWith("{") && jsonString.endsWith("}")){
        jsonString=jsonString.substring(1,jsonString.length() - 1);
      }

      String[]charEntries=jsonString.split("\\},\"");
      for(String entry : charEntries){
        entry=entry.trim();
        if(entry.startsWith("\""))entry=entry.substring(1);
        if(entry.endsWith("}"))entry=entry.substring(0,entry.length() - 1);

        String[]parts=entry.split("\":\\{");
        if(parts.length < 2)continue;

        char character=parts[0].charAt(0);
        String charInfo="{" + parts[1] + "}";

        Glyph glyph=parseGlyph(charInfo);
        glyphs.put(character,glyph);
      }
    }

    private Glyph parseGlyph(String json){
      return new Glyph(
        extractInt(json,"\"x\":"),
        extractInt(json,"\"y\":"),
        extractInt(json,"\"width\":"),
        extractInt(json,"\"height\":"),
        extractInt(json,"\"offset_x\":"),
        extractInt(json,"\"offset_y\":"),
        extractInt(json,"\"advance_x\":")
      );
    }

    private int extractInt(String json,String key){
        int start=json.indexOf(key) + key.length();
        if(start == -1)return 0;
        int end=json.indexOf(",",start);
        if(end == -1)end=json.indexOf("}",start);
        return Integer.parseInt(json.substring(start,end).trim());
    }

    public static String loadJSONFromAssets(String path){
      try(InputStream is=G3D.context.getAssets().open(path)){
        int size=is.available();
        byte[]buffer=new byte[size];
        is.read(buffer);
        return new String(buffer,StandardCharsets.UTF_8);
      }catch(IOException e){
        e.printStackTrace();
        return null;
      }
    }
  }*/
}
