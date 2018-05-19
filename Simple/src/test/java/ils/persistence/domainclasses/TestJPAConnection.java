package ils.persistence.domainclasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ils.persistence.domainclasses.embeddables.Address;
import ils.persistence.domainclasses.embeddables.RecordStatus;

import javax.persistence.EntityManager;

import org.junit.Test;

import java.util.Calendar;
import java.sql.Timestamp;

import junit.framework.JUnit4TestAdapter;

public class TestJPAConnection {

	  private EntityManager entityManager = EntityManagerUtil.getEntityManager();


	  

	  
	  @Test
	  public void testJpaConnection() {
/*	  public static void main(String args[]) {*/
		  TestJPAConnection example = new TestJPAConnection();
		  
	    //System.out.println("After Sucessfully insertion ");
	    User user1 = example.saveUser("Sanjay","verma");
	    
	    //System.out.println("Firstname --"+user1.getFirstName()+"--lastname --"+user1.getLastName());

	  }
	  
	  public static junit.framework.Test suite(){
	       return new JUnit4TestAdapter(TestJPAConnection.class);
	    }

	  public User saveUser(String firstname, String lastname) {
	    User user=new User();
	    try {
	      entityManager.getTransaction().begin();
	      user.setFirstName(firstname);
	      user.setLastName(lastname);
	      user.setEmail("sanjay@gmail.com");
	      user.setPassword("sanjay");
	      user.setRole(getRole());
	      
	      //persisting user profile
	      UserProfile userpro=getProfile();
	      entityManager.persist(userpro);
	      
	      user.setUserProfile(userpro);
	      user.setAddress(getAddress());
	      user.setRecordStatus(getRecordStatus());

	      entityManager.persist(user);
	      entityManager.getTransaction().commit();
	      entityManager.close();
	    } catch (Exception e) {
	    	 System.out.println(e.getMessage());
	    	 e.printStackTrace();
	         entityManager.getTransaction().rollback();
	    }
	    return user;
	  }
	  
	  public Role getRole()
	  {
		  Role role = new Role();
		  role.setId(new Long(1));
		  role.setRole("Student");
		  return role;
	  }
	  
	  public UserProfile getProfile()
	  {
		  UserProfile up = new UserProfile();
		  
		  up.setAdmissionNumber("5338");
		  up.setBloodGroup("B+");
		  up.setFatherName("Sanjay Verma");
		  up.setFatherPhone("9999999999");
		  up.setFatherEmail("skvvks@gmail.com");
		  		
		  return up;
	  }
	  
	  public Address getAddress()
	  {
		  Address add=new Address();
		  add.setStreet("Ashoka-1, sector-34");
		  add.setCity("Faridabad");
		  add.setState("Haryana");
		  add.setCountry("India");
		  add.setZip("121003");
		  
		  return add;
	  }
	  
	  public RecordStatus getRecordStatus() 
	  {
		  RecordStatus rs=new RecordStatus();
		  
/*		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		  LocalDateTime now = LocalDateTime.now();*/
		  
		  /*String dtstr=Date.;
		  SimpleDateFormat sdf=new SimpleDateFormat("dd-mm-yyyy");*/
		  
		  long timeinmillis = System.currentTimeMillis();
		  //Date dt=new Date(timeinmillis);
		  java.sql.Timestamp dt = new java.sql.Timestamp(timeinmillis);
		//try {

			rs.setUpdatedOn(dt);
		//} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		  

	
		  rs.setUpdatedBy(new Long(10000));
		  rs.setDeleted(false);
		  
		  return rs;
	  }
}