package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.MyGdxGame.*;

public class OptionsScreen implements Screen {
    final MyGdxGame game;
    Stage stage;
    TextButton musicOn, musicOff, sfxOn, sfxOff, control1, control2, back;
    String music, sfx, control;
    Skin skin = new Skin(Gdx.files.internal("glassy/glassy-ui.json"));

    Slider buttonScale = new Slider(80, 120, 2, false, skin);

    public OptionsScreen(MyGdxGame game1) {
        this.game = game1;
        game.font.getData().setScale(1.5f);

        musicOn = new TextButton("On", game.skin);
        musicOn.setPosition(8 * TILE_WIDTH, 3.1f * TILE_HEIGHT);
        musicOn.setSize(1.5f * TILE_WIDTH, 0.8f * TILE_HEIGHT);
        musicOn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MyGdxGame.isMusicOn = true;
                game.music.play();
            }
        });

        musicOff = new TextButton("Off", game.skin);
        musicOff.setPosition(10 * TILE_WIDTH, 3.1f * TILE_HEIGHT);
        musicOff.setSize(1.5f * TILE_WIDTH, 0.8f * TILE_HEIGHT);
        musicOff.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MyGdxGame.isMusicOn = false;
            }
        });

        sfxOn = new TextButton("On", game.skin);
        sfxOn.setPosition(8 * TILE_WIDTH, 2.1f * TILE_HEIGHT);
        sfxOn.setSize(1.5f * TILE_WIDTH, 0.8f * TILE_HEIGHT);
        sfxOn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MyGdxGame.isSfxOn = true;
            }
        });

        sfxOff = new TextButton("Off", game.skin);
        sfxOff.setPosition(10 * TILE_WIDTH, 2.1f * TILE_HEIGHT);
        sfxOff.setSize(1.5f * TILE_WIDTH, 0.8f * TILE_HEIGHT);
        sfxOff.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MyGdxGame.isSfxOn = false;
            }
        });

        control1 = new TextButton("R", game.skin);
        control1.setPosition(8 * TILE_WIDTH, 1.1f * TILE_HEIGHT);
        control1.setSize(1.5f * TILE_WIDTH, 0.8f * TILE_HEIGHT);
        control1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MyGdxGame.isControlRight = true;
            }
        });

        control2 = new TextButton("L", game.skin);
        control2.setPosition(10 * TILE_WIDTH, 1.1f * TILE_HEIGHT);
        control2.setSize(1.5f * TILE_WIDTH, 0.8f * TILE_HEIGHT);
        control2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MyGdxGame.isControlRight = false;
            }
        });

        back = new TextButton("Back", game.skin);
        back.setPosition(0, 5.1f * TILE_HEIGHT);
        back.setSize(2 * TILE_WIDTH, 0.8f * TILE_HEIGHT);
        back.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        buttonScale.setValue(game.buttonSize);
        Container<Slider> container = new Container<>(buttonScale);
        container.setTransform(true);
        container.size(3.5f * TILE_WIDTH, 0.8f * TILE_HEIGHT);
        container.setOrigin(container.getWidth() / 2, container.getHeight() / 2);
        container.setPosition(1090, 60);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(musicOn);
        stage.addActor(musicOff);
        stage.addActor(sfxOn);
        stage.addActor(sfxOff);
        stage.addActor(control1);
        stage.addActor(control2);
        stage.addActor(back);
        stage.addActor(container);
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
        game.font.draw(game.batch, "Music: " + music, 20, 3 * TILE_HEIGHT + 75);
        game.font.draw(game.batch, "Sfx: " + sfx, 20, 2 * TILE_HEIGHT + 75);
        game.font.draw(game.batch, "Control type: " + control, 20, TILE_HEIGHT + 75);
        game.font.draw(game.batch, "Button width/height: " + game.buttonSize, 20, 75);
        game.batch.end();

        stage.act();
        stage.draw();

        if (MyGdxGame.isMusicOn) music = "On";
        else music = "Off";
        if (MyGdxGame.isSfxOn) sfx = "On";
        else sfx = "Off";
        if (MyGdxGame.isControlRight) control = "Right";
        else control = "Left";
        game.buttonSize = buttonScale.getValue();
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
        skin.dispose();
    }
}
