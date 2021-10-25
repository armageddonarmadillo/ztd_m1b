package com.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cannon {
    int x, y, w, h;
    String type;

    Cannon(String type, int x, int y){
        this.type = type;
        w = 50;
        h = 50;
        this.x = grid_lock(x - w / 2);
        this.y = grid_lock(y - h / 2);
    }

    void draw(SpriteBatch batch){
        batch.draw(Tables.cannon_resources.get(type) == null ? Resources.cannon : Tables.cannon_resources.get(type), x, y);
    }

    void update() {}

    int grid_lock(int n){
        return ((n + 25) / 50) * 50;
    }

    Rectangle hitbox() { return new Rectangle(x, y, w, h); }
}
