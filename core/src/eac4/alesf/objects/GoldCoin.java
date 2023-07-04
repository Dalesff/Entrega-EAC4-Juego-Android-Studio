package eac4.alesf.objects;
// David Ales Fernandez
import com.badlogic.gdx.graphics.g2d.Batch;
import eac4.alesf.helpers.AssetManager;
import eac4.alesf.utils.Settings;

//TODO: Part 3, creamos una subclase de bonus que tendrá un valor y aspecto diferente a la subclase SilverCoin.
public class GoldCoin extends Bonus {

    private static int value;

    public GoldCoin(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        value = Settings.GOLD_COIN_VALUE;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.goldCoin, position.x,position.y, width, height);
    }

    //TODO: Part 3, Getter que devuelve el valor de la moneda.
    public static int getValue() {
        return value;
    }
}
