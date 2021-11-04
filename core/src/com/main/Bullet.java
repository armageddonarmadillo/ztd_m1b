package com.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    int x, y, w, h, speed;
    float angle;
    String type;
    boolean active = true;

    Bullet(String type, int x, int y){
        this.type = type;
        w = Resources.bullet.getWidth();
        h = Resources.bullet.getHeight();
        this.x = x;
        this.y = y;
        speed = 5;
        angle = calc_angle();
    }

    void draw(SpriteBatch batch){
        batch.draw(Resources.bullet, x, y);
    }

    void update() {
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
        zombie_collision();
    }

    void zombie_collision(){
        if(Main.zombies.isEmpty()) return;
        for(Zombie z : Main.zombies)if(z.hitbox().contains(this.hitbox())) { z.hp--; this.active = false; }
    }

    Rectangle hitbox() { return new Rectangle(x, y, w, h); }

    float calc_angle(){
        float zx = Main.zombies.get(0).x + (float)(Main.zombies.get(0).w / 2), zy = Main.zombies.get(0).y + (float)(Main.zombies.get(0).h / 2);
        return (float)(Math.atan((y - zy) / (x - zx)) + (x >= zx ? Math.PI : 0));
    }
}