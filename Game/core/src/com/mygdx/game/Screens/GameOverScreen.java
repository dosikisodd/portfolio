package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Levels.Level1;
import com.mygdx.game.Levels.Level2;
import com.mygdx.game.Levels.Level3;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.MyGdxGame.*;
import static com.mygdx.game.MyGdxGame.TILE_HEIGHT;

public class GameOverScreen implements Screen {
    final MyGdxGame game;
    Texture t;
    float tWidth, tHeight;

    TextButton tryAgainBtn, mainMenuBtn;
    Stage stage;

    GlyphLayout glyphLayout1 = new GlyphLayout();
    GlyphLayout glyphLayout2 = new GlyphLayout();

    public GameOverScreen(MyGdxGame game1) {
        this.game = game1;
        game.music.stop();
        if (MyGdxGame.isSfxOn) game.gameOverSound.play(0.1f);

        t = new Texture(Gdx.files.internal("Shape.png"));

        game.font.getData().setScale(2);
        glyphLayout1.setText(game.font, "You died :(");
        glyphLayout2.setText(game.font, "Better luck next time.");

        tWidth = MyGdxGame.SCREEN_WIDTH / 2f + 2 * TILE_WIDTH;
        tHeight = MyGdxGame.SCREEN_HEIGHT / 2f + TILE_HEIGHT;

        tryAgainBtn = new TextButton("Try again", game.skin);
        tryAgainBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 4f - TILE_WIDTH + 40, MyGdxGame.SCREEN_HEIGHT / 4f - TILE_HEIGHT / 2f + 40);
        tryAgainBtn.setSize(tWidth / 2f - 80, 100);
        tryAgainBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(MainMenuScreen.currentLevel == 1) game.setScreen(new Level1(game));
                else if(MainMenuScreen.currentLevel == 2) game.setScreen(new Level2(game));
                else if(MainMenuScreen.currentLevel == 3) game.setScreen(new Level3(game));
                game.music.play();
                dispose();
            }
        });

        mainMenuBtn = new TextButton("Main Menu", game.skin);
        mainMenuBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 4f - TILE_WIDTH + tWidth / 2f + 40, MyGdxGame.SCREEN_HEIGHT / 4f - TILE_HEIGHT / 2f + 40);
        mainMenuBtn.setSize(tWidth / 2f - 80, 100);
        mainMenuBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                game.music.play();
                dispose();
            }
        });

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(tryAgainBtn);
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
            game.batch.draw(t,
                    MyGdxGame.SCREEN_WIDTH / 4f - TILE_WIDTH,
                    MyGdxGame.SCREEN_HEIGHT / 4f - TILE_HEIGHT / 2f,
                    tWidth, tHeight);
            game.font.draw(game.batch, glyphLayout1, (Gdx.graphics.getWidth() - glyphLayout1.width) / 2, 4 * TILE_HEIGHT + 50);
            game.font.draw(game.batch, glyphLayout2, (Gdx.graphics.getWidth() - glyphLayout2.width) / 2, 3 * TILE_HEIGHT + 50);
        }
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
