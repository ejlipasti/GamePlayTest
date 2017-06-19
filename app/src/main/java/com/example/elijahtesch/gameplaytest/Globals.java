package com.example.elijahtesch.gameplaytest;

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

    public static void init(){
        SCENE_HEIGHT = 10; //height of scene
        SCENE_WIDTH = SCENE_HEIGHT * ((double)SCREEN_WIDTH / SCREEN_HEIGHT);
        PIX_PER_M = SCREEN_HEIGHT / SCENE_HEIGHT;
        G = -15; //meters per second squared
        System.out.println("Pixel/Meter  = " + PIX_PER_M);
        System.out.println("G = " + G);
    }

}
