package com.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class mButton {
    int x, y, w, h, b = 2;
    Color color;
    String type;
    String text;
    BitmapFont font = new BitmapFont();
    GlyphLayout layout = new GlyphLayout();
    static final int bw = 150;
    static final int bh = 75;

    mButton(String type, int x, int y, int w, int h, Color color){
        this.type = type;
        this.text = type.equals("start") ? "Start" :
                    type.equals("about") ? "About" :
                    type.equals("achieve") ? "Achievements" :
                        type;
        this.color = color;
        font.setColor(Resources.inverse_color(color));
        while((layout.width < w - 4 * (float)w/10) && (layout.height < h - 3 * (float)h/10)){
            font.getData().setScale(font.getData().scaleX + 0.1f);
            layout.setText(
                    font,
                    text
            );
        }
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    void draw(SpriteBatch batch){
        batch.draw(Resources.create_texture(color), x, y, w, h);
        font.setColor(color);
        font.draw(batch, text, x + (float)w / 2 - (float)layout.width / 2 + 1, y + (float)h / 2 + (float)layout.height / 2 - 1);
        font.draw(batch, layout, x + (float)w / 2 - (float)layout.width / 2, y + (float)h / 2 + (float)layout.height / 2);
        batch.draw(Resources.create_texture(Resources.inverse_color(color)), x, y, w, b);
        batch.draw(Resources.create_texture(Resources.inverse_color(color)), x, y + h, w, b);
        batch.draw(Resources.create_texture(Resources.inverse_color(color)), x, y, b, h);
        batch.draw(Resources.create_texture(Resources.inverse_color(color)), x + w - b, y, b, h);
    }

    Rectangle hitbox() { return new Rectangle(x, y, w, h); }
}
