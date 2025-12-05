package montresor.condeminov.ihm;

import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import montresor.condeminov.mng.Calcul;
import montresor.condeminov.mng.InputDataPbA;
import montresor.condeminov.mng.InputDataPbAException;
import montresor.condeminov.mng.OutputDataPbA;
import montresor.condeminov.mng.ParamsPbA;
import montresor.condeminov.mng.RootMng;


@SuppressWarnings("restriction")
public class IhmElems {

	private static final Logger LOGGER = LoggerFactory.getLogger(IhmElems.class);
	
	private static final String ERROR_TITLE = "Erreur!";
	
	public enum FilterModeExcel {

		XLSX_FILES("xlsx files (*.xlsx)", "*.xlsx");
		


		private ExtensionFilter extensionFilter;

		FilterModeExcel(String extensionDisplayName, String... extensions){
			extensionFilter = new ExtensionFilter(extensionDisplayName, extensions);
		}

		public ExtensionFilter getExtensionFilter(){
			return extensionFilter;
		}
	}

	private Scene laScene = null;
	private Stage leStage = null;
	
	private Font mainFont = null;
	
	private TextField mnTxtFieldNbParts = null;
	private TextField mnTxtFieldBnc = null;
	private TextField mnTxtFieldOtrRevFiscal = null;
	
	private TextField mnTxtFieldDepsHorsCso = null;
	
	private TextField mnTxtFieldTauxPressionSociale = null;

	private TextField mnTxtFieldRsm = null;
	private TextField mnTxtFieldRs = null;
	


	
	private TextArea mnTraceTxtArea;
	


	private Button mnBtnLancerCalcul;
	private BorderPane mnBorderPane;
	private MenuBar mnMenuBar;
	private GridPane mnGridPane;
	private BorderPane mnCalculBorderPane;

	


	public IhmElems(Stage stagggg, Scene sceeeeene) {
		super();
		this.leStage = stagggg;
		this.laScene = sceeeeene;
	
		
	}
	
	
	
	public TextField getMnTxtFieldNbParts() {
		return mnTxtFieldNbParts;
	}



	public void setMnTxtFieldNbParts(TextField mnTxtFieldNbParts) {
		this.mnTxtFieldNbParts = mnTxtFieldNbParts;
	}



	private void ecrDansTraceChoixFichierParametrage(File file, String info) {
		if (file != null ) {


			this.mnTraceTxtArea.appendText(info);
			this.mnTraceTxtArea.appendText(file.getAbsolutePath());
			this.mnTraceTxtArea.appendText(System.lineSeparator());
		}


	}
	
	


