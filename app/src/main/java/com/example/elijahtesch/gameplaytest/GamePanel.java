package com.example.elijahtesch.gameplaytest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
//testcommit
/**
 * Created by elijahtesch on 5/31/17.
 */

//Test Git hub Commit & Push
//Test for command line commit
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Player player;
    private ArrayList<Ground> objects;
    int groundSpacing = 30;
    int groundWidth = 17;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(),this);
        setFocusable(true);

        objects = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            objects.add(i,new Ground(groundSpacing * i, Globals.SCENE_HEIGHT / 16,groundWidth, Globals.SCENE_HEIGHT / 8 ));
        }
        player = new Player();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(),this);
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
            int x = (int) event.getX();
            int y = (int) event.getY();
            int id = event.getPointerId(i);
            int action = event.getActionMasked();
            switch (action) {
            /*
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getX() < Globals.SCREEN_WIDTH / 2) player.jump();
                else {
                    player.accelerate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getX() > Globals.SCREEN_WIDTH / 2){
                    player.deAccelerate();
                }
                break;
               */
                case MotionEvent.ACTION_DOWN:
                    if (x < Globals.SCREEN_WIDTH / 2) player.jump();
                    else player.accelerate();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    System.out.println("Pointer Down");
                    player.jump();
                    break;
                case MotionEvent.ACTION_UP:
                    if(x > Globals.SCREEN_WIDTH / 2) player.deAccelerate();
                    System.out.println("Up");
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    if(x > Globals.SCREEN_WIDTH / 2) player.deAccelerate();
                    System.out.println("Pointer Up");
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
                double newX = objects.get(objects.size() - 1).getX() + 5 + 5 * r.nextInt(6);
                objects.add(new Ground(newX, Globals.SCENE_HEIGHT / 16,5 + 5 * r.nextInt(2), Globals.SCENE_HEIGHT / 8 ));
            }
        }
        player.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.BLUE); //set background color
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
