package io.codecrunchers.game.entities.creatures;

import io.codecrunchers.game.entities.Entity;

import java.awt.*;

public abstract class Creature extends Entity {

    protected int health;
    protected boolean jumping = false, falling = true;
    protected float xVel, yVel;
    protected float gravity = 20.0f;
    protected int facing;
    protected long lastAttackTimer;
    protected final long attackCooldown = 300;
    protected long attackTimer = attackCooldown;
    protected int maxHealth = 100;
    protected int rangeWidth;

    public abstract void hurt(int value);

    public Creature(float x, float y) {
        super(x, y, 64, 64);
    }

    public Rectangle range() {
        Rectangle bounds = getBounds();
        Rectangle range = new Rectangle();

        range.width = this.rangeWidth;
        range.height = bounds.height;

        if (facing == 1) {
            range.x = bounds.x + bounds.width - bounds.width / 4;
            range.y = bounds.y;
        }
        if (facing == -1) {
            range.x = bounds.x - bounds.width / 4;
            range.y = bounds.y;
        }


        return range;
    }


    //Mutators and Accessors
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getxVel() {
        return xVel;
    }

    public void setxVel(float xVel) {
        this.xVel = xVel;
    }

    public float getyVel() {
        return yVel;
    }

    public void setyVel(float yVel) {
        this.yVel = yVel;
    }

    public int getMaxHealth(){
        return this.maxHealth;
    }

    protected void setRangeWidth(int width){
        this.rangeWidth = width;
    }
}