	private void buildMenubar() {


		// Create menus
		Menu actionsMenu = new Menu("Actions");
		


		MenuItem effaceTraceItem = new MenuItem("Effacer la trace");
		effaceTraceItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

				try {
					mnTraceTxtArea.clear();

				} catch (Exception e) {

				}

			}
		});
		MenuItem exitItem = new MenuItem("Quitter");
		exitItem.setOnAction((actionEvent) -> this.leStage.close());
		
		Menu paramMenu = new Menu("Paramètres");
		MenuItem paramItem = new MenuItem("Lire le fichier de paramétrage");
		

		paramItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				File file = null;
				try {
					String paramRep = RootMng.getInstance().getRootDirApp()+"/param";
					File paramDir = new File(paramRep);
					boolean isGoodParamDir = false;
					if (paramDir.exists() == true) {
						if (paramDir.isDirectory() == true) {
							isGoodParamDir = true;
						}
					}
					if (isGoodParamDir == false ) {
						Alert infoErrDirParamDlg = new Alert(AlertType.ERROR);
						infoErrDirParamDlg.setTitle(ERROR_TITLE);
						infoErrDirParamDlg.setHeaderText("Répertoire de paramétrage inexistant:"+ paramRep);
						infoErrDirParamDlg.setContentText("Ayez la bonté de le créer et d'y mettre au moins un fichier de paramétrage.");
						infoErrDirParamDlg.showAndWait();
						return;
					}
					FileChooser fileChooserParamFile = new FileChooser();
					fileChooserParamFile.getExtensionFilters().setAll(
							Stream.of(FilterModeExcel.XLSX_FILES)
							.map(FilterModeExcel::getExtensionFilter)
							.collect(Collectors.toList()));
					fileChooserParamFile.setInitialDirectory(paramDir);
					file = fileChooserParamFile.showOpenDialog(leStage);
					if (file != null) {
						mnTraceTxtArea.clear();
						ecrDansTraceChoixFichierParametrage(file, "FICHIER DE PARAMETRAGE: ");
					
						ParamsPbA.newInstance();
						boolean ficParamCorrect = ParamsPbA.getInstance().lireFichierParametrage(file.getAbsolutePath());
						if (ficParamCorrect == false) {
							Alert infoErrFicParamDlg = new Alert(AlertType.ERROR);
							infoErrFicParamDlg.setTitle(ERROR_TITLE);
							infoErrFicParamDlg.setHeaderText("Fichier de paramétrage non conforme: "+ file.getAbsolutePath());
							infoErrFicParamDlg.setContentText("Voir la cause dans le fichier log de l'application");
							infoErrFicParamDlg.showAndWait();
							return;
						}
					
						mnTraceTxtArea.appendText(ParamsPbA.getInstance().getBaremeIrpp().toString());
						mnTraceTxtArea.appendText(ParamsPbA.getInstance().getAutresParams().toString());
						mnBtnLancerCalcul.setDisable(false);
				
					}
						
				} catch (Exception e) {
					
						LOGGER.error(e.toString());
						
						Alert infoErrFicParamDlg = new Alert(AlertType.ERROR);
						infoErrFicParamDlg.setTitle(ERROR_TITLE);
						infoErrFicParamDlg.setHeaderText("Fichier de paramétrage non conforme: "+ file.getAbsolutePath());
						infoErrFicParamDlg.setContentText(e.toString());
						infoErrFicParamDlg.showAndWait();
					
				}
				

			}
		});

	
		Menu helpMenu = new Menu("?");
		MenuItem aboutItem = new MenuItem("A propos du logiciel");

		aboutItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(MainIhmPbA.NOM_LOGICIEL);
				alert.setHeaderText("Version: "+ MainIhmPbA.VERSION);
				alert.setContentText("Date: 09/03/2023");

				alert.showAndWait();

			}
		});










		// Add menuItems to the Menus

		actionsMenu.getItems().addAll( effaceTraceItem, exitItem);
		paramMenu.getItems().addAll(paramItem);

		helpMenu.getItems().addAll(aboutItem);
		
		
		

		// Create MenuBar
		this.mnMenuBar = new MenuBar();
		
		// Add Menus to the MenuBar
		this.mnMenuBar.getMenus().addAll(actionsMenu, paramMenu, helpMenu);
		this.mnMenuBar.prefWidthProperty().bind(this.leStage.widthProperty());
		
	}
	
	private HBox buildZoneSaisieNbPartsAutreRevenuBnc() {

		Label lLabelTitreNbParts = new Label("Nb parts: ");
		lLabelTitreNbParts.setFont(mainFont);

		mnTxtFieldNbParts = new TextField("1");
		mnTxtFieldNbParts.setMinWidth(20);
		mnTxtFieldNbParts.setFont(mainFont);
	
		
		HBox hbNbParts = new HBox();
		hbNbParts.setPadding(new Insets(10));
		hbNbParts.setSpacing(2);
		hbNbParts.setAlignment(Pos.CENTER_LEFT);
		hbNbParts.getChildren().addAll(lLabelTitreNbParts, mnTxtFieldNbParts);
		
		Label lLabelTitreOtrRevFiscal = new Label("Revenus imposables hors BNC: ");
		lLabelTitreOtrRevFiscal.setFont(mainFont);
		mnTxtFieldOtrRevFiscal = new TextField("80000");
		mnTxtFieldOtrRevFiscal.setMinWidth(120);
		mnTxtFieldOtrRevFiscal.setFont(mainFont);

		HBox hbOtrRevFiscal = new HBox();
		hbOtrRevFiscal.setPadding(new Insets(10));
		hbOtrRevFiscal.setSpacing(2);
		hbOtrRevFiscal.setAlignment(Pos.CENTER_LEFT);
		hbOtrRevFiscal.getChildren().addAll(lLabelTitreOtrRevFiscal, mnTxtFieldOtrRevFiscal);

		Label lLabelTitreBnc = new Label("BNC: ");
		lLabelTitreBnc.setFont(mainFont);
		mnTxtFieldBnc = new TextField("160000");
		mnTxtFieldBnc.setMinWidth(120);
		mnTxtFieldBnc.setFont(mainFont);

		HBox hbBnc = new HBox();
		hbBnc.setPadding(new Insets(10));
		hbBnc.setSpacing(2);
		hbBnc.setAlignment(Pos.CENTER_LEFT);
		hbBnc.getChildren().addAll(lLabelTitreBnc, mnTxtFieldBnc);
		
		
		
		

		HBox hbpn = new HBox();
		hbpn.setPadding(new Insets(0));
		hbpn.setSpacing(30);

		hbpn.getChildren().addAll(hbNbParts, hbOtrRevFiscal, hbBnc);
		
		

		return (hbpn);

	}
	
	private HBox buildZoneSaisieDepsHorsCsoTauxPressionSociale() {
		
		Label lLabelTitreDepensesHorsCso = new Label("Dépenses hors CSO: ");
		lLabelTitreDepensesHorsCso.setFont(mainFont);

		mnTxtFieldDepsHorsCso = new TextField("62000");
		mnTxtFieldDepsHorsCso.setMinWidth(120);
		mnTxtFieldDepsHorsCso.setFont(mainFont);
		
		HBox hbDespHorsCso = new HBox();
		hbDespHorsCso.setPadding(new Insets(10));
		hbDespHorsCso.setSpacing(2);
		hbDespHorsCso.setAlignment(Pos.CENTER_LEFT);
		hbDespHorsCso.getChildren().addAll(lLabelTitreDepensesHorsCso, mnTxtFieldDepsHorsCso);

		Label lLabelTitreTauxPressionSociale = new Label("Taux pression sociale: ");
		lLabelTitreTauxPressionSociale.setFont(mainFont);

		mnTxtFieldTauxPressionSociale = new TextField("27");
		mnTxtFieldTauxPressionSociale.setMinWidth(20);
		mnTxtFieldTauxPressionSociale.setFont(mainFont);

		HBox hbTauxPressionSociale = new HBox();
		hbTauxPressionSociale.setPadding(new Insets(10));
		hbTauxPressionSociale.setSpacing(2);
		hbTauxPressionSociale.setAlignment(Pos.CENTER_LEFT);
		hbTauxPressionSociale.getChildren().addAll(lLabelTitreTauxPressionSociale, mnTxtFieldTauxPressionSociale);


		
		

		HBox hbpn = new HBox();
		hbpn.setPadding(new Insets(0));
		hbpn.setSpacing(30);

		hbpn.getChildren().addAll(hbDespHorsCso, hbTauxPressionSociale);

		return (hbpn);

	}
	
	private HBox buildZoneSaisieRsmRs() {



		Label lLabelTitreRsm = new Label("RSM: ");
		lLabelTitreRsm.setFont(mainFont);
		mnTxtFieldRsm = new TextField("176000");
		mnTxtFieldRsm.setMinWidth(120);
		mnTxtFieldRsm.setFont(mainFont);

		HBox hbRsm = new HBox();
		hbRsm.setPadding(new Insets(10));
		hbRsm.setSpacing(2);
		hbRsm.setAlignment(Pos.CENTER_LEFT);
		hbRsm.getChildren().addAll(lLabelTitreRsm, mnTxtFieldRsm);
		
		Label lLabelTitreRs = new Label("RS: ");
		lLabelTitreRs.setFont(mainFont);

		mnTxtFieldRs = new TextField("160000");
		mnTxtFieldRs.setMinWidth(120);
		mnTxtFieldRs.setFont(mainFont);
		
		HBox hbRs = new HBox();
		hbRs.setPadding(new Insets(10));
		hbRs.setSpacing(2);
		hbRs.setAlignment(Pos.CENTER_LEFT);
		hbRs.getChildren().addAll(lLabelTitreRs, mnTxtFieldRs);
		
		

		HBox hbpn = new HBox();
		hbpn.setPadding(new Insets(0));
		hbpn.setSpacing(30);

		hbpn.getChildren().addAll(hbRsm, hbRs);

		return (hbpn);

	}
	
	

	private void buildGridpane() {

		GridPane root = new GridPane();

		

		root.setPadding(new Insets(20));
		root.setHgap(25);
		root.setVgap(15);
		
		

		
		HBox hbZoneSaisieNbPartsAutreRevenuBnc =  buildZoneSaisieNbPartsAutreRevenuBnc();
		
		root.add(hbZoneSaisieNbPartsAutreRevenuBnc, 0, 1, 1, 1);
		
		HBox hbZoneSaisieDepsHorsCsoTauxPressionSociale =  buildZoneSaisieDepsHorsCsoTauxPressionSociale();
		root.add(hbZoneSaisieDepsHorsCsoTauxPressionSociale, 0, 2, 1, 1);
		
		HBox hbZoneSaisieRsmRs =  buildZoneSaisieRsmRs();
		root.add(hbZoneSaisieRsmRs, 0, 3, 1, 1);
		
				

		BorderPane lBorderPaneTrace = new  BorderPane();
		lBorderPaneTrace.setPadding(new Insets(10, 10, 10, 10));
		

		Label labelTrace = new Label("TRACE ET RESULTATS");
		labelTrace.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, 18));


		lBorderPaneTrace.setTop(labelTrace);
		BorderPane.setMargin(labelTrace, new Insets(10, 10, 20, 10));
		BorderPane.setAlignment(labelTrace, Pos.CENTER);


		// TextArea
		TextArea textArea = new TextArea();
		textArea.setText("");

		String style=
				"-fx-text-fill: blue;"+
						"-fx-background-color: black;"+
						"-fx-font-size: 18;" +
						"-fx-font-weight: " + "bold;";  
		textArea.setStyle(style);   
		textArea.setPrefHeight((double)550);
		textArea.setPrefWidth((double)1200);
		textArea.setEditable(false);
		this.mnTraceTxtArea = textArea;

		lBorderPaneTrace.setCenter(textArea);

		root.add(lBorderPaneTrace,0, 4,1,1);
		
		root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	   
		this.mnGridPane = root;

	}

	private void buildBorderpane() {

		this.mnBorderPane = new BorderPane();
		this.mnBorderPane.setTop(this.mnMenuBar);
		this.mnBorderPane.setCenter(this.mnGridPane);
		this.mnBorderPane.setBottom(this.mnCalculBorderPane);
		this.mnBorderPane.prefHeightProperty().bind(this.laScene.heightProperty());
		this.mnBorderPane.prefWidthProperty().bind(this.laScene.widthProperty());
	
	}
	
	private InputDataPbA recupererDonneesEntree() throws NumberFormatException, InputDataPbAException {
		

		String str = this.mnTxtFieldNbParts.getText().trim();
		int nbParts = Integer.parseInt(str);
		
		str = this.mnTxtFieldOtrRevFiscal.getText().trim().replaceAll(",", ".");
		double revenuHorsBnc = Double.parseDouble(str);
		str = this.mnTxtFieldBnc.getText().trim().replaceAll(",", ".");
		double bnc = Double.parseDouble(str);
		
		str = this.mnTxtFieldDepsHorsCso.getText().trim().replaceAll(",", ".");
		double depHorsCso = Double.parseDouble(str);
		str = this.mnTxtFieldTauxPressionSociale.getText().trim().replaceAll(",", ".");
		double tauxPs = Double.parseDouble(str);
		
		str = this.mnTxtFieldRsm.getText().trim().replaceAll(",", ".");
		double rsm = Double.parseDouble(str);
		
		str = this.mnTxtFieldRs.getText().trim().replaceAll(",", ".");
		double rs = Double.parseDouble(str);
		

		InputDataPbA lInputDataPbA = 
				new InputDataPbA
						(
							nbParts,
							revenuHorsBnc,
							bnc, 
							depHorsCso,
							tauxPs,
							rsm,						
							rs
						);
		lInputDataPbA.verification();
		return (lInputDataPbA);

	}
	private OutputDataPbA calcul(InputDataPbA pInputDataPbA) {
		Calcul lCalcul = new Calcul();
		return (lCalcul.launch(pInputDataPbA));
		
	}
	
	private String renvoyerMessErreurDetailleDonneesEntree(String zeUglyError) {
		
		StringBuilder sb = new StringBuilder("Détail: ");
		sb.append(zeUglyError);
		sb.append(System.lineSeparator());
		sb.append("\nAyez la bonté de corriger lesdites données...");
		return(sb.toString());

	}
	private void buildCalculBorderPane() {
		BorderPane bpn = new BorderPane();

		bpn.setPadding(new Insets(10, 10, 10, 10));

		mnBtnLancerCalcul= new Button("Lancer le calcul");
		mnBtnLancerCalcul.setFont(mainFont);
		mnBtnLancerCalcul.setDisable(true);
		
		
		mnBtnLancerCalcul.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mnTraceTxtArea.clear();
				InputDataPbA lInputDataPbA = null;
				try {
					lInputDataPbA = recupererDonneesEntree();
					String strInputDataPbA = lInputDataPbA.toStringSpecial();
					LOGGER.info(strInputDataPbA);
					mnTraceTxtArea.appendText(strInputDataPbA);

				} catch (NumberFormatException e) {
					String zeError = e.toString();
					LOGGER.error(e.toString());
					Alert infoErrInputDataDlg = new Alert(AlertType.ERROR);
					infoErrInputDataDlg.setTitle(ERROR_TITLE);
					infoErrInputDataDlg.setHeaderText("Erreur de format dans les données d'entrée!");
					infoErrInputDataDlg.setContentText(renvoyerMessErreurDetailleDonneesEntree(zeError));
					infoErrInputDataDlg.showAndWait();
					return;
				} catch (InputDataPbAException e1) {
					String zeError = e1.toString();
					LOGGER.error(e1.toString());
					Alert infoErrInputDataDlg = new Alert(AlertType.ERROR);
					infoErrInputDataDlg.setTitle(ERROR_TITLE);
					infoErrInputDataDlg.setHeaderText("Erreur sur au moins une valeur dans les données d'entrée!");
					infoErrInputDataDlg.setContentText(renvoyerMessErreurDetailleDonneesEntree(zeError));
					infoErrInputDataDlg.showAndWait();
					return;					
				}
				
				mnTraceTxtArea.appendText(System.lineSeparator());
				mnTraceTxtArea.appendText(System.lineSeparator());
				OutputDataPbA lOutputDataPbA = calcul(lInputDataPbA);
				String resultatsCalcul = lOutputDataPbA.toStringSpecial();
				mnTraceTxtArea.appendText(resultatsCalcul);
				LOGGER.info("{}{}", System.lineSeparator(), resultatsCalcul);
			}
		});
		
		
		FlowPane fpnber = new FlowPane();
		fpnber.setPadding(new Insets(5, 5, 5, 5));
		fpnber.setHgap(5); //inutile car un seul élément , mais bon...
		fpnber.getChildren().add(mnBtnLancerCalcul);

		bpn.setLeft(fpnber);
		// Set margin for left area.
		BorderPane.setMargin(fpnber, new Insets(10, 10, 10, 10));
		BorderPane.setAlignment(fpnber, Pos.BOTTOM_LEFT);



		this.mnCalculBorderPane = bpn;

	}







	public void initialize() {
		
		this.mainFont = Font.font("Arial", FontWeight.BOLD, 14);
		buildMenubar();
		buildGridpane();
		buildCalculBorderPane();
		buildBorderpane();


	}



	public BorderPane getMnBorderPane() {
		return mnBorderPane;
	}
	public void setMnBorderPane(BorderPane mnBorderPane) {
		this.mnBorderPane = mnBorderPane;
	}
	public MenuBar getMnMenuBar() {
		return mnMenuBar;
	}
	public void setMnMenuBar(MenuBar mnMenuBar) {
		this.mnMenuBar = mnMenuBar;
	}
	public GridPane getMnGridPane() {
		return mnGridPane;
	}
	public void setMnGridPane(GridPane mnGridPane) {
		this.mnGridPane = mnGridPane;
	}


	public Scene getLaScene() {
		return laScene;
	}


	public void setLaScene(Scene laScene) {
		this.laScene = laScene;
	}



}
