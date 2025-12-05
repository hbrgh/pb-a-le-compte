package montresor.condeminov.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RootDirApp {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RootDirApp.class);
	
	private static final String OS_INCONNU_MESSAGE = "Et ta soeur? J'ai l'air de m'exécuter sur un MainFrame, peut-être? Ou sur un boulier?";
			
	
	public static String calcRootDirApp(Properties props) {
	
		String path = props.getProperty("windows_default_app_root"); // VIN-DOSE, par défaut. The HELL
		
		OsCheckUtils.OSType ostype=OsCheckUtils.getOperatingSystemType();
		switch (ostype) {
		    case WINDOWS: 
		    	break;
		    case OSX: 
		    	path = props.getProperty("osx_default_app_root");; // Default paradise
		    	break;
		    case LINUX: 
		    	path = "/tmp/"; // Another paradise
		    	break;
		    case OTHER_OS:
		    	LOGGER.error(OS_INCONNU_MESSAGE);
		    	throw new RuntimeException(OS_INCONNU_MESSAGE);

		}
		return(path);
	}

}
