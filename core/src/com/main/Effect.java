package com.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Effect {
    int x, y, w, h;
    boolean active = true;
    String type;

    //Animation Variables
    int cols, rows = 1;
    Animation anim;
    TextureRegion[] frames;
    TextureRegion frame;
    float frame_time;

    Effect(String type, int x, int y) { // CENTERED X AND Y POSITIONS OF PARENT
        this.type = type;
        cols = Tables.values.get("columns_" + type) == null ? 4 : Tables.values.get("columns_" + type);
        w = (Tables.resources.get("effect_"+type) == null ? Resources.click : Tables.resources.get("effect_"+type)).getWidth() / cols;
        h = (Tables.resources.get("effect_"+type) == null ? Resources.click : Tables.resources.get("effect_"+type)).getHeight() / rows;
        this.x = x - w / 2;
        this.y = y - h / 2;
        prep_animations();
    }

    void draw(SpriteBatch batch){
        frame_time += Gdx.graphics.getDeltaTime();
        frame = (TextureRegion)anim.getKeyFrame(frame_time, false);
        batch.draw(frame, x, y);
        active = !anim.isAnimationFinished(frame_time);
    }

    void prep_animations(){
        //slice image into cells
        TextureRegion[][] sheet = TextureRegion.split((Tables.resources.get("effect_"+type) == null ? Resources.click : Tables.resources.get("effect_"+type)),
                w,
                h);

        //set frames to maximum numbers of cells
        frames = new TextureRegion[rows*cols];
        int index = 0;
        //fill frames
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++)
                frames[index++] = sheet[r][c];

        anim = new Animation(0.1f, frames);
    }
}
