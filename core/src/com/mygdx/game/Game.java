package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture square;
	Texture bg;
	Texture pipeImage;
	Rectangle player;
	Array<Rectangle> tPipes;
	Array<Rectangle> bPipes;
	long lastPipeSpawn;
	double velocity = 0.0;
	double gravity = .6;
	int height = MathUtils.random(300, 600);
	boolean behindPipe = false;

	@Override
	public void create() {
		square = new Texture("black.jpg");
		pipeImage = new Texture(Gdx.files.internal("pipe.jpg"));

		// camera = new OrthographicCamera();

		// camera.setToOrtho(false, 800, 800);

		batch = new SpriteBatch();
		player = new Rectangle();
		player.x = 500 / 2 - 64 / 2;
		player.y = 50;
		player.width = 64;
		player.height = 64;

		tPipes = new Array<Rectangle>();
		bPipes = new Array<Rectangle>();
		spawnTPipe();
		spawnBPipe();

	}

	private void spawnTPipe() {
		Rectangle pipe = new Rectangle();
		pipe.x = 800;
		pipe.y = height;
		pipe.width = 70;
		pipe.height = 600;
		tPipes.add(pipe);
		lastPipeSpawn = TimeUtils.nanoTime();
	}

	private void spawnBPipe() {
		Rectangle pipe = new Rectangle();
		pipe.x = 800;
		pipe.y = height - 800;
		pipe.width = 70;
		pipe.height = 600;
		bPipes.add(pipe);
		lastPipeSpawn = TimeUtils.nanoTime();
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 1, 0);

		batch.begin();

		batch.draw(square, player.x, player.y);
		
		for (Rectangle pipe : tPipes) {
			batch.draw(pipeImage, pipe.x, pipe.y);
		}

		for (Rectangle pipe : bPipes) {
			batch.draw(pipeImage, pipe.x, pipe.y);
		}

		batch.end();

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			velocity = -10;
		}

		velocity += gravity;
		player.y -= velocity;

		if (player.y < 0)
			player.y = 0;

		if (player.y > 540)
			player.y = 540;

		if (TimeUtils.nanoTime() - lastPipeSpawn > 600000000) {
			spawnTPipe();
			spawnBPipe();
		}

		for (Iterator<Rectangle> incr = tPipes.iterator(); incr.hasNext();) {
			Rectangle tPipe = incr.next();
			tPipe.x -= 400 * Gdx.graphics.getDeltaTime();
			if (tPipe.x + 100 < 0) {
				incr.remove();	
			}
			if (tPipe.overlaps(player)) {
				System.out.println("You died");
			}
			height = MathUtils.random(300, 600);
		}
		
		for (Iterator<Rectangle> incr = bPipes.iterator(); incr.hasNext();) {
			Rectangle bPipe = incr.next();
			bPipe.x -= 400 * Gdx.graphics.getDeltaTime();
			if (bPipe.x + 100 < 0) {
				incr.remove();
			}
			if (player.overlaps(bPipe)) {
				System.out.println("You died");
			}
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		square.dispose();
	}
}
