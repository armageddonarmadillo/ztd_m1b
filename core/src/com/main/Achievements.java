package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class Achievements extends Scene {
    mButton m1;
    aTile tile = new aTile(130, 380, 64, 64, Color.GOLD);
    ArrayList<aTile> tiles = new ArrayList<aTile>();
    Color color = new Color(0x5010cc00);
    int[] box =  { 100, 475, 1024 - 200, 300};

    Achievements(){
        m1 = new mButton("Back", (1024 / 2) - (mButton.bw / 2), 75, mButton.bw, mButton.bh, Resources.inverse_color(new Color(0x5010aa00)));
        int y = 380, x = 130, wh = 64, b = 24;
        for(int i = 0; i < 27; i++){
            int wlimit = box[2] / (wh + b);
            tiles.add(new aTile(x, y, wh, wh, Color.GOLD));
            x = 130 + (wh + b) * (tiles.size() % wlimit);
            if(x == 130) y -= wh + b;
        }
    }

    void draw(SpriteBatch batch){
        //clear
        ScreenUtils.clear(color);
        //scene box
        batch.draw(Resources.light_gray, 1024 - 10 - 10, 10, 10, 600 - 10 - 10);
        batch.draw(Resources.dark_gray, 10, 10, 1024 - 10 - 10, 10);
        batch.draw(Resources.dark_gray, 10, 10, 10, 600 - 10 - 10);
        batch.draw(Resources.light_gray, 10, 600 - 10 - 10, 1024 - 10 - 10, 10);
        //achievement box
        batch.draw(Resources.create_texture(Resources.darken_color(color)), box[0], box[1] - box[3], box[2], box[3]);
        m1.draw(batch);
        for(aTile t : tiles) t.draw(batch);
    }

    void tap(int x, int y){
        for(aTile t : tiles)
            if(t.expanded && t.bigbox().contains(x, y)) {
                if (t.close.hitbox().contains(x, y)) {
                    t.expanded = false;
                    aTile.is_expanded = false;
                }
                return;
            } else if(!aTile.is_expanded && t.hitbox().contains(x, y)) {
                t.expanded = true;
                aTile.is_expanded = true;
                return;
            }
        if(!aTile.is_expanded && m1.hitbox().contains(x, y)) Main.achievements = false;
    }
}

/*ACHIEVEMENTS:
    - Zombie Kills > 100                                [ZK] -> "Zombie Slayer", "Zombie Annihilator", "Zombie Reaper"
    - Cannons Created > 50                              [CC] -> "The Builder", "The Creator", "The Mastermind"
    - Kill Rare Zombie (pick a rare zombie for this)    [RZ] -> "Zombie Hunter"
    - Reach Wave X without taking damage                [WX] (x = 5, 10, 15, etc) -> "Survivor", "Indomitable", "Invincible", "Maniac"
    - Destroy Cannons > 100                             [DC] -> "Refund!"
    - Unlock ALL Cannons                                [UA] -> "So many choices!"
 */

/*
    values.put("achieve_zk", "ZK")
    values.put("achieve_cc", "CC")
    values.put("achieve_rz", "RZ")
    values.put("achieve_wx", "WX")
    values.put("achieve_dc", "DC")
    values.put("achieve_ua", "UA")

    values.put("achievement_zk", "Zombie Slayer")
    values.put("achievement_cc", "The Builder")
    values.put("achievement_rz", "Zombie Hunter")
    values.put("achievement_wx", "Survivor")
    values.put("achievement_dc", "Refund!")
    values.put("achievement_ua", "So many choices!")
 */
