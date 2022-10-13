package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.MyGdxGame.*;
import static com.mygdx.game.MyGdxGame.BG_PACK_HEIGHT;

public class LastScreen implements Screen {
    final MyGdxGame game;
    Texture t;
    float tWidth, tHeight;
    TextButton mainMenuBtn;
    Stage stage;
    GlyphLayout glyphLayout1 = new GlyphLayout();
    GlyphLayout glyphLayout2 = new GlyphLayout();
    GlyphLayout glyphLayout3 = new GlyphLayout();
    GlyphLayout glyphLayout4 = new GlyphLayout();
    GlyphLayout glyphLayout5 = new GlyphLayout();
    GlyphLayout glyphLayout6 = new GlyphLayout();

    public LastScreen(MyGdxGame game1) {
        this.game = game1;
        t = new Texture(Gdx.files.internal("Shape.png"));

        game.font.getData().setScale(1.25f);
        glyphLayout1.setText(game.font, "Hey! That was the last level.");
        glyphLayout2.setText(game.font, "And thank you for completing my game.");
        glyphLayout3.setText(game.font, "This is just demo version of game,");
        glyphLayout4.setText(game.font, "I will continue making more levels");
        glyphLayout5.setText(game.font, "and adding new mechanics at future.");
        glyphLayout6.setText(game.font, "<3");

        tWidth = MyGdxGame.SCREEN_WIDTH / 2f + 2 * TILE_WIDTH;
        tHeight = MyGdxGame.SCREEN_HEIGHT / 2f + TILE_HEIGHT;

        mainMenuBtn = new TextButton("Main Menu", game.skin);
        mainMenuBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 4f - TILE_WIDTH + 40, MyGdxGame.SCREEN_HEIGHT / 4f - TILE_HEIGHT / 2f + 40);
        mainMenuBtn.setSize(tWidth - 80, 100);
        mainMenuBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MainMenuScreen.currentLevel = 1;
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(mainMenuBtn);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                game.batch.draw(game.bg, i * BG_PACK_WIDTH, j * BG_PACK_HEIGHT, BG_PACK_WIDTH, BG_PACK_HEIGHT);
            }
        }
        game.batch.draw(t, MyGdxGame.SCREEN_WIDTH / 4f - TILE_WIDTH, MyGdxGame.SCREEN_HEIGHT / 4f - TILE_HEIGHT / 2f, tWidth, tHeight);
        game.font.draw(game.batch, glyphLayout1, (Gdx.graphics.getWidth() - glyphLayout1.width) / 2, 570);
        game.font.draw(game.batch, glyphLayout2, (Gdx.graphics.getWidth() - glyphLayout2.width) / 2, 520);
        game.font.draw(game.batch, glyphLayout3, (Gdx.graphics.getWidth() - glyphLayout3.width) / 2, 470);
        game.font.draw(game.batch, glyphLayout4, (Gdx.graphics.getWidth() - glyphLayout4.width) / 2, 420);
        game.font.draw(game.batch, glyphLayout5, (Gdx.graphics.getWidth() - glyphLayout5.width) / 2, 370);
        game.font.draw(game.batch, glyphLayout6, (Gdx.graphics.getWidth() - glyphLayout6.width) / 2, 320);
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        t.dispose();
    }
}
