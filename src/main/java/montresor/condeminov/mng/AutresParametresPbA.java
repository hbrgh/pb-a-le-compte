package montresor.condeminov.mng;

public class AutresParametresPbA {
	
	private double tauxCotisationSociale; // pourcentage
	
	public AutresParametresPbA() {
		super();
		
	}
	
	
	private void appendParam(StringBuilder sb, String nomParam, double valParam) {
		sb.append(System.lineSeparator());
		sb.append(nomParam);
		sb.append("=");
		sb.append( String.format("%.2f", valParam) );
		sb.append("%");		
		
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();

		appendParam(sb, ParamsInputUtils.NOM_PARAM_TAUX_CS_NON_DEDUCTIBLES_DANS_ONGLET_AUTRES,
				this.getTauxCotisationSociale());
		
		return sb.toString();	
	}



	
	public double getTauxCotisationSociale() {
		return tauxCotisationSociale;
	}

	public void setTauxCotisationSociale(double tauxCotisationSociale) {
		this.tauxCotisationSociale = tauxCotisationSociale;
	}



	
	
	

}
