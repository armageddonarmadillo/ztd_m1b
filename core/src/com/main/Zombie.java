package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Zombie {
    int x, y, w, h, speed, hp;
    float chunk;
    String type;
    boolean active = true;

    //Animation Variables
    int cols, rows = 1;
    Animation anim;
    TextureRegion[] frames;
    TextureRegion frame;
    float frame_time;

    Zombie(String type, int x, int y){
        this.type = type;
        this.x = x;
        this.y = y;
        this.speed = (Tables.values.get("speed_" + type) == null ? 2 : Tables.values.get("speed_" + type));
        this.cols = Tables.values.get("columns_" + type) == null ? 4 : Tables.values.get("columns_" + type);
        this.w = (Tables.zombie_resources.get(type) == null ? Resources.zombie : Tables.zombie_resources.get(type)).getWidth() / cols;
        this.h = (Tables.zombie_resources.get(type) == null ? Resources.zombie : Tables.zombie_resources.get(type)).getHeight() / rows;
        this.hp = Tables.values.get("health_" + type) == null ? 3 : Tables.values.get("health_" + type);
        chunk = (float)w / hp;
        // make this last in constructor
        prep_animations();
    }

    void draw(SpriteBatch batch){
        frame_time += Gdx.graphics.getDeltaTime();
        frame = (TextureRegion)anim.getKeyFrame(frame_time, true);
        batch.draw(frame, x, y);

        batch.draw(Resources.red_bar, x, y + h, w, 5);
        batch.draw(Resources.green_bar, x, y + h, hp * chunk, 5);
    }

    void update(){
        x -= speed;
        UI.score += hp > 0 ? 0 : (Tables.values.get("score_" + type) == null ? 1 : Tables.values.get("score_" + type));
        UI.life -= x >= 0 ? 0 : 1;
        UI.zk += hp > 0 ? 0 : 1;
        active = x >= 0 && hp > 0;
    }

    Rectangle hitbox() { return new Rectangle(x, y, w, h); }

    void prep_animations(){
        //slice image into cells
        TextureRegion[][] sheet = TextureRegion.split(Tables.zombie_resources.get(type) == null ? Resources.zombie : Tables.zombie_resources.get(type),
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
