package eac4.alesf;
// David Ales Fernandez
import com.badlogic.gdx.Game;

import eac4.alesf.helpers.AssetManager;
import eac4.alesf.screens.SplashScreen;

public class EAC4 extends Game {

	@Override
	public void create() {

		// A l'iniciar el joc carreguem els recursos
		AssetManager.load();
		// I definim la pantalla d'splash com a pantalla
		setScreen(new SplashScreen(this));

	}

	// Cridem per descartar els recursos carregats.
	@Override
	public void dispose() {
		super.dispose();
		AssetManager.dispose();
	}
}
