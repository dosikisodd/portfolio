package com.mygdx.game.Functions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    public float bulletSpeed = 10;
    public Sprite bullet;
    public Vector2 currentPosition, endPosition, dir;
    public Rectangle bulletBounds;

    public Bullet() {
        bullet = new Sprite(new Texture(Gdx.files.internal("Bullets/BlueBullet.png")));
        bullet.setSize(20, 20);
        currentPosition = new Vector2();
        endPosition = new Vector2();
        dir = new Vector2();
        bulletBounds = new Rectangle();
        bulletBounds.width = bulletBounds.height = 25;
    }

    public void fire(float x1, float y1, float x2, float y2) {
        currentPosition.set(x1, y1);
        endPosition.set(x2, y2);

        dir = endPosition.cpy();
        dir.sub(currentPosition);
        dir.setLength(bulletSpeed);
    }

    public void render(float dt) {
        currentPosition.set(currentPosition).add(dir);
        bullet.setPosition(currentPosition.x, currentPosition.y);
        bulletBounds.x = currentPosition.x;
        bulletBounds.y = currentPosition.y;
    }

    public void dispose() {

    }
}
