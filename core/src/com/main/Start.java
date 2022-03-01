package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Start extends Scene {
    mButton m1, m2, m3;
    Color bg = new Color(0x03101500);
    Start(){
        m1 = new mButton("start", (1024 / 2) - (mButton.bw / 2), 325, mButton.bw, mButton.bh, Color.DARK_GRAY);
        m2 = new mButton("about", (1024 / 2) - (mButton.bw / 2), 200, mButton.bw, mButton.bh, Color.DARK_GRAY);
        m3 = new mButton("achieve", (1024 / 2) - (mButton.bw / 2), 75, mButton.bw, mButton.bh, Color.DARK_GRAY);
        font.setColor(Color.FIREBRICK);
        font.getData().setScale(5f);
        layout.setText(font, "Zombie Tower Defense");
    }

    void tap(int x, int y){
        if(m1.hitbox().contains(x, y)) Main.started = true;
        if(m2.hitbox().contains(x, y)) Main.about = true;
        if(m3.hitbox().contains(x, y)) Main.achievements = true;
    }

    void draw(SpriteBatch batch){
        ScreenUtils.clear(bg);
        batch.draw(Resources.light_gray, 1024 - 10 - 10, 10, 10, 600 - 10 - 10);
        batch.draw(Resources.dark_gray, 10, 10, 1024 - 10 - 10, 10);
        batch.draw(Resources.dark_gray, 10, 10, 10, 600 - 10 - 10);
        batch.draw(Resources.light_gray, 10, 600 - 10 - 10, 1024 - 10 - 10, 10);
        batch.draw(Resources.scarlet, (float)1024 / 2 - layout.width / 2 - 25, 525 - layout.height - 15, layout.width + 50, 2);
        batch.draw(Resources.scarlet, (float)1024 / 2 - layout.width / 2 - 25, 525 + 15, layout.width + 50, 2);
        font.draw(batch, layout, (float)1024 / 2 - layout.width / 2, 525);
        m1.draw(batch);
        m2.draw(batch);
        m3.draw(batch);
    }
}
