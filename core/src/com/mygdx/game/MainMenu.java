package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.Cube;

public class MainMenu implements Screen {
    final Cube game;
    Texture dayBg;
    public MainMenu(final Cube game) {
        this.game = game;
    }

    @Override
    public void show() {
        

    }

    @Override
    public void render(float delta) {
        
        dayBg = new Texture(Gdx.files.internal("daybg.jpg"));
    
        game.batch.begin();
        game.batch.draw(dayBg, 0 ,0);
        game.batch.draw(new Texture("DREAM.jpg"), 500 / 2 - 64 / 2, 250);
        game.font.draw(game.batch, "Score: 0", 670, 590);
        game.font.draw(game.batch, "High Score: " + game.highScore, 670, 570);
        game.font.draw(game.batch, "Hit Space to Start!", 300, 300);
        game.font.draw(game.batch, "Controls:", 5, 590);
        game.font.draw(game.batch, "Space to jump", 5, 550);
        game.font.draw(game.batch, "F to hold position", 5, 530);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
