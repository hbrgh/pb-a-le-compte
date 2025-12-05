package montresor.condeminov.ihm;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import montresor.condeminov.mng.RootMng;
import montresor.condeminov.utils.RootDirApp;

@SuppressWarnings("restriction")
public class MainIhmPbA extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainIhmPbA.class);

	public static final String VERSION = "v1.0.6";
	public static final String NOM_LOGICIEL = "PROBLEME A Hippocrate Fisc pour l'Aristocratie";
	public static final double LARGEUR_FENETRE_APPLI_PAR_DEFAUT = 1300.0;
	public static final double HAUTEUR_FENETRE_APPLI_PAR_DEFAUT = 900.0;
	
	private static final String TITRE_FENETRE_APPLI = NOM_LOGICIEL + " " + VERSION;

	
	@Override
	public void start(Stage stage) {

		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

		RootMng.newInstance();

		Properties props = new Properties();
		try {
			props.load(MainIhmPbA.class.getResource("params.properties").openStream()); //$NON-NLS-1$
		} catch (IOException e) {
			LOGGER.error("Unable to read params.properties resource file {}", e); //$NON-NLS-1$

			throw new RuntimeException(e);
		}

		RootMng.getInstance().setRootDirApp(RootDirApp.calcRootDirApp(props));

		

		Group rootGrp = new Group();
		Scene scene = new Scene(rootGrp, LARGEUR_FENETRE_APPLI_PAR_DEFAUT, HAUTEUR_FENETRE_APPLI_PAR_DEFAUT, Color.LIGHTGOLDENRODYELLOW);
		IhmElems lIhmElems = new IhmElems(stage, scene);
		lIhmElems.initialize();
		rootGrp.getChildren().add(lIhmElems.getMnBorderPane());
		


		stage.setOnCloseRequest((event) -> {
			Platform.exit();
		});
		stage.setTitle(TITRE_FENETRE_APPLI);
		stage.setScene(scene);

		stage.setResizable(false);
		stage.show();
//		Platform.runLater(() -> {
			// Ce qui suit sert à ce que lIhmElems.getMnTxtFieldNbParts() ne chope pas de
			// sélection et de focus au démarrage de l'application
			// cf.
			// https://stackoverflow.com/questions/29051225/remove-default-focus-from-textfield-javafx
			lIhmElems.getMnTxtFieldNbParts().focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue && firstTime.get()) {
					lIhmElems.getMnBorderPane().requestFocus(); // Delegate the focus to container
					firstTime.setValue(false); // Variable value changed for future references
				}
			});
//		});

	}

	public static void main(String[] args) {

		Application.launch(args);
	}

}