package montresor.condeminov.mng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calcul {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Calcul.class);
	
	public Calcul() {
		super();
		
	}

	public OutputDataPbA launch(InputDataPbA pInpuDataPbA) {
		
		OutputDataPbA lOutputDataPbA = new OutputDataPbA();
		
		double recettesExtrapolees = 
				pInpuDataPbA.getBnc() + 
				((pInpuDataPbA.getTauxPressionSociale() / 100.0) * pInpuDataPbA.getBnc()) +
				pInpuDataPbA.getDepensesHorsCso();
		lOutputDataPbA.setRecetteExtrapolees(recettesExtrapolees);
		
		double pressionFiscaleRevenuHorsBnc = 
				ParamsPbA.getInstance().getBaremeIrpp().getImpotDu((int)Math.round(pInpuDataPbA.getRevenuHorsBnc()),pInpuDataPbA.getNbParts(),0);

		double pressionFiscaleTotale =
				ParamsPbA.getInstance().getBaremeIrpp().getImpotDu((int)Math.round(pInpuDataPbA.getRevenuHorsBnc()+pInpuDataPbA.getBnc()),pInpuDataPbA.getNbParts(),0);
		
		double pressionFiscale = pressionFiscaleTotale - pressionFiscaleRevenuHorsBnc;
		lOutputDataPbA.setPressionFiscale(pressionFiscale);
		
		double tauxPressionFiscale = pressionFiscale / pInpuDataPbA.getBnc() * 100.0;
		lOutputDataPbA.setTauxPressionFiscale(tauxPressionFiscale);
		
	
		double resultatHorsCSO = recettesExtrapolees - pInpuDataPbA.getDepensesHorsCso();// c'est le (3)
		lOutputDataPbA.setResultatHorsCso(resultatHorsCSO);
		double pressionSociale = (pInpuDataPbA.getTauxPressionSociale() / 100.0) * pInpuDataPbA.getBnc(); // c'est le (4)
		
		double csoNonDeductible = pInpuDataPbA.getBnc() * (pInpuDataPbA.getRsm() / pInpuDataPbA.getRs()) *
				(ParamsPbA.getInstance().getAutresParams().getTauxCotisationSociale() / 100.0);
		LOGGER.info("csNonDeductibles (valeur4b) = {}", csoNonDeductible);
		lOutputDataPbA.setCsoNonDeductible(csoNonDeductible);
		
		double revenuReel = pInpuDataPbA.getBnc() - pressionFiscale;
		lOutputDataPbA.setRevenuReel(revenuReel);
		
		double perteRentabilitePO = ((pressionSociale + pressionFiscale) / resultatHorsCSO) * 100.0;
		lOutputDataPbA.setPerteRentabilitePO(perteRentabilitePO);	
		
		lOutputDataPbA.setPressionSociale(pressionSociale);
		
		
		return(lOutputDataPbA);
	}

}
