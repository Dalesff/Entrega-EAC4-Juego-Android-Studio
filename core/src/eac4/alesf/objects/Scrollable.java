package eac4.alesf.objects;
// David Ales Fernandez
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

public class Scrollable extends Actor {

    protected Vector2 position;
    protected float velocity;
    protected float width;
    protected float height;
    protected boolean leftOfScreen;
    // TODO: Part 4, variable de pausa.
    protected boolean pause;

    public Scrollable(float x, float y, float width, float height, float velocity) {
        position = new Vector2(x, y);
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        leftOfScreen = false;
        pause = false;

    }

    public void act(float delta) {

        if (!pause) {
            super.act(delta);
            // Desplacem l'objecte en l'eix de les x
            position.x += velocity * delta;

            // Si està fora de la pantalla canviem la variable a true
            if (position.x + width < 0) {
                leftOfScreen = true;
            }
        }

    }

    public void reset(float newX) {
        position.x = newX;
        leftOfScreen = false;
    }

    public boolean isLeftOfScreen() {
        return leftOfScreen;
    }

    public float getTailX() {
        return position.x + width;
    }

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

    //TODO: Part 4, método para estblecer el estado pause que heredarán las clases Background, Microones, FireBall y Bonus.
    public void setPause() {
        pause = true;
    }

    //TODO: Part 4, método para estblecer el estado running que heredarán las clases Background, Microones, FireBall y Bonus.
    public void setRun() {
        pause = false;
    }

}
