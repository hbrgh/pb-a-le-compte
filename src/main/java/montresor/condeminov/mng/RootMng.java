package montresor.condeminov.mng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RootMng {
	
	private static Logger LOGGER = LoggerFactory.getLogger(RootMng.class);
	
	private static RootMng uniqueInstance = null;
	
	private String rootDirApp;
	
	
	private RootMng() {
		
	}

	public static synchronized RootMng getInstance()
	{
          if(uniqueInstance==null)
          {
                  uniqueInstance = new RootMng();
          }
          return uniqueInstance;
	}
	
	public static synchronized void newInstance()
	{
     
          uniqueInstance = new RootMng();
        
         
	}

	public String getRootDirApp() {
		return rootDirApp;
	}

	public void setRootDirApp(String rootDirApp) {
		this.rootDirApp = rootDirApp;
		LOGGER.info("Répertoire père de l'application: {}", rootDirApp);
	}
	


	
}
