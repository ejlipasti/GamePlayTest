package com.example.elijahtesch.gameplaytest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


/**
 * Created by elijahtesch on 5/31/17.
 */

public class Player {
    int draw_x;
    int draw_y;
    int draw_radius;
    double radius;
    double x; //meters
    double y; //meters
    //velocity (meters per second)
    double velY;
    double velX;
    //acceleration (meters per second squared)
    double accY;

    //other state variables
    boolean onGround;
    boolean jumping;
    double maxXVel;
    double maxJumpHeight;
    double jumpingVelocity;
    double initialJumpHeight;
    double edge;

    private Animation run;

    public Player(){
        draw_x = Globals.SCREEN_WIDTH / 4;
        radius = 1;
        draw_radius = (int)(radius * Globals.PIX_PER_M);
        velX = 0;
        velY = 0;
        accY = 0;
        x = 0;
        y = 5.0;
        onGround = false;
        jumping = false;
        maxXVel = 17;
        maxJumpHeight = 5;
        jumpingVelocity = 7;
        //load animations
        BitmapFactory bf = new BitmapFactory();
        Bitmap[] runFrames = new Bitmap[8];
        runFrames[0] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.p1);
        runFrames[1] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.p2);
        runFrames[2] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.p3);
        runFrames[3] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.p4);
        runFrames[4] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.p5);
        runFrames[5] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.p6);
        runFrames[6] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.p7);
        runFrames[6] = bf.decodeResource(Globals.CURRENT_CONTEXT.getResources(),R.drawable.p8);
        run = new Animation(runFrames,1000);
    }

    public void collidesWith(Ground g){
      //checks if inbetween x boundaries
      if(x > g.getX() - g.getW() / 2
              && x < g.getX() + g.getW() / 2
              && y - radius <  g.getY() + g.getH() / 2
              && y - radius > g.getY()) {
            onGround = true;
            velY = 0;
            y = radius + g.getY() + g.getH() / 2;
            edge = g.getX() + g.getW() / 2;
      }
    }


    public void update(){
        //stop jumping based on max jump height
        if (y > initialJumpHeight + maxJumpHeight) jumping = false;
        //fall of right edge of ground
        if (x > edge) onGround = false;

        //physics & Animation logic
        if(!onGround){
          if (!jumping){
              velY += Globals.G / MainThread.MAX_FPS;
          }
          run.stop();
          y += velY / MainThread.MAX_FPS;
        }else if(!run.isRunning()) run.start();
        x += velX / MainThread.MAX_FPS;

        run.update();

        //fall reset for debugging
        if(y < -3) y = 10;

        draw_y = (int)(Globals.SCREEN_HEIGHT - y * Globals.PIX_PER_M);
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        Rect dst = new Rect(draw_x - draw_radius,draw_y - draw_radius,draw_x + draw_radius, draw_y + draw_radius);
        run.draw(canvas,dst,paint);
    }

    public void ChangeXVel(double intensity){
      velX = maxXVel * intensity;
    }

    public void startJump(){
        if(onGround) {
            jumping = true;
            onGround = false;
            velY = jumpingVelocity;
            initialJumpHeight = y;
            y += velY / MainThread.MAX_FPS;
        }
    }

    public void stopJump(){
      jumping = false;
    }

    public double getX(){
      return x;
    }

    public double getVelX(){
        return velX;
    }
}
