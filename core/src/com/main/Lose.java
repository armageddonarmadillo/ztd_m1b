package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Lose extends Scene {
    mButton m1;

    Lose(){
        m1 = new mButton("back", (1024 / 2) - (mButton.bw / 2), 325, mButton.bw, mButton.bh, Color.DARK_GRAY);
    }

    void tap(int x, int y){
        if(m1.hitbox().contains(x, y)) {
            Main.started = false;
            Main.gameover = false;
            Main.p.putInteger("zk", UI.zk);
            Main.p.flush();
            Main.ztd = new ZTD();
        }
    }

    void draw(SpriteBatch batch){
        ScreenUtils.clear(new Color(0x15100300));
        m1.draw(batch);
    }
}
