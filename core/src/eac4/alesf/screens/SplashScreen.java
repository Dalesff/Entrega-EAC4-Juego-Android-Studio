package eac4.alesf.screens;
// David Ales Fernandez
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import eac4.alesf.EAC4;
import eac4.alesf.helpers.AssetManager;
import eac4.alesf.utils.Settings;

public class SplashScreen implements Screen {

    private Stage stage;
    private EAC4 game;

    private Label.LabelStyle textStyle;
    private Label textLbl;

    // TODO: Part 5, creamos una variable para puntuación máxima y una de la clase Preferences.
    static Preferences prefs;
    public static int highScore;

    // TODO Part 5, Label donde cargaremos lo puntuación máxima.
    private Label highScoreLbl;

    public SplashScreen(EAC4 game) {

        this.game = game;

        // Creem la càmera de les dimensions del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        // Posant el paràmetre a true configurem la càmera per a
        // que faci servir el sistema de coordenades Y-Down
        camera.setToOrtho(true);

        // Creem el viewport amb les mateixes dimensions que la càmera
        StretchViewport viewport = new StretchViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        // Creem l'stage i assginem el viewport
        stage = new Stage(viewport);

        // Afegim el fons
        stage.addActor(new Image(AssetManager.background));

        // Creem l'estil de l'etiqueta i l'etiqueta
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textLbl = new Label("EAC4 David Ales", textStyle);

        // Creem el contenidor necessari per aplicar-li les accions
        Container container = new Container(textLbl);
        container.setTransform(true);
        container.center();
        container.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);

        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
        container.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 1))));
        stage.addActor(container);

        /*
        // Creem la imatge de la nau i li assignem el moviment en horitzontal
        Image spacecraft = new Image(AssetManager.spacecraft);
        float y = Settings.GAME_HEIGHT / 2 + textLbl.getHeight();
        spacecraft.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(0 - spacecraft.getWidth(), y), Actions.moveTo(Settings.GAME_WIDTH, y, 5))));

        stage.addActor(spacecraft);
        */

        // TODO: Part 5,
        prefs = Gdx.app.getPreferences("David Ales Fernandez EAC4 Highscore");
        highScore = prefs.getInteger("highScore",0);

        highScoreLbl = new Label("HighScore: " + String.format("%06d", highScore), textStyle);
        Container hsContainer = new Container(highScoreLbl);
        hsContainer.bottom();
        hsContainer.setPosition(Settings.GAME_WIDTH -150, Settings.GAME_HEIGHT - 20);
        stage.addActor(hsContainer);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        stage.draw();
        stage.act(delta);

        // Si es fa clic en la pantalla, canviem la pantalla
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
            dispose();
        }

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

    // TODO: Part 5, método establece la puntuación máxima.
    public static void setHighScore(int actualScore) {
        int highScore = getHighScore();
        if (highScore <= actualScore) {
            prefs.putInteger("highScore",actualScore);
            prefs.flush();
        }
    }

    // TODO: Part 5, método que devuelve la puntuación máxima.
    public static int getHighScore() {
        return highScore;
    }
}