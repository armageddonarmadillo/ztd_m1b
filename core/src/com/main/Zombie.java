package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Zombie {
    int x, y, w, h, speed, hp;
    String type;
    boolean active = true;

    //Animation Variables
    int cols = 4, rows = 1;
    Animation anim;
    TextureRegion[] frames;
    TextureRegion frame;
    float frame_time;

    Zombie(String type, int x, int y, int speed){
        this.type = type;
        this.x = x;
        this.y = y;
        this.speed = speed;
        w = 50;
        h = 50;
        hp = 5;
        // make this last in constructor
        prep_animations();
    }

    void draw(SpriteBatch batch){
        frame_time += Gdx.graphics.getDeltaTime();
        frame = (TextureRegion)anim.getKeyFrame(frame_time, true);
        batch.draw(frame, x, y);
    }

    void update(){
        x -= speed;
        active = x >= 0 && hp > 0;
    }

    Rectangle hitbox() { return new Rectangle(x, y, w, h); }

    void prep_animations(){
        //slice image into cells
        TextureRegion[][] sheet = TextureRegion.split(Resources.zombie,
                Resources.zombie.getWidth() / cols,
                Resources.zombie.getHeight() / rows);

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
