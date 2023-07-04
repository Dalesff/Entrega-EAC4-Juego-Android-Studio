package eac4.alesf.helpers;
// David Ales Fernandez
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManager {

    // TODO: Part 1, modificamos o creamos las variables pertientes.
    // Sprite Sheet
    public static Texture sheet;

    // Animació Helicopter
    public static TextureRegion[] heli;
    public static Animation heliAnim;

    // Fons
    public static TextureRegion background;

    // Microones enemic
    public static TextureRegion micro;

    // Explosió
    public static TextureRegion[] explosion;
    public static Animation explosionAnim;

    // Sons
    public static Sound explosionSound;
    public static Music music;
    public static Sound shotSound;
    public static Sound scoreSound;

    // Font
    public static BitmapFont font;

    // TODO: Part 2, creamos una variable para aplicar un disparo.
    public static TextureRegion[] fireBall;
    public static Animation fireBallAnim;
    public static TextureRegion fireBT;

    // TODO: Part 3, creamos una variable donde incluiremos el Sprite Sheet de las monedas del bonus, y los texture region con las monedas pertinentes.
    public static Texture ex3_coins;

    public static TextureRegion silverCoin;
    public static TextureRegion goldCoin;

    // TODO: Part 4, creamos  una variable donde almacenaremos el sprite del botón para pausar el juego.
    public static TextureRegion ex4_Pausa;

    public static void load() {

        // TODO: Part 1, después de crear el archivo sprite sheet con los nuevos sprites, aplicamos el codigo.
        // Carreguem les textures i li apliquem el mètode d'escalat 'nearest'
        sheet = new Texture(Gdx.files.internal("sheet.png"));
        sheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // TODO: Part 1, aquí aplicamos las texturas con las coordenadas dentro del sprite sheet.
        // Carreguem els 8 estats de l'helicopter
        heli = new TextureRegion[8];
        heli[0] = new TextureRegion(sheet,440,105,96,32);
        heli[1] = new TextureRegion(sheet,374,139,96,32);
        heli[2] = new TextureRegion(sheet,440,71,96,32);
        heli[3] = new TextureRegion(sheet,472,139,96,32);
        heli[4] = new TextureRegion(sheet,276,139,96,32);
        heli[5] = new TextureRegion(sheet,342,71,96,32);
        heli[6] = new TextureRegion(sheet,342,105,96,32);
        heli[7] = new TextureRegion(sheet,342,37,96,32);

        // TODO: Part 1, aplicamos a cada texture region  el flip para que no salga volteado.
        for (int i = 0; i < heli.length; i++) {
            heli[i].flip(false, true);
        }

        // TODO: Part 1, creamos la animación.
        // Finalment creem l'animació
        heliAnim = new Animation(0.05f, heli);
        heliAnim.setPlayMode(Animation.PlayMode.LOOP);

        // TODO: Part 1, aplicamos el sprite del enemigo.
        // Sprite del microones
        micro = new TextureRegion(sheet, 342, 7, 34, 28);
        //micro.flip(false, true);

        // TODO: Part 1, aplicamos los nuevos sprites para la explosión.
        // Carreguem els 11 estats de l'explosió
        explosion = new TextureRegion[11];
        explosion[0] = new TextureRegion(sheet, 446, 3, 32, 32);
        explosion[1] = new TextureRegion(sheet, 508, 37, 32, 32);
        explosion[2] = new TextureRegion(sheet, 474, 37, 32, 32);
        explosion[3] = new TextureRegion(sheet, 378, 3, 32, 32);
        explosion[4] = new TextureRegion(sheet, 538, 71, 32, 32);
        explosion[5] = new TextureRegion(sheet, 480, 3, 32, 32);
        explosion[6] = new TextureRegion(sheet, 542, 37, 32, 32);
        explosion[7] = new TextureRegion(sheet, 538, 105, 32, 32);
        explosion[8] = new TextureRegion(sheet, 412, 3, 32, 32);
        explosion[9] = new TextureRegion(sheet, 440, 37, 32, 32);
        explosion[10] = new TextureRegion(sheet, 0, 0, 0, 0);

        // TODO: Part 1, creamos la animación para la explosión.
        // Creem l'animació de l'explosió
        explosionAnim = new Animation(0.04f, explosion);

        // TODO: Part 1, aplicamos el nuevo fondo de pantalla y utilizamos .flip para que no salga volteado.
        // Fons de pantalla
        background = new TextureRegion(sheet, 2, 11, 272, 160);
        background.flip(false, true);

        // TODO: Part 2, aplicamos los sprites de disparo.
        fireBall = new TextureRegion[2];
        fireBall[0] = new TextureRegion(sheet, 532,2, 16, 16);
        fireBall[1] = new TextureRegion(sheet, 514,2, 16, 16);

        // TODO: Part 2, aplicamos la textura del botón para disparar.
        fireBT = new TextureRegion(sheet, 532,2, 16, 16);

        // TODO: Part 2, creamos la animación para el disparo.
        fireBallAnim = new Animation(0.04f, fireBall);

        //TODO: Part 3, añadimos las texturas de moneda que utilizaremos para incrementar la puntuación.
        goldCoin = new TextureRegion(sheet, 276,73,64,64);
        silverCoin = new TextureRegion(sheet, 276,7,64,64);

        //TODO: Part 4, añadimos la textura del botón de pausa que utilizaremos para esta función.
        ex4_Pausa = new TextureRegion(sheet, 514, 20, 21, 15);

        /******************************* Sounds *************************************/
        // Explosió
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        // Música del joc
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Afterburner.ogg"));
        music.setVolume(0.2f);
        music.setLooping(true);

        // TODO: Part 2, sonido para el disparo.
        shotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser-shot.wav"));

        // TODO: Part 3, sonido para la moneda.
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("sounds/chimes.wav"));

        /******************************* Text *************************************/
        // Font space
        FileHandle fontFile = Gdx.files.internal("fonts/space.fnt");
        font = new BitmapFont(fontFile, true);
        font.getData().setScale(0.4f);

    }

    public static void dispose() {

        // Alliberem els recursos gràfics i de audio
        sheet.dispose();
        explosionSound.dispose();
        shotSound.dispose();
        scoreSound.dispose();
        music.dispose();

    }
}
