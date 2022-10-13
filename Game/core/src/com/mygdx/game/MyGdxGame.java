package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Functions.MC;
import com.mygdx.game.Screens.LastScreen;
import com.mygdx.game.Screens.MainMenuScreen;
import com.mygdx.game.Screens.OptionsScreen;

public class MyGdxGame extends Game {
    public static float SCREEN_WIDTH;
    public static float SCREEN_HEIGHT;

    public Texture bg,
            tile1, tile2, tile3, tile4, tile5, tile6,
            doorLocked, doorOpened,
            switchOn, switchOff,
            acid1, acid2, spike,
            fence1, fence2, fence3,
            barrel1, barrel2, box;
    public TextureRegion saw, cannon;
    public static float BG_PACK_WIDTH, BG_PACK_HEIGHT, TILE_WIDTH, TILE_HEIGHT;

    public Skin upSkin, rightSkin, leftSkin, bulletSkin, skin;
    public ImageButton upButton, leftButton, rightButton, bulletButton, pauseButton;

    public BitmapFont font;
    public SpriteBatch batch;

    public static boolean isMusicOn, isSfxOn, isControlRight;
    public float buttonSize;
    public Music music;
    public Sound gameOverSound, pauseSound;

    @Override
    public void create() {
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();

        buttonSize = 80;
        isMusicOn = true;
        isSfxOn = true;
        isControlRight = true;

        upSkin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));
        leftSkin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));
        rightSkin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));
        bulletSkin = new Skin(Gdx.files.internal("craftacular/craftacular-ui.json"));
        skin = new Skin(Gdx.files.internal("glassy/glassy-ui.json"));

        upButton = new ImageButton(upSkin);
        upButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/ButtonUp.png"))));
        upButton.setSize(buttonSize, buttonSize);
        upButton.setPosition(20, 20);
        upButton.addListener(MC.BUTTON_UP);

        leftButton = new ImageButton(leftSkin);
        leftButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/ButtonLeft.png"))));
        leftButton.setSize(buttonSize, buttonSize);
        leftButton.setPosition(SCREEN_WIDTH - buttonSize * 3 - 60, 20);
        leftButton.addListener(MC.BUTTON_LEFT);

        rightButton = new ImageButton(rightSkin);
        rightButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/ButtonRight.png"))));
        rightButton.setSize(buttonSize, buttonSize);
        rightButton.setPosition(SCREEN_WIDTH - buttonSize - 20, 20);
        rightButton.addListener(MC.BUTTON_RIGHT);

        bulletButton = new ImageButton(bulletSkin);
        bulletButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/ButtonBullet.png"))));
        bulletButton.setSize(buttonSize, buttonSize);
        bulletButton.setPosition(SCREEN_WIDTH - buttonSize * 2 - 40, 20);
        bulletButton.addListener(MC.BUTTON_BULLET);

        pauseButton = new ImageButton(skin);
        pauseButton.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/pauseBtn.png"))));
        pauseButton.setSize(150, 100);
        pauseButton.setPosition(SCREEN_WIDTH - 170, SCREEN_HEIGHT - 120);

        BG_PACK_WIDTH = SCREEN_WIDTH / 6;
        BG_PACK_HEIGHT = SCREEN_HEIGHT / 3;

        TILE_WIDTH = BG_PACK_WIDTH / 2;
        TILE_HEIGHT = BG_PACK_HEIGHT / 2;

        bg = new Texture(Gdx.files.internal("BgSprites/Tiles/BgPack.png"));

        tile1 = new Texture(Gdx.files.internal("BgSprites/Tiles/Tile1.png"));
        tile2 = new Texture(Gdx.files.internal("BgSprites/Tiles/Tile2.png"));
        tile3 = new Texture(Gdx.files.internal("BgSprites/Tiles/Tile3.png"));
        tile4 = new Texture(Gdx.files.internal("BgSprites/Tiles/Tile4.png"));
        tile5 = new Texture(Gdx.files.internal("BgSprites/Tiles/Tile5.png"));
        tile6 = new Texture(Gdx.files.internal("BgSprites/Tiles/Tile6.png"));

        doorLocked = new Texture(Gdx.files.internal("BgSprites/Objects/DoorLocked.png"));
        doorOpened = new Texture(Gdx.files.internal("BgSprites/Objects/DoorOpen.png"));

        switchOn = new Texture(Gdx.files.internal("BgSprites/Objects/SwitchOn.png"));
        switchOff = new Texture(Gdx.files.internal("BgSprites/Objects/SwitchOff.png"));

        acid1 = new Texture(Gdx.files.internal("BgSprites/Tiles/Acid1.png"));
        acid2 = new Texture(Gdx.files.internal("BgSprites/Tiles/Acid2.png"));
        spike = new Texture(Gdx.files.internal("BgSprites/Tiles/Spike.png"));

        fence1 = new Texture(Gdx.files.internal("BgSprites/Tiles/Fence1.png"));
        fence2 = new Texture(Gdx.files.internal("BgSprites/Tiles/Fence2.png"));
        fence3 = new Texture(Gdx.files.internal("BgSprites/Tiles/Fence3.png"));
        saw = new TextureRegion(new Texture(Gdx.files.internal("BgSprites/Objects/Saw.png")));
        cannon = new TextureRegion(new Texture(Gdx.files.internal("BgSprites/Objects/Cannon.png")));

        barrel1 = new Texture(Gdx.files.internal("BgSprites/Objects/Barrel1.png"));
        barrel2 = new Texture(Gdx.files.internal("BgSprites/Objects/Barrel2.png"));
        box = new Texture(Gdx.files.internal("BgSprites/Objects/Box.png"));

        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();

        music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/bgTheme.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/gameOver.ogg"));
        pauseSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/pauseSound.ogg"));

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
        if (!isMusicOn) music.pause();
    }

    @Override
    public void dispose() {
        bg.dispose();
        tile1.dispose();
        tile2.dispose();
        tile3.dispose();
        tile4.dispose();
        tile5.dispose();
        tile6.dispose();
        doorOpened.dispose();
        doorLocked.dispose();
        switchOn.dispose();
        switchOff.dispose();
        acid1.dispose();
        acid2.dispose();
        spike.dispose();
        barrel1.dispose();
        barrel2.dispose();
        fence1.dispose();
        fence2.dispose();
        fence3.dispose();
        box.dispose();
        batch.dispose();
        upSkin.dispose();
        rightSkin.dispose();
        leftSkin.dispose();
        bulletSkin.dispose();
        skin.dispose();
        font.dispose();
        music.dispose();
        gameOverSound.dispose();
        pauseSound.dispose();
        super.dispose();
    }
}
