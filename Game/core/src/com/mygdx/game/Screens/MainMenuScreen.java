package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Functions.Animation;
import com.mygdx.game.Levels.Level1;
import com.mygdx.game.Levels.Level2;
import com.mygdx.game.Levels.Level3;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.MyGdxGame.*;

public class MainMenuScreen implements Screen {
    final MyGdxGame game;
    public TextButton continueBtn, newGameBtn, optionsBtn, exitBtn;
    Stage stage;
    public static int currentLevel;
    Texture blueMC, greenMC;
    Animation anim1, anim2;
    GlyphLayout appName = new GlyphLayout();

    public MainMenuScreen(MyGdxGame game1) {
        this.game = game1;
        game.font.getData().setScale(2);
        appName.setText(game.font, "Special Forces game");

        blueMC = new Texture(Gdx.files.internal("Characters/Blue/Gunner_Blue_Idle_Right.png"));
        anim1 = new Animation(new TextureRegion(blueMC), 5, 0.5f);

        greenMC = new Texture(Gdx.files.internal("Characters/Green/Gunner_Green_Idle_Left.png"));
        anim2 = new Animation(new TextureRegion(greenMC), 5, 0.5f);

        continueBtn = new TextButton("Continue", game.skin);
        continueBtn.setPosition(0, 300);
        continueBtn.setSize(500, 100);
        continueBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (currentLevel == 2) game.setScreen(new Level2(game));
                else if (currentLevel == 3) game.setScreen(new Level3(game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        newGameBtn = new TextButton("New game", game.skin);
        newGameBtn.setPosition(0, 200);
        newGameBtn.setSize(500, 100);
        newGameBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Level1(game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        optionsBtn = new TextButton("Options", game.skin);
        optionsBtn.setPosition(0, 100);
        optionsBtn.setSize(500, 100);
        optionsBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionsScreen(game));
                dispose();
            }
        });

        exitBtn = new TextButton("Exit", game.skin);
        exitBtn.setPosition(0, 0);
        exitBtn.setSize(500, 100);
        exitBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(continueBtn);
        if (currentLevel <= 1) stage.clear();
        stage.addActor(newGameBtn);
        stage.addActor(exitBtn);
        stage.addActor(optionsBtn);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                game.batch.draw(game.bg, i * BG_PACK_WIDTH, j * BG_PACK_HEIGHT, BG_PACK_WIDTH, BG_PACK_HEIGHT);
            }
        }
        for (int i = 0; i < 3; i++)
            game.batch.draw(game.tile1, (i + 6) * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        for (int i = 0; i < 3; i++)
            game.batch.draw(game.tile1, (i + 9) * TILE_WIDTH, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        for (int i = 0; i < 6; i++) game.batch.draw(game.tile2, (i + 6) * TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
        for (int i = 0; i < 3; i++)
            game.batch.draw(game.tile2, (i + 9) * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);

        game.batch.draw(anim1.getFrame(), 7 * TILE_WIDTH, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(anim2.getFrame(), 10 * TILE_WIDTH, 3 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.font.draw(game.batch, appName, (SCREEN_WIDTH - appName.width) / 2, 700);
        game.batch.end();

        anim1.render(delta);
        anim2.render(delta);
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
        blueMC.dispose();
        greenMC.dispose();
    }
}
