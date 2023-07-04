package eac4.alesf.screens;
// David Ales Fernandez
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import eac4.alesf.helpers.AssetManager;
import eac4.alesf.helpers.InputHandler;
import eac4.alesf.objects.GoldCoin;
import eac4.alesf.objects.SilverCoin;
import eac4.alesf.objects.FireBall;
import eac4.alesf.objects.Microones;
import eac4.alesf.objects.ScrollHandler;
import eac4.alesf.objects.Helicopter;
import eac4.alesf.utils.Settings;

public class GameScreen implements Screen {

    // Els estats del joc
    public enum GameState {

        READY, RUNNING, GAMEOVER, PAUSA

    }

    private GameState currentState;

    // Objectes necessaris
    private Stage stage;
    private Helicopter helicopter;
    private ScrollHandler scrollHandler;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;

    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;

    ArrayList<FireBall> fireballs;

    // TODO: Part 3, creamos una variable y un label para la puntuación.
    private static int score;
    private Label.LabelStyle textStyle;
    private static Label scoreLabel;

    public GameScreen(Batch prevBatch, Viewport prevViewport) {

        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        // Creem la nau i la resta d'objectes
        helicopter = new Helicopter(Settings.HELICOPTER_STARTX, Settings.HELICOPTER_STARTY, Settings.HELICOPTER_WIDTH, Settings.HELICOPTER_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(helicopter);
        // Donem nom a l'Actor
        helicopter.setName("helicopter");

        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "Are you\nready?");

        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

        // TODO: Part 2, Creamos el botón para disparar bolas de fuego.
        fireballs = new ArrayList<FireBall>();
        Image fireBT = new Image(AssetManager.fireBT);
        fireBT.setName("FireballBT");
        fireBT.setPosition((Settings.GAME_WIDTH) - fireBT.getWidth() - 5, (Settings.GAME_HEIGHT) - fireBT.getHeight() - 5);
        stage.addActor(fireBT);

        // TODO: Part 3, definimos el label donde mostrara la puntuación.
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        scoreLabel = new Label(String.format("%06d", score), textStyle);
        Container container = new Container(scoreLabel);
        container.setName("Score");
        container.setPosition(Settings.GAME_WIDTH - 200, Settings.GAME_HEIGHT - 130);
        stage.addActor(container);

        // TODO: Part 4, definimos y añadimos el actor pausa.
        Image pausa = new Image(AssetManager.ex4_Pausa);
        pausa.setPosition(Settings.GAME_WIDTH - 30, Settings.GAME_HEIGHT -130);
        pausa.setName("Pause");
        stage.addActor(pausa);

    }

    private void drawElements() {

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Pintem el fons de negre per evitar el "flickering"
        //Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem la nau
        shapeRenderer.rect(helicopter.getX(), helicopter.getY(), helicopter.getWidth(), helicopter.getHeight());

        // Recollim tots els Microones
        ArrayList<Microones> microoneses = scrollHandler.getMicrooneses();
        Microones microones;

        for (int i = 0; i < microoneses.size(); i++) {

            microones = microoneses.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(microones.getX() + microones.getWidth() / 2, microones.getY() + microones.getWidth() / 2, microones.getWidth() / 2);
        }
        shapeRenderer.end();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Dibuixem tots els actors de l'stage
        stage.draw();

        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {
            case GAMEOVER:
                updateGameOver(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case READY:
                updateReady();
                break;
            case PAUSA:
                textLayout.setText(AssetManager.font, "Pause");
                updatePause(delta);
                break;
        }
        //drawElements();
    }

    private void updateReady() {
        // Dibuixem el text al centre de la pantalla
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        //stage.addActor(textLbl);
        batch.end();
    }

    private void updateRunning(float delta) {
        stage.act(delta);
        // TODO: Part 3, distinguimos entre la colisioón de un enemigo y una moneda.
        if (scrollHandler.collides(helicopter)) {
            if (scrollHandler.scoreCoin) {
                // TODO: Part 3, cuando la colisión es con una moneda incrementamos la puntuación.
                AssetManager.scoreSound.play();
                if (getScrollHandler().silverCoin) {
                    updateScore(SilverCoin.getValue());
                }
                if (getScrollHandler().goldCoin) {
                    updateScore(GoldCoin.getValue());
                }
                scrollHandler.scoreCoin = false;
            } else {
                // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
                AssetManager.explosionSound.play();
                stage.getRoot().findActor("helicopter").remove();
                // TODO: Part 5, Layout mensaje game over.
                textLayout.setText(AssetManager.font, getGameover(score));
                SplashScreen.setHighScore(score);
                currentState = GameState.GAMEOVER;
                scrollHandler.scoreCoin = false;

            }
        }
        // TODO: Part 2, Cuando una bola de fuego colisiona con un enemigo microondas, borramos ambos.
        if(fireballs.size()>0) {
            for (FireBall fireball : fireballs) {
                Microones microones = scrollHandler.collides(fireball);
                if (microones != null) {
                    fireballs.remove(fireball);
                    fireball.remove();
                    scrollHandler.removeMicrooneses(microones);
                    break;
                }
            }
        }
    }

    private void updateGameOver(float delta) {
        stage.act(delta);
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 2);
        // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
        batch.draw((TextureRegion) AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (helicopter.getX() + helicopter.getWidth() / 2) - 32, helicopter.getY() + helicopter.getHeight() / 2 - 32, 64, 64);
        batch.end();

        explosionTime += delta;

    }

