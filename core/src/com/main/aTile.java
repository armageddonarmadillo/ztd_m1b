package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class aTile {
    //TODO: accepts a color and uses the color to create a box on the screen (size TBD)
    int x, y, w, h;
    Color color;
    boolean expanded = false;
    static boolean is_expanded = false;
    int[] box =  { 100, 475, 1024 - 200, 300};
    Button close;

    aTile(int x, int y, int w, int h, Color color){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        close = new Button("close", box[0] + box[2] - Resources.button_close.getWidth() - 5, box[1] - Resources.button_close.getHeight() - 5);
        close.locked = false;
    }

    void draw(SpriteBatch batch){
        if(expanded) batch.draw(Resources.create_texture(new Color(0x05050555)), 0, 0, 1024, 600);
        batch.draw(Resources.create_texture(color),
                expanded ? box[0] : x,
                expanded ? box[1] - box[3] : y,
                expanded ? box[2] : w,
                expanded ? box[3] : h
        );
        if(expanded) close.draw(batch);
    }

    Rectangle hitbox() { return new Rectangle(x, y, w, h); }
    Rectangle bigbox() { return new Rectangle(box[0], box[1] - box[3], box[2], box[3]); }
}
