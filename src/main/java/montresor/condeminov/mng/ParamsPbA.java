package montresor.condeminov.mng;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import montresor.BaremeFiscal;



public class ParamsPbA {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ParamsPbA.class);
	
	private static ParamsPbA uniqueInstance = null;
	
	private BaremeFiscal baremeIrpp;
	private AutresParametresPbA autresParams;
	
	
	private ParamsPbA() {
		
	}

	public static synchronized ParamsPbA getInstance()
	{
          if(uniqueInstance==null)
          {
                  uniqueInstance = new ParamsPbA();
          }
          return uniqueInstance;
	}
	
	public static synchronized void newInstance()
	{
     
          uniqueInstance = new ParamsPbA();
        
         
	}
	
	
	
	public boolean lireFichierParametrage(String fullPath) {
		
		BaremeFiscal lBaremeFiscal = null;
		try {
			lBaremeFiscal = ParamsInputUtils.readBaremeIrppFromFileParam(fullPath);
			if (lBaremeFiscal == null) {
				return(false);
			}
		} catch (InvalidFormatException e) {
			LOGGER.error(e.toString());
			return(false);
		}
		
		LOGGER.info(lBaremeFiscal.toString());
		this.setBaremeIrpp(lBaremeFiscal);
		
		AutresParametresPbA lAutresParametresPbA = null;
		try {
			lAutresParametresPbA = ParamsInputUtils.readAutresParamsPbAFromFileParam(fullPath);
			if (lAutresParametresPbA == null) {
				return(false);
			}
		} catch (InvalidFormatException e) {
			LOGGER.error(e.toString());
			return(false);
		}
		LOGGER.info(lAutresParametresPbA.toString());
		this.setAutresParams(lAutresParametresPbA);
		return(true);
	}

	public BaremeFiscal getBaremeIrpp() {
		return baremeIrpp;
	}

	public void setBaremeIrpp(BaremeFiscal baremeIrpp) {
		this.baremeIrpp = baremeIrpp;
	}

	public AutresParametresPbA getAutresParams() {
		return autresParams;
	}

	public void setAutresParams(AutresParametresPbA autresParams) {
		this.autresParams = autresParams;
	}

	


	
}
