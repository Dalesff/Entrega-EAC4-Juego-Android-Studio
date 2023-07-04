package eac4.alesf.objects;
// David Ales Fernandez
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import java.util.Random;
import eac4.alesf.helpers.AssetManager;
import eac4.alesf.utils.Methods;
import eac4.alesf.utils.Settings;

// TODO: Part 1, hacemos un refactor para que el codigo concuerde contextualmente con los nuevos sprites que utilizamos.
public class Microones extends Scrollable {

    private Circle collisionCircle;

    Random r;

    int assetMicroones;

    public Microones(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Creem el cercle
        collisionCircle = new Circle();

        /* Accions */
        r = new Random();
        assetMicroones = r.nextInt(15);

        setOrigin();

        // Rotacio
        RotateByAction rotateAction = new RotateByAction();
        rotateAction.setAmount(-0.5f);
        rotateAction.setDuration(0.2f);


        // Accio de repetició
        RepeatAction repeat = new RepeatAction();
        repeat.setAction(rotateAction);
        repeat.setCount(RepeatAction.FOREVER);

        // Equivalent:
        // this.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(-90f, 0.2f)));

        this.addAction(repeat);

    }

    public void setOrigin() {

        this.setOrigin(width/2 + 1, height/2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualitzem el cercle de col·lisions (punt central de l'asteroid i el radi.
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
        
    }

    public void reset() {

        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_MICROONES, Settings.MAX_MICROONES);
        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 34 * newSize;
        float newX = Settings.GAME_WIDTH + width;

        super.reset(newX);
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

        assetMicroones = r.nextInt(15);
        setOrigin();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.micro, position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    // Retorna true si hi ha col·lisió
    public boolean collides(Helicopter nau) {

        if (position.x <= nau.getX() + nau.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan l'asteroid estigui a la mateixa alçada que la spacecraft
            return (Intersector.overlaps(collisionCircle, nau.getCollisionRect()));
        }
        return false;
    }

    //TODO: Part 2, devuelve si hay una colisión entre un objeto Microones con un objeto FireBall.
    public boolean collides(FireBall fireball) {

        if (position.x <= fireball.getX() + fireball.getWidth()) {
            return (Intersector.overlaps(collisionCircle, fireball.getCollisionRect()));
        }
        return false;
    }
}
