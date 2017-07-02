package com.example.elijahtesch.gameplaytest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by elijahtesch on 7/2/17.
 */

//Animations branch

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;
    boolean running = false;
    private float frameTime; //in millis
    private long lastFrame;

    public void start(){
        running = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void stop(){
        running = false;

    }

    public boolean isRunning(){
        return running;
    }

    public Animation(Bitmap[] frames, float animTime) {
      this.frames = frames;
      frameIndex = 0;
      frameTime = animTime / frames.length;

      lastFrame = System.currentTimeMillis();
    }

    public void update() {
      if(!running) return;
      if(System.currentTimeMillis() - lastFrame > frameTime){
          frameIndex++;
          if(frameIndex >= frames.length) frameIndex = 0;
          lastFrame = System.currentTimeMillis();
      }
    }

    public void draw(Canvas canvas, Rect destination, Paint paint){
      //if(!running) return;
      canvas.drawBitmap(frames[frameIndex],null,destination,paint);
    }




}
