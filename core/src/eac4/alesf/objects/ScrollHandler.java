package eac4.alesf.objects;
// David Ales Fernandez
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.Scrollable;

import eac4.alesf.helpers.AssetManager;
import eac4.alesf.utils.Methods;
import eac4.alesf.utils.Settings;

public class ScrollHandler extends Group {

    // Fons de pantalla
    static Background bg;
    static Background bg_back;

    // Asteroides
    int numAsteroids;
    private static ArrayList<Microones> microoneses;

    // Objecte Random
    Random r;

    private float respawn;

    // TODO: Part 3, variables que trabajarán con la puntuación.
    private static ArrayList<Bonus> coins;
    public boolean scoreCoin;
    public boolean silverCoin;
    public boolean goldCoin;

    public boolean pause;

    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 3 microones
        numAsteroids = 3;

        // Creem l'ArrayList
        this.microoneses = new ArrayList<Microones>();

        respawn = 0;

        this.coins  = new ArrayList<Bonus>();

        scoreCoin = false;
        silverCoin = false;
        goldCoin = false;

        pause = false;

        newMicroones();

        newCoin();
    }

    @Override
    public void act(float delta) {

        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());
        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());
        }

        for (int i = 0; i < microoneses.size(); i++) {
            Microones microones = this.microoneses.get(i);
            if (microones.isLeftOfScreen()) {
                microones.reset();
            }
        }

        // TODO: Part 3,
        for (int i = 0; i < coins.size(); i++) {
            Bonus coin = this.coins.get(i);
            if (coin.isLeftOfScreen()) {
                coin.reset();
            }
        }

        // TODO: Part 2, Los enemigos vuelven a aparecer cada 0.5 segundos, siempre que en pantalla tengamos menos de 5 enemigos.
        if (pause) respawn = 0;
        if(respawn > 1f && microoneses.size() < 5){
            newMicroones();
            respawn = 0;
        } else {
            respawn += delta;
        }
        // TODO: Part 3, Las monedas vuelven a aparecer.
        if(respawn > 1f && coins.size() < 5){
            newCoin();
            respawn = 0;
        } else {
            respawn += delta;
        }

    }

    public boolean collides(Helicopter nau) {

        // Comprovem les col·lisions entre cada asteroid i la nau
        for (Microones microones : this.microoneses) {
            if (microones.collides(nau)) {
                return true;
            }
        }
        // TODO: Part 3, comprovamos la colisión de los bonus.
        for (Bonus coin : this.coins) {
            if (coin.collides(nau)) {
                coinType(coin);
                removeCoin(coin);
                scoreCoin = true;
                return true;
            }
        }
        return false;
    }

    // TODO: Part 2, comprovem les col·lisions entre cada bola de foc i cada microones.
    public Microones collides(FireBall fireball) {

        for (Microones microones : this.microoneses) {
            if (microones.collides(fireball)) {
                return microones;
            }
        }
        return null;
    }

    public void reset() {

        // Posem el primer asteroid fora de la pantalla per la dreta
        microoneses.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta d'microones.
        for (int i = 1; i < microoneses.size(); i++) {

            microoneses.get(i).reset(microoneses.get(i - 1).getTailX() + Settings.MICROONES_GAP);

        }

        coins.get(0).reset(Settings.GAME_WIDTH);

        for (int i = 1; i < coins.size(); i++) {
            coins.get(i).reset(coins.get(i - 1).getTailX() + Settings.COINS_GAP);
        }
    }

    public ArrayList<Microones> getMicrooneses() {
        return microoneses;
    }

    // TODO: Part 2, método que borra los enemigos.
    public void removeMicrooneses(Microones microones){
        AssetManager.explosionSound.play();
        microoneses.remove(microones);
        microones.remove();
    }

    // TODO: Part 2, creamos un método para crear los enemigos.
    public void newMicroones() {
        // Creem la mida al·leatòria
        float newSize = Methods.randomFloat(Settings.MIN_MICROONES, Settings.MAX_MICROONES) * 34;

        Microones microones = new Microones(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.MICROONES_SPEED);
        microoneses.add(microones);
        addActor(microones);

    }

    // TODO: Part 3, método que crea monedas.
    public void newCoin() {

        float respawnCoin = Methods.randomFloat(1,10);

        if (respawnCoin > 6) {
            SilverCoin silverCoin = new SilverCoin(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, 0, 0, Settings.SILVER_SCORE_SPEED);
            coins.add(silverCoin);
            addActor(silverCoin);
        } else if (respawnCoin < 6){
            GoldCoin goldCoin = new GoldCoin(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, 0, 0, Settings.GOLD_SCORE_SPEED);
            coins.add(goldCoin);
            addActor(goldCoin);
        }

    }

    // TODO: Part 3, método que borra las monedas.
    public void removeCoin(Bonus coin){
        coins.remove(coin);
        coin.remove();
    }

    //TODO: Part 3, método que evalua de que tipo de bonus se trata.
    public void coinType (Bonus coin) {

        if (coin instanceof GoldCoin) {
            goldCoin = false;
            silverCoin = true;
        }
        if (coin instanceof SilverCoin) {
            silverCoin = false;
            goldCoin = true;
        }
    }

    //TODO : Part 4, método para hacer set true en la variable pausa en Background, Microones y Bonus.
    public static void setPause(){
        bg.setPause();
        bg_back.setPause();
        for (Microones mic : microoneses){
            mic.setPause();
        }
        for (Bonus bon : coins){
            bon.setPause();
        }

    }
    //TODO : Part 4, método para hacer set false en la variable pausa en Background, Microones y Bonus.
    public static void setRun(){
        bg.setRun();
        bg_back.setRun();
        for (Microones mic : microoneses){
            mic.setRun();
        }
        for (Bonus bon : coins){
            bon.setRun();
        }
    }
}