package com.gamejam.helth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Helth extends Game {

    SpriteBatch batch;
    BitmapFont font;
    BitmapFont font2;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.

        font = new BitmapFont();
        font.getData().setScale(2, 2);
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }


    //
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}


