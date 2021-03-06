package com.example.elijahtesch.gameplaytest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elijahtesch on 5/31/17.
 */

 //Animations Branch

public class GamePanel extends GLSurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Player player;
    private ArrayList<Ground> objects;
    private Background [] backgrounds;
    int groundSpacing = 20;
    int groundWidth = 15;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        Globals.CURRENT_CONTEXT = context;
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(),this);
        Ground.loadBitmap();
        objects = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            objects.add(i,new Ground(groundSpacing * i, Globals.SCENE_HEIGHT / 16,groundWidth, Globals.SCENE_HEIGHT / 8 ));
        }
        Background.loadBitmap();
        backgrounds = new Background [4];
        backgrounds[0] = new Background(0,2);
        backgrounds[1] = new Background(Globals.SCREEN_WIDTH,2);
        backgrounds[2] = new Background(0,1);
        backgrounds[3] = new Background(Globals.SCREEN_WIDTH,1);
        player = new Player();

        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        for(int i = 0; i < pointerCount; i++) {
            MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
            event.getPointerCoords(i,pointerCoords);
            int x = (int)pointerCoords.x;
            int y = (int)pointerCoords.y;
            int id = event.getPointerId(i);
            int action = event.getActionMasked();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    if(x > Globals.SCREEN_WIDTH / 2)
                        player.ChangeXVel((2 * x - Globals.SCREEN_WIDTH)/(double)Globals.SCREEN_WIDTH);
                    break;
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if(x < Globals.SCREEN_WIDTH / 2)
                    player.startJump();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    if(x < Globals.SCREEN_WIDTH / 2)
                    player.stopJump();
                    break;

            }
        }
        //System.out.println(event.getPointerCount());
        return  true;
    }

    public void update() {
        for (int i = 0; i < objects.size(); i++){
            objects.get(i).update(player.getX());
            player.collidesWith(objects.get(i));
            if (objects.get(i).isRemovable()){
                objects.remove(i);
                Random r = new Random();
                double newX = objects.get(objects.size() - 1).getX() + 5 + 5 * r.nextInt(2);
                double newY = Globals.SCENE_HEIGHT / 16 + r.nextInt(3) * (Globals.SCENE_HEIGHT / 4);
                objects.add(new Ground(newX, newY,5 + 5 * r.nextInt(2), Globals.SCENE_HEIGHT / 8 ));
            }
        }
        for (int i = 0; i < backgrounds.length; i++){
          backgrounds[i].update(player.getVelX());
        }
        player.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE); //set background color
        canvas.drawARGB(50,0,0,0);
        backgrounds[0].draw(canvas);
        backgrounds[1].draw(canvas);
        canvas.drawARGB(50,0,0,0);
        backgrounds[2].draw(canvas);
        backgrounds[3].draw(canvas);
        canvas.drawARGB(50,0,0,0);

        for (int i = 0; i < objects.size(); i++){
            objects.get(i).draw(canvas);
        }
        player.draw(canvas);

        //print speed in mph
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        NumberFormat f = new DecimalFormat("#0.00");
        canvas.drawText("Speed: " + f.format(player.getVelX() * 2.23694) + "MPH",15,15,paint);
        canvas.drawText("Dist: " + f.format(player.getX()) + "M",15,50,paint);
    }

}
