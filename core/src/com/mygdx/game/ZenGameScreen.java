package com.mygdx.game;

import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.Cube;

public class ZenGameScreen implements Screen {

    final Cube game;

    SpriteBatch batch;
    Texture square;

    Texture pipeImage;

    Rectangle player;
    Array<Rectangle> tPipes;
    Array<Rectangle> bPipes;
    long lastPipeSpawn;
    long lastEmptyMeter;
    double velocity = 0.0;
    double gravity = .6;
    int height = MathUtils.random(300, 600);
    int state = 0;
    int score = 0;

    BitmapFont font;
    Sound jumpSound;
    Sound failSound;
    boolean held = false;
    boolean heldCooldown = false;

    Texture nightBg1, nightBg2;
    float yBgMax, yBg1, yBg2;
    final int BACKGROUND_MOVE_SPEED = -50;

    public ZenGameScreen(final Cube game) {
        this.game = game;

        square = new Texture("DREAM.jpg");
        pipeImage = new Texture(Gdx.files.internal("pipe.jpg"));
        font = new BitmapFont();
        batch = new SpriteBatch();
        player = new Rectangle();
        player.x = 500 / 2 - 64 / 2;
        player.y = 250;
        player.width = 64;
        player.height = 64;
        tPipes = new Array<Rectangle>();
        bPipes = new Array<Rectangle>();
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
        failSound = Gdx.audio.newSound(Gdx.files.internal("lose.wav"));
        nightBg1 = new Texture(Gdx.files.internal("nightbg.jpg"));
        nightBg2 = new Texture(Gdx.files.internal("nightbg.jpg"));
        yBgMax = 800;
        yBg1 = 0;
        yBg2 = yBgMax + yBgMax;

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
    public void render(float delta) {
        yBg1 += BACKGROUND_MOVE_SPEED * Gdx.graphics.getDeltaTime();
        yBg2 = yBg1 + yBgMax;
        if (yBg1 <= yBgMax * (-1)) {
            yBg1 = 0;
            yBg2 = yBgMax + yBgMax;
        }

        batch.begin();
        batch.draw(nightBg1, yBg1, 0);
        batch.draw(nightBg2, yBg2, 0);
        batch.draw(square, player.x, player.y);

        for (Rectangle pipe : tPipes) {
            batch.draw(pipeImage, pipe.x, pipe.y);
        }

        for (Rectangle pipe : bPipes) {
            batch.draw(pipeImage, pipe.x, pipe.y);
        }

        font.draw(batch, "Score: " + score, 700, 590);
        font.draw(batch, "Zen Mode", 370, 550);
        font.draw(batch, "Press esc to Exit", 680, 550);
        font.draw(batch, "Press z to Restart", 670, 530);
        font.draw(batch, "Press r to Return to Regular Mode", 570, 510);
        font.draw(batch, "Controls:", 5, 590);
        font.draw(batch, "Space to jump", 5, 550);
        font.draw(batch, "F to hold position", 5, 530);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            velocity = -10;
            jumpSound.play();
        }
        if (Gdx.input.isKeyPressed(Keys.F) && TimeUtils.nanoTime() - lastEmptyMeter > 1000000000) {
            velocity = 0;
            gravity = 0;
        }
        if (TimeUtils.nanoTime() - lastEmptyMeter > 1000000000) {
            heldCooldown = false;
        }
        if (Gdx.input.isKeyPressed(Keys.R)) {
            game.setScreen(new MainMenu(game));

        }
        if (Gdx.input.isKeyPressed(Keys.Z)) {
            game.setScreen(new ZenGameScreen(game));
           
        }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            game.dispose();
        }


        velocity += gravity;
        player.y -= velocity;

        gravity = .6;

        if (player.y < 0)
            player.y = 0;

        if (player.y > 600 - 64)
            player.y = 600 - 64;

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
            height = MathUtils.random(300, 600);
        }

        for (Iterator<Rectangle> incr = bPipes.iterator(); incr.hasNext();) {
            Rectangle bPipe = incr.next();
            bPipe.x -= 400 * Gdx.graphics.getDeltaTime();
            if (bPipe.x + 100 < 0) {
                incr.remove();
            }

        }
        score += 1;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        square.dispose();

    }

}
