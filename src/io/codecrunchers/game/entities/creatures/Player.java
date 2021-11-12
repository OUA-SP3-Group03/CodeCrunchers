package io.codecrunchers.game.entities.creatures;
import io.codecrunchers.core.ASCII;
import io.codecrunchers.facades.App;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class Player extends Creature {

    private static Clip jumpClip, attackClip;
    private static AudioInputStream stream;
    private static AudioFormat format;

    //Records the player's direction
    //right = 1
    //left = -1
    private int facing = 1;
    private App app;
    private boolean jumping = false;
    private boolean attacking = false;
    private int fallSpeed = 0;
    private int jumpSpeed = 30;

    public Player(float x, float y, int width, int height, App app) {
        super(x, y, width, height);
        this.app = app;
        this.texture = this.app.texture().allImages()[26];

        //Load first jump+attack sound clips
        try {
            jumpSound();
            attackSound();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void tick() {
        if((this.app.keyPressed().containsKey((int)'D') || this.app.keyPressed().containsKey(KeyEvent.VK_RIGHT))
                && !this.app.getTileAtLocation(((int)(this.x+46)/64),(int)(this.y+32)/64).solid() ){
                this.x += 6;
        }
        if((this.app.keyPressed().containsKey((int)'A') || this.app.keyPressed().containsKey(KeyEvent.VK_LEFT))
                && !this.app.getTileAtLocation(((int)(this.x+12)/64),(int)(this.y+32)/64).solid()
                && this.x >= this.app.getCamera().getxOffset() - 12 ) {
                this.x -= 6;
        }

        if(this.app.keyPressed().containsKey((int)'W') || this.app.keyPressed().containsKey(KeyEvent.VK_UP)) {
            jumping = true;
        }

        //Temp attack statement
        if(this.app.keyPressed().containsKey(ASCII.space)) {
            attacking = true;
        }
        else {
            attacking = false;
        }

        attack();
        jump();
        fall();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.texture, (int) ((int)this.x - this.app.getCamera().getxOffset()), (int) ((int)this.y - this.app.getCamera().getyOffset()),null);
    }

    public void fall() {
        if(!(this.app.getTileAtLocation( ((int)(this.x+32)/64),(int)(this.y+64)/64).solid() ||
                this.app.getTileAtLocation( ((int)(this.x)/64),(int)(this.y+64)/64).solid())
                        && !jumping) {
            y += fallSpeed;
            falling = true;

            if (fallSpeed != gravity) {
                fallSpeed = fallSpeed + 2;
            }

            //Player doesn't fall through tiles
            if (this.app.getTileAtLocation( ((int)(this.x+32)/64),(int)(this.y+64)/64).solid() && !jumping) {
                float tempY = this.y;
                tempY = tempY / 64;

                this.y = (int)tempY * 64;
            }
       } else {
            falling = false;
            fallSpeed = 0;
        }
    }

    public void jump() {
        if(!this.app.getTileAtLocation( ((int)(this.x+32)/64),(int)(this.y)/64).solid() && jumping && !falling) {

            //Play jump sound
            jumpClip.start();

            y -= jumpSpeed;
            jumpSpeed -= 2;

            if (jumpSpeed <= 0) {
                jumpSpeed = 30;
                jumping = false;
                falling = true;
            }
        }
        else {
            //Reload jump sound for next use
            try {
                jumpSound();
            } catch (Exception e) {
                e.printStackTrace();
            }
            jumping = false;
            falling = true;
            jumpSpeed = 30;
        }
    }

    //Temp attack method, to test sound
    public void attack() {
        if (attacking) {
            attackClip.start();
        }
        else {
            try {
                attackSound();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void die() {
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y,width,height);
    }


    //These bounds are called to specify which side the player is colliding
    public Rectangle getBottom() {
        return new Rectangle((int)(x + (width/4)), (int)(y + (height)-(height/3)) - 5, width / 2,  height/3);
    }
    public Rectangle getTop(){
        return new Rectangle((int)(x + (width/4)), (int) y + 5,  width / 2,  height/3);
    }
    public Rectangle getLeft(){
        return new Rectangle((int) x + 5, (int) y + 10,  5,  height - 20);
    }
    public Rectangle getRight(){
        return new Rectangle((int)( x + width - 10), (int) y + 10,  5,  height - 20);
    }


    public void jumpSound() throws Exception {
        stream = AudioSystem.getAudioInputStream(new File("res/Jump.wav"));
        format = stream.getFormat();

        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(),
                    format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2,
                    format.getFrameRate(), true);
            stream = AudioSystem.getAudioInputStream(format, stream);
        }

        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),
                ((int) stream.getFrameLength() * format.getFrameSize()));
        jumpClip = (Clip) AudioSystem.getLine(info);

        jumpClip.open(stream);

        //Volume control
        FloatControl volume = (FloatControl) jumpClip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-10f);
    }

    public void attackSound() throws Exception {
        stream = AudioSystem.getAudioInputStream(new File("res/Attack.wav"));
        format = stream.getFormat();

        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(),
                    format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2,
                    format.getFrameRate(), true);
            stream = AudioSystem.getAudioInputStream(format, stream);
        }

        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),
                ((int) stream.getFrameLength() * format.getFrameSize()));
        attackClip = (Clip) AudioSystem.getLine(info);

        attackClip.open(stream);

        //Volume control
        FloatControl volume = (FloatControl) attackClip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-10f);
    }
}