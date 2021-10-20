package io.codecrunchers.providers;


import io.codecrunchers.core.Provider;
import io.codecrunchers.facades.App;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AssetServiceProvider extends Provider {

    //**** BOOT METHOD ****\\
    private BufferedImage test;
    private BufferedImage textureMap;
    private BufferedImage enemy;
    private BufferedImage[] images;


    @Override
    //this method is called by the Kernel when the program loads for the first time, you can think of this like your constructor class
    //place any code that you need to run at the start of the program once in here
    public void boot(App app) {

        //Code Block below is responsible for loading sprite sheet into application
        textureMap = imageLoader(app.config().texturePath());
        int i = 0;
        int rows = 0;
        while (i < app.config().textureMapWidth()){
            rows ++;
            i += app.config().textureWidth();
        }
        i = 0;
        int columns = 0;
        while(i < app.config().textureMapHeight()){
            columns ++;
            i += app.config().textureHeight();
        }

        this.images = new BufferedImage[rows * columns];

        //Code block below is responsible for auto-cropping loaded sprite sheet.
        int x = 0;
        int y = 0;
        int currentImage = 0;

        //While loop create sub-images from texture map depending on their position.
        while (x < rows){
            while (y < columns){
                this.images[currentImage] = textureMap.getSubimage(x*app.config().textureWidth(),y*app.config().textureHeight(),app.config().textureWidth(),app.config().textureHeight());
                currentImage++;
                y++;
            }
            y=0;
            x++;
        }

    this.booted=true;
    }

    //**** PERFORM TICK METHOD ****\\
    @Override
    public boolean performTick() {
        //this method tells the kernel if this service provider needs to be ticked or not, return true for yes or false for no
        return false;
    }

    //**** PERFORM RENDER METHOD ****\\
    @Override
    public boolean performRender() {
        //this method tells the kernel if this service provider needs to render code in its render function, return true for yes or no for false.
        return false;
    }

    //**** RENDER METHOD ****\\
    @Override
    public void render(Graphics g) {
        //place all your canvas rendering code in this method, this is called by the kernel at a time specified by the program max frames per second
        //keep this code as lean as possible to keep performance high
    }

    //**** TICK METHOD ****\\
    @Override
    public void tick() {
        //place all your tick or update code in this method, this is called by the kernel at a time specified by the max ticks per second
        //keep this code as lean as possible to keep performance high
    }

    //**** Image loader method ****\\
    private BufferedImage imageLoader(String path ){
        try{
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //**** Image getter method ****\\
    public BufferedImage[] getImages(){
        return this.images;
    }

}