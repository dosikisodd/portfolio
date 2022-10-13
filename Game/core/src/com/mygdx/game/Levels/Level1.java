package com.mygdx.game.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Functions.Bullet;
import com.mygdx.game.Functions.MC;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.BetweenLevelsScreen;
import com.mygdx.game.Screens.GameOverScreen;
import com.mygdx.game.Screens.MainMenuScreen;
import com.mygdx.game.Screens.OptionsScreen;


import static com.mygdx.game.MyGdxGame.*;

public class Level1 implements Screen {
    final MyGdxGame game;
    float i = 10000;
    int j;
    public static boolean paused;

    private MC mc;

    private OrthographicCamera camera;
    Stage stage;

    Texture t;
    float tWidth, tHeight;
    TextButton continueBtn, mainMenuBtn, optionsBtn;
    GlyphLayout glyphLayout = new GlyphLayout();

    public Rectangle doorBounds;
    public Rectangle[] upBounds = new Rectangle[2];
    public Rectangle[] leftBounds = new Rectangle[5];
    public Rectangle[] rightBounds = new Rectangle[4];
    public Rectangle[] groundBounds = new Rectangle[4];
    public Rectangle[] killBounds = new Rectangle[3];

    public Level1(MyGdxGame gam) {
        game = gam;
        MainMenuScreen.currentLevel = 1;
        paused = false;

        t = new Texture(Gdx.files.internal("Shape.png"));
        game.font.getData().setScale(2);
        glyphLayout.setText(game.font, "Paused");

        tWidth = MyGdxGame.SCREEN_WIDTH / 2f + 2 * TILE_WIDTH;
        tHeight = MyGdxGame.SCREEN_HEIGHT / 2f + TILE_HEIGHT;

        continueBtn = new TextButton("Continue", game.skin);
        continueBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 4f - TILE_WIDTH + 40, MyGdxGame.SCREEN_HEIGHT / 4f - TILE_HEIGHT / 2f + 40);
        continueBtn.setSize(tWidth / 2f - 80, 100);
        continueBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                paused = false;
                stage.clear();
                stage.addActor(game.upButton);
                stage.addActor(game.leftButton);
                stage.addActor(game.rightButton);
                stage.addActor(game.bulletButton);
                stage.addActor(game.pauseButton);
                game.music.play();
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
                game.music.play();
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });

        optionsBtn = new TextButton("Options", game.skin);
        optionsBtn.setPosition(MyGdxGame.SCREEN_WIDTH / 4f - TILE_WIDTH + 40, MyGdxGame.SCREEN_HEIGHT / 4f - TILE_HEIGHT / 2f + 160);
        optionsBtn.setSize(tWidth - 80, 100);
        optionsBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.music.play();
                game.setScreen(new OptionsScreen(game));
                dispose();
            }
        });

        mc = new MC(75, 2.5f * TILE_HEIGHT);
        j = 0;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        game.upButton.setSize(game.buttonSize, game.buttonSize);
        game.leftButton.setSize(game.buttonSize, game.buttonSize);
        game.rightButton.setSize(game.buttonSize, game.buttonSize);
        game.bulletButton.setSize(game.buttonSize, game.buttonSize);

        if (isControlRight) {
            game.upButton.setPosition(20, 20);
            game.leftButton.setPosition(SCREEN_WIDTH - game.buttonSize * 3 - 60, 20);
            game.rightButton.setPosition(SCREEN_WIDTH - game.buttonSize - 20, 20);
            game.bulletButton.setPosition(SCREEN_WIDTH - game.buttonSize * 2 - 40, 20);
        } else {
            game.upButton.setPosition(SCREEN_WIDTH - game.buttonSize - 20, 20);
            game.leftButton.setPosition(20, 20);
            game.rightButton.setPosition(60 + game.buttonSize * 2, 20);
            game.bulletButton.setPosition(40 + game.buttonSize, 20);
        }

        stage.addActor(game.upButton);
        stage.addActor(game.leftButton);
        stage.addActor(game.rightButton);
        stage.addActor(game.bulletButton);

        game.pauseButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                paused = true;
                stage.clear();
                stage.addActor(continueBtn);
                stage.addActor(mainMenuBtn);
                stage.addActor(optionsBtn);
                game.music.pause();
                if (MyGdxGame.isSfxOn) game.pauseSound.play(0.1f);
            }
        });
        stage.addActor(game.pauseButton);

        groundBounds[0] = new Rectangle(0, 2 * TILE_HEIGHT - 5, 4 * TILE_WIDTH - 10, 5);
        groundBounds[1] = new Rectangle(3 * TILE_WIDTH + 5, 3 * TILE_HEIGHT - 5, TILE_WIDTH - 10, 5);
        groundBounds[2] = new Rectangle(5 * TILE_WIDTH + 5, 4 * TILE_HEIGHT - 5, 2 * TILE_WIDTH - 10, 5);
        groundBounds[3] = new Rectangle(8 * TILE_WIDTH + 5, 3 * TILE_HEIGHT - 5, 2 * TILE_WIDTH - 10, 5);

        leftBounds[0] = new Rectangle(3 * TILE_WIDTH, 2 * TILE_HEIGHT, 5, TILE_HEIGHT - 5);
        leftBounds[1] = new Rectangle(5 * TILE_WIDTH, 3.5f * TILE_HEIGHT, 5, TILE_HEIGHT / 2 - 5);
        leftBounds[2] = new Rectangle(6 * TILE_WIDTH, 2 * TILE_HEIGHT, 5, TILE_HEIGHT - 5);
        leftBounds[3] = new Rectangle(8 * TILE_WIDTH, 2 * TILE_HEIGHT, 5, TILE_HEIGHT - 5);
        leftBounds[4] = new Rectangle(10 * TILE_WIDTH, 4.5f * TILE_HEIGHT + 5, 5, TILE_HEIGHT / 2 - 5);

        rightBounds[0] = new Rectangle(2 * TILE_WIDTH - 5, 4.5f * TILE_HEIGHT + 5, 5, TILE_HEIGHT / 2 - 5);
        rightBounds[1] = new Rectangle(4 * TILE_WIDTH - 5, TILE_HEIGHT, 5, 2 * TILE_HEIGHT - 5);
        rightBounds[2] = new Rectangle(7 * TILE_WIDTH - 5, 3.5f * TILE_HEIGHT, 5, TILE_HEIGHT / 2 - 5);
        rightBounds[3] = new Rectangle(10 * TILE_WIDTH - 5, TILE_HEIGHT, 5, 2 * TILE_HEIGHT - 5);

        upBounds[0] = new Rectangle(0, 4.5f * TILE_HEIGHT, 2 * TILE_WIDTH - 5, 5);
        upBounds[1] = new Rectangle(10 * TILE_WIDTH + 5, 4.5f * TILE_HEIGHT, 2 * TILE_WIDTH - 5, 5);

        killBounds[0] = new Rectangle(4 * TILE_WIDTH, 0, 2 * TILE_WIDTH, 2 * TILE_HEIGHT - 50);
        killBounds[1] = new Rectangle(7 * TILE_WIDTH, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT / 2);
        killBounds[2] = new Rectangle(10 * TILE_WIDTH, TILE_HEIGHT, 2 * TILE_WIDTH, TILE_HEIGHT - 50);

        doorBounds = new Rectangle(8 * TILE_WIDTH + 25, 3 * TILE_HEIGHT, TILE_WIDTH, 25);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                game.batch.draw(game.bg, i * BG_PACK_WIDTH, j * BG_PACK_HEIGHT, BG_PACK_WIDTH, BG_PACK_HEIGHT);
            }
        }

        game.batch.draw(game.saw, 7 * TILE_WIDTH, 1.5f * TILE_HEIGHT, TILE_WIDTH / 2, TILE_HEIGHT / 2, TILE_WIDTH, TILE_HEIGHT, 1, 1, i -= 5);
        if (i < 0) i += 10000;
        for (int i = 0; i < 4; i++) {
            game.batch.draw(game.tile2, i * TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
            game.batch.draw(game.tile1, i * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        }
        game.batch.draw(game.doorLocked, 50, 2 * TILE_HEIGHT, TILE_WIDTH, 1.8f * TILE_HEIGHT);
        game.batch.draw(game.doorOpened, 8 * TILE_WIDTH + 25, 3 * TILE_HEIGHT, TILE_WIDTH, 1.8f * TILE_HEIGHT);
        game.batch.draw(game.switchOff, 200, 2 * TILE_HEIGHT, game.switchOff.getWidth() / 2f, TILE_HEIGHT);
        game.batch.draw(game.acid2, 4 * TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.acid2, 5 * TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.acid1, 4 * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.acid1, 5 * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);

        for (int i = 6; i < 10; i++) {
            game.batch.draw(game.tile2, i * TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
        }
        for (Bullet bullet : MC.bullets)
            if (bullet.currentPosition.x != 0 && bullet.currentPosition.y != 0) {
                if (!paused) bullet.render(delta);
                bullet.bullet.draw(game.batch);
            }
        game.batch.draw(game.tile1, 10 * TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile1, 11 * TILE_WIDTH, 0, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.spike, 10 * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.spike, 11 * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile1, 6 * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile1, 7 * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile2, 8 * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile2, 9 * TILE_WIDTH, TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile1, 8 * TILE_WIDTH, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile1, 9 * TILE_WIDTH, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.switchOn, 8 * TILE_WIDTH + 175, 3 * TILE_HEIGHT, game.switchOn.getWidth() / 2f, TILE_HEIGHT);
        game.batch.draw(game.tile4, 5 * TILE_WIDTH, 3 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile4, 6 * TILE_WIDTH, 3 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.barrel2, 6 * TILE_WIDTH, 4 * TILE_HEIGHT, TILE_WIDTH / 1.5f, TILE_HEIGHT);
        for (int i = 0; i < 2; i++) {
            game.batch.draw(game.tile4, i * TILE_WIDTH, 4 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        }
        game.batch.draw(game.box, 0.5f * TILE_WIDTH, 5 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile4, 10 * TILE_WIDTH, 4 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile4, 11 * TILE_WIDTH, 4 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.tile5, 3 * TILE_WIDTH, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.barrel1, 11 * TILE_WIDTH, 5 * TILE_HEIGHT, TILE_WIDTH / 1.5f, TILE_HEIGHT);
        game.batch.draw(mc.getFrame(), mc.getMcPosition().x, mc.getMcPosition().y, MC.mcWidth, MC.mcHeight);
        game.batch.draw(game.fence2, 0, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.fence2, TILE_WIDTH, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.fence3, 2 * TILE_WIDTH, 2 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.fence1, 10 * TILE_WIDTH, 5 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        game.batch.draw(game.fence2, 11 * TILE_WIDTH, 5 * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        if (paused) {
            game.batch.draw(t, MyGdxGame.SCREEN_WIDTH / 4f - TILE_WIDTH, MyGdxGame.SCREEN_HEIGHT / 4f - TILE_HEIGHT / 2f, tWidth, tHeight);
            game.font.draw(game.batch, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2, 4 * TILE_HEIGHT + 50);
        }
        game.batch.end();

        if (!paused) mc.render(delta);
        stage.act();
        stage.draw();

        for (int i = 0; i < 4; i++) {
            if (MC.bounds.overlaps(groundBounds[i])) {
                j = i;
                MC.mcVelocity.set(0, -mc.gravity);
                MC.canJump = true;
                if (MC.mcPosition.y < groundBounds[i].y + 5) MC.mcPosition.y = groundBounds[i].y + 5;
            }
        }
        if (MC.mcPosition.y > groundBounds[j].y + 5 && !MC.bounds.overlaps(groundBounds[j])) MC.canJump = false;

        for (Rectangle leftBound : leftBounds) {
            if (MC.bounds.overlaps(leftBound)) {
                MC.mcPosition.add(-5, 0);
                if (MC.mcPosition.x + MC.mcWidth / 2f + 10 > leftBound.x)
                    MC.mcPosition.x = leftBound.x - MC.mcWidth / 2f - 10;
            }
            int i = 0;
            for (Bullet b : MC.bullets) {
                if (b.bulletBounds.overlaps(leftBound)) MC.bullets.removeIndex(i);
                i++;
            }
        }

        for (Rectangle rightBound : rightBounds) {
            if (MC.bounds.overlaps(rightBound)) {
                MC.mcPosition.add(5, 0);
                if (MC.mcPosition.x + MC.mcWidth / 2f - 10 < rightBound.x + 10)
                    MC.mcPosition.x = rightBound.x - MC.mcWidth / 2f + 20;
            }
            int i = 0;
            for (Bullet b : MC.bullets) {
                if (b.bulletBounds.overlaps(rightBound)) MC.bullets.removeIndex(i);
                i++;
            }
        }

        for (Rectangle upBound : upBounds) {
            if (MC.bounds.overlaps(upBound)) {
                MC.mcVelocity.set(0, 0);
                if (MC.mcPosition.y + MC.mcHeight > upBound.y) MC.mcPosition.y = upBound.y - MC.mcHeight;
            }
        }

        for (Rectangle killBound : killBounds) {
            if (MC.bounds.overlaps(killBound)) {
                stage.clear();
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        if (MC.bounds.overlaps(doorBounds)) {
            stage.clear();
            MainMenuScreen.currentLevel = 2;
            game.setScreen(new BetweenLevelsScreen(game));
            dispose();
        }
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
        mc.dispose();
        t.dispose();
    }
}