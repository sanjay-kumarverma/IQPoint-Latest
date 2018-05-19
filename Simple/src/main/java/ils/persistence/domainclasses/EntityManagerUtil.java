package ils.persistence.domainclasses;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Tapas
 *
 */
public class EntityManagerUtil {
	
	private static EntityManagerFactory emf = null;
	
	//private static Logger LOG = Logger.getLogger(EntityManagerUtil.class);
	
	static{
		// Read the persistence.xml once
		//LOG.debug("Loading persistence.xml to EntityManagerFactory.");
		emf = Persistence.createEntityManagerFactory("ils");
	}
	
	public static EntityManagerFactory getEntityManagerFactory(){
		if(emf == null){
			//LOG.debug("EntityManagerFactory is null. Creating fresh EntityManagerFactory.");
			emf = Persistence.createEntityManagerFactory("ils");
		}
		return emf;
	}
	
	public static EntityManager getEntityManager(){
		return getEntityManagerFactory().createEntityManager();
	}
	
}