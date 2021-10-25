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

	//TODO: GAME LISTS
	ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	ArrayList<Cannon> cannons = new ArrayList<Cannon>();
	ArrayList<Button> buttons = new ArrayList<Button>();

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
		// END DRAWING CODE BEFORE THIS
		batch.end();
	}

	void update(){
		tap();
		for(Zombie z : zombies) z.update();
		for(Cannon c : cannons) c.update();
		for(Button b : buttons) b.update();

		//clean up after updates
		housekeeping();
		spawn_zombies();
	}

	void housekeeping(){
		for(Zombie z : zombies) if(!z.active) { zombies.remove(z); break; }
	}

	void tap(){
		if(Gdx.input.justTouched()){
			int x = Gdx.input.getX(), y = Gdx.graphics.getHeight() - Gdx.input.getY();

			for(Cannon c : cannons) if(c.hitbox().contains(x, y)) return;
			if(buildable(x, y)) cannons.add(new Cannon("ccc", x, y));
		}
	}

	boolean buildable(int x, int y){
		return (((y > 0 && y < 200) || (y > 300 && y < 500)) && x < 1000);
	}

	void setup(){
		Tables.init();
		spawn_zombies();
		for (int i = 0; i < 5; i++) {
			buttons.add(new Button("bbb", 50 + i * 100, 525));
		}
	}

	void spawn_zombies(){
		if(!zombies.isEmpty()) return;
		for (int i = 0; i < 15; i++) {
			zombies.add(new Zombie("zzz", 1024 + i * 50, r.nextInt(450), 3));
		}
	}

	//*******************END OF FILE*******************
	@Override
	public void dispose () {
		batch.dispose();
	}
}
