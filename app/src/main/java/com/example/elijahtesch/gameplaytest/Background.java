package com.example.elijahtesch.gameplaytest;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by elijahtesch on 7/5/17.
 */

public class Background {

    double draw_x;
    private static Bitmap [] bitmaps;
    int index;
    int layer;

    public static void loadBitmap(){
      bitmaps = new Bitmap[2];
      BitmapFactory bf = new BitmapFactory();
      bitmaps[0] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.bg1);
      bitmaps[1] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.bg2);
      for (int i = 0; i < bitmaps.length; i++){
        bitmaps[i] = Bitmap.createScaledBitmap(bitmaps[i],Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT,false);
      }
    }

    public Background(int draw_x,int layer){
      this.draw_x = draw_x;
      this.layer = layer;
      Random random = new Random();
      index = random.nextInt(bitmaps.length);
    }

    public void update(double player_speed){
      if (draw_x <= -Globals.SCREEN_WIDTH){
        Random random = new Random();
        index = random.nextInt(bitmaps.length);
        draw_x = Globals.SCREEN_WIDTH;
      }
      draw_x -= ((player_speed * Globals.PIX_PER_M) / MainThread.MAX_FPS) * Math.pow(0.5,layer + 1) ;
    }

    public void draw(Canvas canvas){
      Paint paint = new Paint();
        canvas.drawBitmap(bitmaps[index],(int)draw_x,0,paint);
      //Rect dst = new Rect((int)draw_x,0,(int)draw_x + Globals.SCREEN_WIDTH,Globals.SCREEN_HEIGHT);
      //canvas.drawBitmap(bitmaps[index],null,dst,paint);
    }



}
