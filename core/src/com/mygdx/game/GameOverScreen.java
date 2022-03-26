package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.mygdx.Cube;

public class GameOverScreen implements Screen {
    final Cube game;

    public GameOverScreen(final Cube game) {
        this.game = game;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.font.draw(game.batch, "You died!\n     Press r to restart.\n        Press esc to Exit", 300, 300);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.dispose();
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
