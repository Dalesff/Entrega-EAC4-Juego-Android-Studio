package eac4.alesf.objects;
// David Ales Fernandez
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import eac4.alesf.helpers.AssetManager;
import eac4.alesf.utils.Settings;

// TODO: Part 1, hacemos un refactor para que el codigo concuerde contextualmente con los nuevos sprites que utilizamos.
public class Helicopter extends Actor {

    // Distintes posicions de la spacecraft, recta, pujant i baixant
    public static final int HELICOPTER_STRAIGHT = 0;
    public static final int HELICOPTER_UP = 1;
    public static final int HELICOPTER_DOWN = 2;

    // Paràmetres de la spacecraft
    private Vector2 position;
    private int width, height;
    private int direction;

    private Rectangle collisionRect;

    private float runTime;

    private boolean pause;

    public Helicopter(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem la spacecraft a l'estat normal
        direction = HELICOPTER_STRAIGHT;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);

        pause = false;

    }

    public void act(float delta) {
        if (!pause) {
            super.act(delta);
            runTime += delta;

            // Movem la spacecraft depenent de la direcció controlant que no surti de la pantalla
            switch (direction) {
                case HELICOPTER_UP:
                    if (this.position.y - Settings.HELICOPTER_VELOCITY * delta >= 0) {
                        this.position.y -= Settings.HELICOPTER_VELOCITY * delta;
                    }
                    break;
                case HELICOPTER_DOWN:
                    if (this.position.y + height + Settings.HELICOPTER_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                        this.position.y += Settings.HELICOPTER_VELOCITY * delta;
                    }
                    break;
                case HELICOPTER_STRAIGHT:
                    break;
            }

            collisionRect.set(position.x, position.y + 3, width, 10);
            setBounds(position.x, position.y, width, height);
        }
    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció de la spacecraft: Puja
    public void goUp() {
        direction = HELICOPTER_UP;
    }

    // Canviem la direcció de la spacecraft: Baixa
    public void goDown() {
        direction = HELICOPTER_DOWN;
    }

    // Posem la spacecraft al seu estat original
    public void goStraight() {
        direction = HELICOPTER_STRAIGHT;
    }

    public void reset() {

        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.HELICOPTER_STARTX;
        position.y = Settings.HELICOPTER_STARTY;
        direction = HELICOPTER_STRAIGHT;
        collisionRect = new Rectangle();
    }
    // TODO: Part 1, aplicamos al metodo draw para que pueda dibujar la animación.
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) AssetManager.heliAnim.getKeyFrame(runTime, true), position.x, position.y, width, height);

    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    // TODO: Part 4, métodos para establecer o desactivar el estado pause en el actor helicopter.
    public void setPause() {
        pause = true;
    }
    public void setRun() {
        pause = false;
    }
}
