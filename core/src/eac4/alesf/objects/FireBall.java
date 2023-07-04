package eac4.alesf.objects;
// David Ales Fernandez
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import eac4.alesf.helpers.AssetManager;

// TODO: Part 2, creamos una clase que le otorgará propiedades al disparo.
public class FireBall extends Scrollable {

    private float runTime;
    private Rectangle collisionRect;

    public FireBall(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        collisionRect = new Rectangle();

    }

    @Override
    public void act(float delta) {
        if (!pause) {
            super.act(delta);
            collisionRect.set(position.x, position.y, width, 2);
            runTime += delta;
        }
    }

    // TODO: Part 2, aplicamos al metodo draw para que pueda dibujar la animación.
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) AssetManager.fireBallAnim.getKeyFrame(runTime, true), position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());

    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }

}
