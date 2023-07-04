package eac4.alesf.utils;
// David Ales Fernandez

public class Settings {
    // TODO: Part 1, hacemos un refactor para que el codigo concuerde contextualmente con los nuevos sprites que utilizamos.
    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 240;
    public static final int GAME_HEIGHT = 135;

    // Propietats de la nau
    public static final float HELICOPTER_VELOCITY = 75;
    public static final int HELICOPTER_WIDTH = 36;
    public static final int HELICOPTER_HEIGHT = 15;
    public static final float HELICOPTER_STARTX = 20;
    public static final float HELICOPTER_STARTY = GAME_HEIGHT/2 - HELICOPTER_HEIGHT /2;

    // Rang de valors per canviar la mida de l'asteroide.
    public static final float MAX_MICROONES = 1f;
    public static final float MIN_MICROONES = 1f;

    // Configuració Scrollable
    public static final int MICROONES_SPEED = -150;
    public static final int MICROONES_GAP = 75;
    public static final int BG_SPEED = -100;

    // TODO: Part 3, Propietats per les monedas.
    public static final int GOLD_SCORE_SPEED = -130;
    public static final int GOLD_COIN_VALUE = 50;
    public static final int SILVER_SCORE_SPEED = -225;
    public static final int SILVER_COIN_VALUE = 25;
    public static final int COINS_GAP = 75;

    // TODO: Part 5, tipos de mensajes de game over.
    public static final String NOOB = "Eres un novato, animo.";
    public static final String FINE = "No ha estado nada mal.";
    public static final String PRO = "Eres muy bueno!";

}
