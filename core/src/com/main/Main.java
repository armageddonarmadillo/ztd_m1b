package com.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {
	//TODO: GAME VARIABLES / OBJECTS
	SpriteBatch batch;
	static Random r = new Random();
	static String current_type = "";

	//TODO: GAME LISTS
	static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	static ArrayList<Cannon> cannons = new ArrayList<Cannon>();
	static ArrayList<Button> buttons = new ArrayList<Button>();
	static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	static ArrayList<Effect> effects = new ArrayList<Effect>();
	static ArrayList<Wall> walls = new ArrayList<Wall>();

	//CREATE RUNS *ONCE* WHEN THE APPLICATION STARTS / OPENS
	@Override
	public void create () {
		batch = new SpriteBatch();
		setup();
	}

	//RENDER RUNS ONCE PER FRAME
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		//right before drawing
		update();
		// ADD DRAWING CODE AFTER THIS
		batch.begin();
		batch.draw(Resources.bg, 0, 0);
		for(Zombie z : zombies) z.draw(batch);
		for(Cannon c : cannons) c.draw(batch);
		for(Button b : buttons) b.draw(batch);
		for(Bullet b : bullets) b.draw(batch);
		for(Effect e : effects) e.draw(batch);
		for(Wall w : walls) w.draw(batch);
		// END DRAWING CODE BEFORE THIS
		batch.end();
	}

	void update(){
		tap();
		for(Zombie z : zombies) z.update();
		for(Cannon c : cannons) c.update();
		for(Button b : buttons) b.update();
		for(Bullet b : bullets) b.update();
		for(Wall w : walls) w.update();
		//clean up after updates
		housekeeping();
		spawn_zombies();
	}

	void housekeeping(){
		for(Zombie z : zombies) if(!z.active) { zombies.remove(z); break; }
		for(Bullet b : bullets) if(!b.active) { bullets.remove(b); break; }
		for(Effect e : effects) if(!e.active) { effects.remove(e); break; }
		for(Wall w : walls) if(!w.active) { walls.remove(w); break; }
	}

	void tap(){
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX(), y = Gdx.graphics.getHeight() - Gdx.input.getY();

			effects.add(new Effect("boom", x, y));

			for(Button b : buttons) {
				if (b.t != null && !b.t.hidden && b.t.close.hitbox().contains(x, y)) { b.t.hidden = true; return; }
				if (b.t != null && !b.t.hidden && b.t.hitbox().contains(x, y)) return;
				if (b.hitbox().contains(x, y)) {
					if (b.locked) {
						if (b.t.hidden) {
							hidett();
							b.t.hidden = false;
						} else {
							b.locked = false;
							b.t.hidden = true;
						}
						return;
					} else {
						if(b.type.equals("wall") || b.type.equals("mounted")) {
							if(walls.size() < 3) walls.add(new Wall(walls.size() * 50, 0, b.type.equals("mounted")));
							return;
						}
						hidett();
						deselect();
						b.selected = true;
						current_type = b.type;
					}
					return;
				}
			}

			for(Cannon c : cannons) if(c.hitbox().contains(x, y)) return;
			if(buildable(x, y)) cannons.add(new Cannon(current_type, x, y));
		}
	}

	void deselect(){
		for(Button b : buttons) b.selected = false;
	}

	void hidett(){
		for(Button b : buttons) if(b.t != null) b.t.hidden = true;
	}

	boolean buildable(int x, int y){
		return (((y > 0 && y < 200) || (y > 300 && y < 500)) && x < 1000);
	}

	void setup(){
		Tables.init();
		spawn_zombies();
		buttons.add(new Button("bbb", 50 + buttons.size() * 75, 525));
		buttons.get(buttons.size() - 1).selected = true;
		buttons.get(buttons.size() - 1).locked = false;
		buttons.add(new Button("fire", 50 + buttons.size() * 75, 525));
		buttons.add(new Button("super", 50 + buttons.size() * 75, 525));
		buttons.add(new Button("double", 50 + buttons.size() * 75, 525));
		buttons.add(new Button("laser", 50 + buttons.size() * 75, 525));
		buttons.add(new Button("wall", 50 + buttons.size() * 75, 525));
		buttons.get(buttons.size() - 1).locked = false;
		buttons.add(new Button("mounted", 50 + buttons.size() * 75, 525));

	}

	void spawn_zombies(){
		if(!zombies.isEmpty()) return;
		for (int i = 0; i < 10; i++) {
			zombies.add(new Zombie("wizard_blue", 1024 + i * 50, r.nextInt(450)));
		}
	}

	//*******************END OF FILE*******************
	@Override
	public void dispose () {
		batch.dispose();
	}
}
