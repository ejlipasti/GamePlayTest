package com.example.elijahtesch.gameplaytest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
    //velocity meters per second
    double velY;
    double velX;
    double minXVel;
    double maxXVel;
    //velocity meters per second squared
    double accY;
    double accX;
    boolean onGround;
    boolean jumping;
    boolean accelerating;
    double edge;

    public Player(){
        draw_x = Globals.SCREEN_WIDTH / 4;
        radius = 0.66;
        draw_radius = (int)(radius * Globals.PIX_PER_M);
        velX = 0;
        velY = 0;
        accX = 0.45;
        accY = 0;
        x = 0;
        y = 5.0;
        onGround = false;
        jumping = false;
        accelerating = false;
        minXVel = 6;
        maxXVel = 25;
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
        //System.out.println(onGround);
        if (x > edge) onGround = false;
        if(!onGround){
            velY += Globals.G / MainThread.MAX_FPS;
            y += velY / MainThread.MAX_FPS;
        }else{
          if (accelerating && velX < maxXVel){
            velX += accX;
          }else if(!accelerating && velX > minXVel){
            velX -= accX * 3;
          }
        }
        x += velX / MainThread.MAX_FPS;


        draw_y = (int)(Globals.SCREEN_HEIGHT - y * Globals.PIX_PER_M);
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(draw_x,draw_y,draw_radius,paint);
    }

    public void accelerate(){
      accelerating = true;
    }

    public void deAccelerate(){
      accelerating = false;
    }

    public void jump(){
      if(onGround){
          onGround = false;
        velY = 7;
      }
    }

    public double getX(){
      return x;
    }

    public double getVelX(){
        return velX;
    }
}
