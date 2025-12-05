package montresor.condeminov.mng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputDataPbA extends DataPbA {
	
	private static Logger LOGGER = LoggerFactory.getLogger(InputDataPbA.class);
	
	private int nbParts;
	private double revenuHorsBnc;
	private double bnc;
	private double depensesHorsCso;
	private double tauxPressionSociale;
	private double rsm;
	private double rs;
	
	

	

	
	
	public InputDataPbA(int nbParts, double revenuHorsBnc, double bnc, double depensesHorsCso,
			double tauxPressionSociale, double rsm, double rs) {
		super();
		this.nbParts = nbParts;
		this.revenuHorsBnc = revenuHorsBnc;
		this.bnc = bnc;
		this.depensesHorsCso = depensesHorsCso;
		this.tauxPressionSociale = tauxPressionSociale;
		this.rsm = rsm;
		this.rs = rs;
	}
	
	public void verifDouble(String name, double val, double binf, double bsup) throws InputDataPbAException {
		if ((val < binf) || (val > bsup)) {
			StringBuilder sbZeError = new StringBuilder("Erreur sur ");
			sbZeError.append(name);
			sbZeError.append(": ");
			sbZeError.append(String.format("%.2f", val));
			sbZeError.append(". Valeur attendue: entre ");
			sbZeError.append(String.format("%.2f", binf));
			sbZeError.append(" et ");
			sbZeError.append(String.format("%.2f", bsup));
			sbZeError.append(".");
			String zeError = sbZeError.toString();
			LOGGER.error(zeError);
			throw new InputDataPbAException(zeError);

		}		
	}
	public void verification() throws InputDataPbAException {
		if ((this.nbParts < 1) || (this.nbParts > 10)) {
			String zeErrorNbParts = "Erreur sur nbParts: "+ this.nbParts+". Valeur attendue: entre 1 et 9";
			LOGGER.error(zeErrorNbParts);
			throw new InputDataPbAException(zeErrorNbParts);
		}
		verifDouble("Revenus imposables hors BNC", this.revenuHorsBnc, 0.0, 1000000000.0);
		verifDouble("BNC", this.bnc, 0.0, 1000000000.0);
		verifDouble("Dépenses hors CSO", this.depensesHorsCso, 0.0, 1000000000.0);
		verifDouble("Taux Pression Sociale", this.tauxPressionSociale, 0.0, 100.0);
		verifDouble("RSM", this.rsm, 0.0, 1000000000.0);
		verifDouble("RS", this.rs, 0.0, 1000000000.0);
	}



	public String toStringSpecial() {
		StringBuilder sb = new StringBuilder("DONNEES D'ENTREE:");
		sb.append(System.lineSeparator());
		appendResultIntegerValue(sb, "Nombre de parts", this.nbParts, "");
		appendResultDoubleValue(sb, "Dépenses hors CSO", this.depensesHorsCso, " €");
		appendResultDoubleValue(sb, "Revenus imposables hors BNC", this.revenuHorsBnc, " €");
		appendResultDoubleValue(sb, "BNC", this.bnc , " €");
		appendResultDoubleValueSansFinDeLigne(sb, "Taux pression sociale", this.tauxPressionSociale, " %");
		sb.append(ESPACE_ENTRE_DATA);
		appendResultDoubleValueSansFinDeLigne(sb, "RSM", this.rsm, " €");
		sb.append(ESPACE_ENTRE_DATA);
		appendResultDoubleValue(sb, "RS", this.rs, " €");
		return (sb.toString());
	}





	@Override
	public String toString() {
		return "InputDataPbA [nbParts=" + nbParts + ", revenuHorsBnc=" + revenuHorsBnc + ", bnc=" + bnc
				+ ", depensesHorsCso=" + depensesHorsCso + ", tauxPressionSociale=" + tauxPressionSociale + ", rsm="
				+ rsm + ", rs=" + rs + "]";
	}

	public int getNbParts() {
		return nbParts;
	}


	public void setNbParts(int nbParts) {
		this.nbParts = nbParts;
	}


	public double getDepensesHorsCso() {
		return depensesHorsCso;
	}


	public void setDepensesHorsCso(double depensesHorsCso) {
		this.depensesHorsCso = depensesHorsCso;
	}


	public double getBnc() {
		return bnc;
	}


	public void setBnc(double bnc) {
		this.bnc = bnc;
	}


	public double getTauxPressionSociale() {
		return tauxPressionSociale;
	}


	public void setTauxPressionSociale(double tauxPressionSociale) {
		this.tauxPressionSociale = tauxPressionSociale;
	}


	public double getRevenuHorsBnc() {
		return revenuHorsBnc;
	}


	public void setRevenuHorsBnc(double revenuHorsBnc) {
		this.revenuHorsBnc = revenuHorsBnc;
	}

	public double getRsm() {
		return rsm;
	}

	public void setRsm(double rsm) {
		this.rsm = rsm;
	}

	public double getRs() {
		return rs;
	}

	public void setRs(double rs) {
		this.rs = rs;
	}


	
	
	

}
