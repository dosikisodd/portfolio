package com.mygdx.game.Functions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Levels.Level1;
import com.mygdx.game.Levels.Level2;
import com.mygdx.game.Levels.Level3;
import com.mygdx.game.MyGdxGame;

import java.util.Iterator;

import static com.mygdx.game.MyGdxGame.TILE_HEIGHT;
import static com.mygdx.game.MyGdxGame.TILE_WIDTH;

public class MC {
    public static boolean rightIsTouched, leftIsTouched, watchingRight, canJump;
    public int gravity;
    public static Texture rightIdle, leftIdle;
    public static Vector2 mcPosition, mcVelocity;
    public static Array<Bullet> bullets;
    public static Rectangle bounds;
    public static float mcWidth, mcHeight;
    public static Animation mcAnim;
    public static Sound fireSound;

    public MC(float x, float y) {
        gravity = -Gdx.graphics.getHeight() / 30;
        mcPosition = new Vector2(x, y);
        mcVelocity = new Vector2(0, 0);

        watchingRight = true;

        mcWidth = TILE_WIDTH;
        mcHeight = TILE_HEIGHT;
        bullets = new Array<>();

        bounds = new Rectangle();
        bounds.width = mcWidth / 2f + 10;
        bounds.height = mcHeight;

        rightIdle = new Texture(Gdx.files.internal("Characters/Blue/Gunner_Blue_Idle_Right.png"));
        leftIdle = new Texture(Gdx.files.internal("Characters/Blue/Gunner_Blue_Idle_Left.png"));

        fireSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/fireSound.ogg"));
        mcAnim = new Animation(new TextureRegion(rightIdle), 5, 0.5f);
    }

    public Vector2 getMcPosition() {
        return mcPosition;
    }

    public TextureRegion getFrame() {
        return mcAnim.getFrame();
    }

    public static InputListener BUTTON_UP = new InputListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (canJump) mcVelocity.y = 700;
            return true;
        }
    };

    public static InputListener BUTTON_LEFT = new InputListener() {

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            leftIsTouched = false;
            mcAnim = new Animation(new TextureRegion(leftIdle), 5, 0.5f);
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            leftIsTouched = true;
            if (!watchingRight) mcPosition.add(30, 0);
            mcPosition.sub(30, 0);
            watchingRight = false;
            mcAnim = new Animation(new TextureRegion(leftIdle), 5, 0.5f);
            return true;
        }
    };

    public static InputListener BUTTON_RIGHT = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            rightIsTouched = true;
            mcPosition.add(30, 0);
            if (watchingRight) mcPosition.sub(30, 0);
            watchingRight = true;
            mcAnim = new Animation(new TextureRegion(rightIdle), 5, 0.5f);
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            rightIsTouched = false;
            mcAnim = new Animation(new TextureRegion(rightIdle), 5, 0.5f);
        }
    };

    public static InputListener BUTTON_BULLET = new InputListener() {
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Bullet b = new Bullet();
            if (watchingRight) b.fire(mcPosition.x + mcWidth, mcPosition.y + mcHeight / 2 - 10,
                    Gdx.graphics.getWidth(), mcPosition.y + mcHeight / 2);
            if (!watchingRight) b.fire(mcPosition.x, mcPosition.y + mcHeight / 2 - 10,
                    0, mcPosition.y + mcHeight / 2);
            bullets.add(b);
            if (MyGdxGame.isSfxOn) fireSound.play(0.1f);
            return true;
        }
    };

    public void render(float dt) {
        mcVelocity.add(0, gravity);
        mcVelocity.scl(dt);
        mcPosition.add(0, mcVelocity.y);
        mcVelocity.scl(1 / dt);

        if (mcPosition.y < 0) mcPosition.y = 0;
        if (mcPosition.x < 0) mcPosition.x = 0;
        if (mcPosition.x > Gdx.graphics.getWidth() - mcWidth) mcPosition.x = Gdx.graphics.getWidth() - mcWidth;

        if (rightIsTouched) mcPosition.add(5, 0);
        if (leftIsTouched) mcPosition.add(-5, 0);

        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet b = iterator.next();
            if (b.currentPosition.x < 0 || b.currentPosition.x > Gdx.graphics.getWidth()) iterator.remove();
        }

        if (watchingRight) bounds.x = mcPosition.x;
        if (!watchingRight) bounds.x = mcPosition.x + bounds.width - 20;
        bounds.y = mcPosition.y;

        if(!Level1.paused || !Level2.paused || !Level3.paused) mcAnim.render(dt);
    }

    public void dispose() {
        rightIdle.dispose();
        fireSound.dispose();
    }
}