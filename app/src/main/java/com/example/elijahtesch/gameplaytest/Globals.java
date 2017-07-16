package com.example.elijahtesch.gameplaytest;

import android.content.Context;

/**
 * Created by elijahtesch on 5/31/17.
 */




public class Globals {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static double SCENE_HEIGHT; //scene height in meters
    public static double SCENE_WIDTH; //scene width in meters
    public static double PIX_PER_M; //pixels per meter
    public static double G; //acceleration due to gravity
    public static Context CURRENT_CONTEXT;

    public static void init(){
        SCENE_HEIGHT = 10; //height of scene
        SCENE_WIDTH = SCENE_HEIGHT * ((double)SCREEN_WIDTH / SCREEN_HEIGHT);
        PIX_PER_M = SCREEN_HEIGHT / SCENE_HEIGHT;
        G = -15; //meters per second squared
        System.out.println("H " + SCREEN_HEIGHT);
        System.out.println("W " + SCREEN_WIDTH);
    }

}
