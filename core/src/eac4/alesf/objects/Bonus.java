package eac4.alesf.objects;
// David Ales Fernandez
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;
import eac4.alesf.utils.Methods;
import eac4.alesf.utils.Settings;

//TODO: Part 3, creamos una clase abstracta desde la cual crearemos las subclases de bonus que incrementará la puntuación.
public abstract class Bonus extends Scrollable{

    public Bonus(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        collisionCircle = new Circle();

        r = new Random();
        assetCoin = r.nextInt(15);

        setOrigin();
    }

    private Circle collisionCircle;

    Random r;

    int assetCoin;

    public void setOrigin() {

        this.setOrigin(width/2 + 1, height/2);

    }

    @Override
    public void act(float delta) {

        if (!pause) {
            super.act(delta);

            // Actualitzem el cercle de col·lisions (punt central de l'asteroid i el radi.
            collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
        }
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

        assetCoin = r.nextInt(15);
        setOrigin();
    }

    //TODO: Part 3, retorna si hi ha col·lisió de la moneda amb l'helicopter.
    public boolean collides(Helicopter nau) {

        if (position.x <= nau.getX() + nau.getWidth()) {
            return (Intersector.overlaps(collisionCircle, nau.getCollisionRect()));
        }
        return false;
    }

}
