package montresor.condeminov.mng;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import montresor.BaremeFiscal;
import montresor.TrancheFiscale;


public class ParamsInputUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(ParamsInputUtils.class);
	
	private static final String NOM_ONGLET_BAREME_IRPP = "bareme_irpp";
	private static final String NOM_ONGLET_AUTRES_PARAMS = "autres";
	
	private static final int IND_COL_NOM_PARAM_DANS_ONGLET_AUTRES = 0;
	public static final String NOM_PARAM_TAUX_CS_NON_DEDUCTIBLES_DANS_ONGLET_AUTRES = "TauxDesCSnonDeductibles";
	private static final int IND_LIG_TAUX_COTISATION_SOCIALE_DANS_ONGLET_AUTRES = 0;
	private static final int IND_COL_VALEUR_PARAM_DANS_ONGLET_AUTRES = 1;
	
	private static final int IND_COL_NOM_TRANCHE_DANS_ONGLET_BAREME_IRPP = 0;
	private static final int IND_COL_TAUX_DANS_ONGLET_BAREME_IRPP = 1;
	private static final int IND_COL_BINF_DANS_ONGLET_BAREME_IRPP = 2;
	private static final int IND_COL_BSUP_DANS_ONGLET_BAREME_IRPP = 3;
	
	
	private static XSSFRow verifPresenceParamDansOngletAutres(XSSFSheet sheet, String nomParam, int ligParam) throws MissingOrIncorrectParamPbAException {
		XSSFRow row = sheet.getRow(ligParam);
		if (row == null) {
			String fatalError = "Paramètre absent: " + nomParam;
			LOGGER.error(fatalError);
			throw new MissingOrIncorrectParamPbAException(fatalError);
		}
		XSSFCell cellCode = row.getCell(IND_COL_NOM_PARAM_DANS_ONGLET_AUTRES);
		String nomParamCell = cellCode.getStringCellValue();
		if (nomParamCell.equalsIgnoreCase(nomParam) == false) {

			String fatalError = "Paramètre absent: " + nomParam;
			LOGGER.error(fatalError);
			throw new MissingOrIncorrectParamPbAException(fatalError);
		}

		return(row);
	}
	
	private static boolean verifValParamTaux(String nomParam, double taux) {
		if ((taux < 0.0) || (taux > 100.0)) {
			LOGGER.error("Paramètre dont la valeur est inacceptable: {}. Valeur=", nomParam,
					taux);
			return false;
		}
		return(true);
	}
	public static boolean lireOngletAutres(XSSFSheet sheet, AutresParametresPbA otrParams) {


		XSSFRow row;
		// Taux cotisation sociale
		try {
			row = verifPresenceParamDansOngletAutres
							(
								sheet,
								NOM_PARAM_TAUX_CS_NON_DEDUCTIBLES_DANS_ONGLET_AUTRES,
								IND_LIG_TAUX_COTISATION_SOCIALE_DANS_ONGLET_AUTRES
							);
		} catch (MissingOrIncorrectParamPbAException e) {
			return(false);
		}
		XSSFCell cellCode = row.getCell(IND_COL_VALEUR_PARAM_DANS_ONGLET_AUTRES);
		double tauxCs = cellCode.getNumericCellValue();
		if (verifValParamTaux(NOM_PARAM_TAUX_CS_NON_DEDUCTIBLES_DANS_ONGLET_AUTRES, tauxCs) == false) {
			return(false);
		}		
		otrParams.setTauxCotisationSociale(tauxCs);
		
		
	
	
		


		return (true);
	}
	
	private static void lireOngletBaremeIrpp(XSSFSheet sheet, BaremeFiscal bareme) {
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		
		
		// on saute la ligne de headers
		for(int rowNum=firstRowNum+1; rowNum<=lastRowNum; rowNum++) {
			

			XSSFRow row = sheet.getRow(rowNum);
			XSSFCell cellCode = row.getCell(IND_COL_NOM_TRANCHE_DANS_ONGLET_BAREME_IRPP);
			String nomTranche = cellCode.getStringCellValue();
			
			cellCode = row.getCell(IND_COL_TAUX_DANS_ONGLET_BAREME_IRPP);
			double taux = cellCode.getNumericCellValue();
			
			cellCode = row.getCell(IND_COL_BINF_DANS_ONGLET_BAREME_IRPP);
			int binf = (int)cellCode.getNumericCellValue();
			
			cellCode = row.getCell(IND_COL_BSUP_DANS_ONGLET_BAREME_IRPP);
			int bsup = (int)cellCode.getNumericCellValue();	
			TrancheFiscale lTrancheFiscale = 
					new TrancheFiscale
							(
								nomTranche,
								taux,
								binf,
								bsup
							);
			bareme.addTranche(lTrancheFiscale);
							
			
		
		}
	}
	


	public static BaremeFiscal readBaremeIrppFromFileParam( String filePathName ) throws InvalidFormatException  {
	
		

		BaremeFiscal lBaremeFiscal = new BaremeFiscal(NOM_ONGLET_BAREME_IRPP);
		LOGGER.info("Début lecture du fichier : {}", filePathName);
		
		try (FileInputStream stream = new FileInputStream(new File(filePathName))){
			
			XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(stream);
			XSSFSheet sheet = workbook.getSheet(NOM_ONGLET_BAREME_IRPP);
			lireOngletBaremeIrpp(sheet, lBaremeFiscal);
			
			workbook.close();
		} catch (IOException e) {
			LOGGER.error(e.toString());
			
		} 
		LOGGER.info("Fin lecture du fichier : {}", filePathName);
		if (lBaremeFiscal.verifCompletEtCoherent()) {
			return(lBaremeFiscal);
		} 
		return(null);
		
	}
	public static AutresParametresPbA readAutresParamsPbAFromFileParam(String filePathName)
			throws InvalidFormatException {

		AutresParametresPbA lAutresParametresPbA = new AutresParametresPbA();
		boolean autresParamsOK = false;
		LOGGER.info("Début lecture du fichier : {}", filePathName);

		try (FileInputStream stream = new FileInputStream(new File(filePathName))) {

			XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(stream);
			XSSFSheet sheet = workbook.getSheet(NOM_ONGLET_AUTRES_PARAMS);
			autresParamsOK = lireOngletAutres(sheet, lAutresParametresPbA);

			workbook.close();
		} catch (IOException e) {
			LOGGER.error(e.toString());

		}
		LOGGER.info("Fin lecture du fichier : {}", filePathName);
		if (autresParamsOK == true) {
			return (lAutresParametresPbA);
		}
		return (null);

	}

}