    // TODO: Part 4, método que actualiza el estado de pausa.
    private void updatePause(float delta) {
        stage.act(delta);
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        batch.end();
    }

    public void reset() {

        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "Are you\nready?");
        // Cridem als restart dels elements.
        helicopter.reset();
        scrollHandler.reset();

        // Posem l'estat a 'Ready'
        currentState = GameState.READY;

        // Afegim la nau a l'stage
        stage.addActor(helicopter);

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;

        for(FireBall l : fireballs) l.remove();
        fireballs.clear();

        score = 0;

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Helicopter getHelicopter() {
        return helicopter;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        switch (currentState) {
            case PAUSA:
                setPause();
                break;
            case READY:
                break;
            case RUNNING:
                setRunning();
                break;
        }
        this.currentState = currentState;
    }

    // TODO: Part 2, método que crea la bola de fuego.
    public void shootFireball() {
        FireBall fireball = new FireBall(helicopter.getX() + helicopter.getWidth(), helicopter.getY() + helicopter.getHeight() / 2, 9, 2, Settings.HELICOPTER_VELOCITY * 2);
        stage.getRoot().addActor(fireball);
        fireballs.add(fireball);
        AssetManager.shotSound.play();
    }

    // TODO: Part 3, método actualizar la puntuación.
    public static void updateScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    // TODO: Part 4, método para efectuar el estado "PAUSE".
    public void setPause() {
        //TODO: Part 4, bajar volúmen.
        AssetManager.music.setVolume(0.05f);
        // TODO: Part 4, detener los elementos en pantalla.
        ScrollHandler.setPause();
        helicopter.setPause();
        for(FireBall fb: fireballs) fb.setPause();
        // TODO: Part 4, ocultamos los actores
        stage.getRoot().findActor("Pause").setVisible(false);
        stage.getRoot().findActor("Score").setVisible(false);
    }

    // TODO: Part 4, método para volver al estado "RUNNING".
    public void setRunning() {
        // TODO Part 4, subir volúmen.
        AssetManager.music.setVolume(0.2f);
        // TODO: Part 4, reanudar los elementos en pantalla.
        scrollHandler.setRun();
        helicopter.setRun();
        for(FireBall fb: fireballs) fb.setRun();
        // TODO: Part 4, mostrar los actores.
        stage.getRoot().findActor("Pause").setVisible(true);
        stage.getRoot().findActor("Score").setVisible(true);

    }

    // TODO: Part 5, método que devuelve un mensaje según tu puntuación.
    public String getGameover(int gameScored) {
        if (gameScored < 100) {
            return Settings.NOOB;
        } else if (gameScored > 150) {
            return Settings.PRO;
        } else {
            return Settings.FINE;
        }
    }

}
