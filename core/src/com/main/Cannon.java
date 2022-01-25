package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Cannon {
    Sprite sprite;
    int x, y, w, h;
    String type;
    int counter = 0, delay, hp;
    float chunk;
    boolean active = true, damaged = false;

    //Animation Variables
    int cols, rows = 1;
    Animation anim;
    TextureRegion[] frames;
    TextureRegion frame;
    float frame_time;

    Cannon(String type, int x, int y){
        sprite = new Sprite(Tables.cannon_resources.get(type) == null ? Resources.cannon : Tables.cannon_resources.get(type));
        this.type = type;
        delay = Tables.values.get("delay_" + type) == null ? 30 : Tables.values.get("delay_" + type);
        cols = Tables.values.get("columns_" + type) == null ? 1 : Tables.values.get("columns_" + type);
        w = (Tables.cannon_resources.get(type) == null ? Resources.cannon : Tables.cannon_resources.get(type)).getWidth() / cols;
        h = (Tables.cannon_resources.get(type) == null ? Resources.cannon : Tables.cannon_resources.get(type)).getHeight() / rows;
        this.hp = Tables.values.get("health_" + type) == null ? 100 : Tables.values.get("health_" + type);
        chunk = (float)w / hp;
        this.x = grid_lock(x - w / 2);
        this.y = grid_lock(y - h / 2);
        sprite.setPosition(this.x, this.y);
        prep_animations();
    }

    void draw(SpriteBatch batch){
        sprite.draw(batch);
        if(damaged) { batch.draw(Resources.damaged, x, y); return; }
        batch.draw(Resources.red_bar, x, y + h, w, 5);
        batch.draw(Resources.green_bar, x, y + h, hp * chunk, 5);
    }

    void update() {
        damaged = !type.equals("super") && hp-- < 0;
        if(damaged) return;
        frame_time += Gdx.graphics.getDeltaTime();
        frame = (TextureRegion)anim.getKeyFrame(frame_time, true);
        sprite = new Sprite(frame);
        sprite.setPosition(this.x, this.y);
        sprite.setRotation(calc_angle());
        fire();
    }

    void fire() {
        if(counter++ < delay) return;
        counter = 0;
        ZTD.bullets.add(new Bullet(type, x + w / 2, y + h / 2));
    }

    int grid_lock(int n){
        return ((n + 25) / 50) * 50;
    }

    Rectangle hitbox() { return new Rectangle(x, y, w, h); }

    float calc_angle(){
        Zombie closest = null;
        for(Zombie z : ZTD.zombies){
            if(closest == null) { closest = z; continue; }
            float closest_dif = (float)Math.sqrt((x - closest.x) * (x - closest.x) + (y - closest.y) * (y - closest.y));
            float z_dif = (float)Math.sqrt((x - z.x) * (x - z.x) + (y - z.y) * (y - z.y));
            if(z_dif < closest_dif) closest = z;
        }
        return (float)Math.toDegrees(Math.atan((float)(y - (closest.y + closest.h / 2)) / (float)(x - (closest.x + closest.w / 2))) + (x >= closest.x ? Math.PI : 0));
    }

    void prep_animations(){
        //slice image into cells
        TextureRegion[][] sheet = TextureRegion.split(Tables.cannon_resources.get(type) == null ? Resources.cannon : Tables.cannon_resources.get(type),
                w,
                h);

        //set frames to maximum numbers of cells
        frames = new TextureRegion[rows*cols];
        int index = 0;
        //fill frames
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++)
                frames[index++] = sheet[r][c];

        anim = new Animation(0.2f, frames);
    }
}
