package montresor.condeminov.mng;

public class OutputDataPbA extends DataPbA {
	
	private double recetteExtrapolees;
	
	private double pressionFiscale;
	
	private double tauxPressionFiscale;
	
	private double revenuReel;
	
	private double perteRentabilitePO;
	
	private double rentabiliteRelle;
	
	private double pressionSociale;
	
	private double resultatHorsCso;
	
	private double csoNonDeductible;

	public OutputDataPbA() {
		super();
	}

	
	public String toStringSpecial() {
		StringBuilder sb = new StringBuilder("RESULTATS DU CALCUL:");
		sb.append(System.lineSeparator());
		appendResultDoubleValue(sb, "Recettes extrapolées", this.recetteExtrapolees, " €");
		appendResultDoubleValue(sb, "Résultat hors CSO", this.resultatHorsCso, " €");
		appendResultDoubleValueSansFinDeLigne(sb, "Surcroît d'impôt lié au seul BNC", this.pressionFiscale, " €");
		appendResultDoubleValue(sb, "   d'où pression fiscale", this.tauxPressionFiscale, " %");
		appendResultDoubleValueSansFinDeLigne(sb, "CSO", this.pressionSociale , " €");
		appendResultDoubleValue(sb, "    dont CS non déductibles", this.csoNonDeductible , " €");
		appendResultDoubleValue(sb, "Revenu réel lié au seul BNC", this.revenuReel, " €");
		appendResultDoubleValue(sb, "Perte de rentabilité liée aux PO", this.perteRentabilitePO, " %");
		appendResultDoubleValue(sb, "Rentabilité réelle en BNC/IR", (100.0 - this.perteRentabilitePO), " %");
		return (sb.toString());
	}

	public double getRecetteExtrapolees() {
		return recetteExtrapolees;
	}

	public void setRecetteExtrapolees(double recetteExtrapolees) {
		this.recetteExtrapolees = recetteExtrapolees;
	}

	public double getPressionFiscale() {
		return pressionFiscale;
	}

	public void setPressionFiscale(double pressionFiscale) {
		this.pressionFiscale = pressionFiscale;
	}

	public double getRevenuReel() {
		return revenuReel;
	}

	public void setRevenuReel(double revenuReel) {
		this.revenuReel = revenuReel;
	}

	public double getPerteRentabilitePO() {
		return perteRentabilitePO;
	}

	public void setPerteRentabilitePO(double perteRentabilitePO) {
		this.perteRentabilitePO = perteRentabilitePO;
	}

	public double getRentabiliteRelle() {
		return rentabiliteRelle;
	}

	public void setRentabiliteRelle(double rentabiliteRelle) {
		this.rentabiliteRelle = rentabiliteRelle;
	}

	public double getPressionSociale() {
		return pressionSociale;
	}

	public void setPressionSociale(double pressionSociale) {
		this.pressionSociale = pressionSociale;
	}

	public double getResultatHorsCso() {
		return resultatHorsCso;
	}

	public void setResultatHorsCso(double resultatHorsCso) {
		this.resultatHorsCso = resultatHorsCso;
	}


	public double getCsoNonDeductible() {
		return csoNonDeductible;
	}


	public void setCsoNonDeductible(double csoNonDeductible) {
		this.csoNonDeductible = csoNonDeductible;
	}


	public double getTauxPressionFiscale() {
		return tauxPressionFiscale;
	}


	public void setTauxPressionFiscale(double tauxPressionFiscale) {
		this.tauxPressionFiscale = tauxPressionFiscale;
	}
	
	

}
