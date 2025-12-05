package montresor.condeminov.mng;

public class DataPbA {
	
	protected static final String ESPACE_ENTRE_DATA = "    ";
	
	protected void appendResultDoubleValue(StringBuilder pSb, String name, 
			double val, String suffix) {
		pSb.append(name);
		pSb.append(" = ");
		pSb.append(String.format( "%.2f", val ));
		if (suffix.length() > 0) {
			pSb.append(suffix);
		}
		pSb.append(System.lineSeparator());
	}
	
	protected void appendResultDoubleValueSansFinDeLigne(StringBuilder pSb, String name, 
			double val, String suffix) {
		pSb.append(name);
		pSb.append(" = ");
		pSb.append(String.format( "%.2f", val ));
		if (suffix.length() > 0) {
			pSb.append(suffix);
		}
		
	}
	
	protected void appendResultIntegerValue(StringBuilder pSb, String name, 
			int val, String suffix) {
		pSb.append(name);
		pSb.append(" = ");
		pSb.append(String.valueOf(val));
		if (suffix.length() > 0) {
			pSb.append(suffix);
		}
		pSb.append(System.lineSeparator());
	}


}
