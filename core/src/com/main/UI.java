package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI {
    static BitmapFont font = new BitmapFont();
    static int money, wave, score, life, zk;

    static void draw(SpriteBatch b){
        font.getData().setScale(1.75f);
        font.setColor(Color.GOLD);
        font.draw(b, "Money: " + money, 2, 597);
        font.setColor(Color.MAGENTA);
        font.draw(b, "Wave: " + wave, 2, 597 - 12 * font.getData().scaleX - 3);
        font.setColor(Color.CYAN);
        font.draw(b, "Score: " + score, 2, 597 - 12 * font.getData().scaleX * 2 - 6);
        font.setColor(Color.GREEN);
        font.draw(b, "Life: " + life, 2, 597 - 12 * font.getData().scaleX * 3 - 7);
        //font.getData().setScale(1f);
        font.setColor(Color.YELLOW);
        font.draw(b, "ZK: " + Main.p.getInteger("zk"), 2, 597 - 12 * font.getData().scaleX * 4 - 8);
        font.getData().setScale(1f);
    }
}
