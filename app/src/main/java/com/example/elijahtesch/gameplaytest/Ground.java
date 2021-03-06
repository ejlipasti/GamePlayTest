package com.example.elijahtesch.gameplaytest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by elijahtesch on 6/4/17.
 */

public class Ground {
  int draw_x; //centered draw position
  int draw_y; //centered draw position
  int draw_w;
  int draw_h;
  double x; //centered x position(meters)
  double y; //centered y position(meters)
  double w; //width (meters)
  double h; //height (meters)
  boolean removable;
  private static Bitmap bitmap; //static to save memory

  public static void loadBitmap(){
    BitmapFactory bf = new BitmapFactory();
    bitmap = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.pipe);
  }

  public Ground(double x, double y,double w, double h){
    removable = false;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    draw_w = (int)(Globals.PIX_PER_M * w);
    draw_h = (int)(Globals.PIX_PER_M * h);
  }

  public void update(double player_x){
    if(x + w < player_x) removable = true;
    draw_x = (int)((x - player_x + Globals.SCENE_WIDTH / 4) * Globals.PIX_PER_M);
    draw_y = (int)(Globals.SCREEN_HEIGHT - y * Globals.PIX_PER_M);
  }

  public void draw(Canvas canvas){
    Paint paint = new Paint();
    Rect dst = new Rect(draw_x - draw_w / 2, draw_y - draw_h / 2, draw_x + draw_w / 2, draw_y + draw_h / 2);
    canvas.drawBitmap(bitmap,null,dst,paint);
  }

  public double getX(){
    return x;
  }

  public double getY(){
    return y;
  }

  public double getW(){
    return w;
  }

  public double getH(){
    return h;
  }

  public boolean isRemovable(){
    return  removable;
  }


}
