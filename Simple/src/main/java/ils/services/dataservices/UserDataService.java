package ils.services.dataservices;

import ils.json.AddressPlain;
import ils.json.LevelPlain;
import ils.json.SubjectPlain;
import ils.json.UserPlain;
import ils.json.UserPlainV2;
import ils.json.UserProfilePlain;
import ils.persistence.domainclasses.Role;
import ils.persistence.domainclasses.User;
import ils.persistence.domainclasses.embeddables.Address;
import ils.persistence.domainclasses.embeddables.RecordStatus;
import ils.persistence.repositories.UserDataRepository;
import ils.testspring.context.SpringApplicationContext;
import ils.utils.GsonExclusionStrat;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Path("/qb")
//@RolesAllowed({"admin","manager"})
public class UserDataService {
	
	final static Logger logger = Logger.getLogger(UserDataService.class);
	//@RolesAllowed("admin")
	@GET
    @Path("/showusers")
    @Produces(MediaType.TEXT_HTML)	
	public Response getUsers(@Context SecurityContextHolder sec)
	{
/*		if (sec.isSecure() && sec.isUserInRole("admin")) {
			System.out.println(sec.getContext().getAuthentication()..getUserPrincipal()+ " accessed customer database. Role ---->sec.isSecure()--"+sec.isSecure()+"----sec.isUserInRole(admin)"+sec.isUserInRole("admin"));
			}
		*/
		UserDetails userDetails;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        userDetails = (UserDetails) auth.getPrincipal();
		        
		        System.out.println("Username :"+userDetails.getUsername());
		        System.out.println("Password :"+userDetails.getPassword());
		        System.out.println("Authorities :"+userDetails.getAuthorities().toString());
		        System.out.println("Account expired (yes/No) :"+userDetails.isAccountNonExpired());
		        System.out.println("Account locked (yes/no) :"+userDetails.isAccountNonLocked());
		        
		        
		}
		String jsonstr="";
	    try {	
				UserDataRepository srit= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
				
				List<User> userlist=srit.listAllUsers();
				
				  GsonExclusionStrat ges = new GsonExclusionStrat();
				  Gson gson = new GsonBuilder().setExclusionStrategies(ges).setPrettyPrinting().serializeNulls().create();
				
				jsonstr= gson.toJson(userlist);
	    } catch (Exception ex) {
	    	
	      	  ex.printStackTrace();
	      	  logger.error("UserDataService-Error getting list of users, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 		    	
	    }
		
		return Response.status(200).entity(jsonstr).build();
		
	}

	//@RolesAllowed("manager")
	@GET
    @Path("/showuser/{id}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getUserById(@PathParam("id") String id )
	{
		String jsonstr="";
		try {
		
				UserDataRepository srit= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
				
				User user=srit.findUserById(new Long(id));
				
				GsonExclusionStrat ges = new GsonExclusionStrat();
			
				//Gson gson = new GsonBuilder().setExclusionStrategies(ges).setPrettyPrinting().serializeNulls().create();
				Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			    //Creating plain object
			    UserPlain up =new UserPlain();
				up.setId(user.getId().toString());  
			    up.setFirstName(user.getFirstName());
			    up.setLastName(user.getLastName());
			    up.setEmail(user.getEmail());
			    up.setPhone((user.getPhone()==null) ? "Not available" : user.getPhone());
			    
			    LevelPlain lp = new LevelPlain();
			    lp.setId(user.getUserLevel().getId().toString());
			    lp.setLevel(user.getUserLevel().getLevel());
			    lp.setLevelName(user.getUserLevel().getLevelName());
			    
			    up.setUserLevel(lp);
			    
			    up.setUpdateDate(getDateString(user.getRecordStatus().getUpdatedOn()));
			    up.setUpdateTime(getTimeString(user.getRecordStatus().getUpdatedOn()));
			    
			    //getting the name and role of user who updated this user
			    User updatedByUser=srit.findUserById(user.getRecordStatus().getUpdatedBy());
			    String updatedByStr=updatedByUser.getFirstName()+" "+updatedByUser.getLastName();
			    
			    up.setUpdateBy(updatedByStr);
			    UserProfilePlain upp = new UserProfilePlain();
			    upp.setPhotoUrl((user.getUserProfile().getPhotoUrl()==null) ? "files/DefaultUser.jpg" : "files/"+user.getUserProfile().getPhotoUrl());
			    upp.setBloodGroup((user.getUserProfile().getBloodGroup()==null) ? "Not available" : user.getUserProfile().getBloodGroup());
			    upp.setFatherName((user.getUserProfile().getFatherName()==null) ? "Not available" : user.getUserProfile().getFatherName());
			    upp.setFatherPhone(user.getUserProfile().getFatherPhone()==null ? "Not available" : user.getUserProfile().getFatherPhone());
			    upp.setFatherEmail(user.getUserProfile().getFatherEmail()==null ? "Not available" : user.getUserProfile().getFatherEmail());
			    upp.setMotherEmail(user.getUserProfile().getMotherEmail()==null ? "Not available" :user.getUserProfile().getMotherEmail());
			    upp.setMotherName(user.getUserProfile().getMotherName()==null ? "Not available" : user.getUserProfile().getMotherName());
			    upp.setMotherPhone(user.getUserProfile().getMotherPhone()==null ? "Not available" : user.getUserProfile().getMotherPhone());
			    upp.setAdmissionNumber(user.getUserProfile().getAdmissionNumber()==null ? "Not available" :user.getUserProfile().getAdmissionNumber());	       		
			    upp.setStudiesInSection(user.getUserProfile().getStudiesInSection() == null ? "Not available" : user.getUserProfile().getStudiesInSection() );
			    upp.setStudiesIn(user.getUserProfile().getStudiesIn() ==null ? "Not available" : user.getUserProfile().getStudiesIn());
			    upp.setLevelName(user.getUserLevel().getLevel() == null ? "Not available" : user.getUserLevel().getLevel());
			    up.setUserProfile(upp);
			    
			    AddressPlain ap = new AddressPlain();
			    
			    ap.setStreet(user.getAddress().getStreet()==null ? "Not available" : user.getAddress().getStreet());
			    ap.setCity(user.getAddress().getCity()== null ? "Not available" : user.getAddress().getCity() );
			    ap.setState(user.getAddress().getState() == null ? "Not available" : user.getAddress().getState());
			    ap.setZip(user.getAddress().getZip() == null ? "Not available" : user.getAddress().getZip());
			    up.setAddress(ap);
			    
			    up.setComments(user.getComments());				
				
				jsonstr= gson.toJson(up);
		}
		catch(Exception ex) {
			
	      	  ex.printStackTrace();
	      	  logger.error("UserDataService-Error getting user by user id, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 				
		}
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	@GET
    @Path("/getusersubjects/{userid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getUserSubjects(@PathParam("userid") String userid )
	{
		UserDataRepository srit= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
		List<SubjectPlain> subjects=null;
		try {
				User user=srit.findUserById(new Long(userid));
				
		        //get level of user
				Long levelid = user.getUserLevel().getId();
				
				//find subjects of this level
				subjects=srit.getUserSubjects(levelid);
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("UserDataService-Error getting user subjects, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}
	
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(subjects);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	//@RolesAllowed("manager")
	@GET
    @Path("/getuserbyloginname/{loginid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getUserByLoginName(@PathParam("loginid") String loginName )
	{
		String jsonstr="";
		try {
			UserDataRepository srit= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
			
			User user=srit.findUserByEmailId(loginName);
			
			GsonExclusionStrat ges = new GsonExclusionStrat();
		
			Gson gson = new GsonBuilder().setExclusionStrategies(ges).setPrettyPrinting().serializeNulls().create();
			
			jsonstr= gson.toJson(user);
		}
		catch(Exception ex) {
			
	      	  ex.printStackTrace();
	      	  logger.error("UserDataService-Error getting user by login name, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 				
		}		
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	

	//@RolesAllowed("manager")
	@GET
    @Path("/subject/subjectlist/{userid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getSubjectListForUser(@PathParam("userid") Long userid )
	{
		String jsonstr="";
		try {
			UserDataRepository srit= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
			
			List<SubjectPlain> subjects=srit.getSubjects(userid);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			
			jsonstr= gson.toJson(subjects);
		}
		catch(Exception ex) {
			
	      	  ex.printStackTrace();
	      	  logger.error("UserDataService-Error getting subject list for user, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 				
		}		
		
		return Response.status(200).entity(jsonstr).build();
		
		
	}	
	
	///ilsWebPages/registration/resteasy/qb/userservice/registerUser
	
	@POST
    @Path("/userservice/registerUser")
    @Produces(MediaType.TEXT_HTML)	
	public Response registerUser(
			     @FormParam("firstname") String firstName,
			     @FormParam("lastname") String lastName,
			     @FormParam("email") String email,
			     @FormParam("password") String password,
			     @FormParam("registerAs") String registerAs)
	{
	  
	  String jsonstr="";
	  
	  GsonExclusionStrat ges = new GsonExclusionStrat();
	  Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

	  User userobj=null;
	  
      try {
    	  
    	      UserDataRepository dataRep= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
    		  
    			
    			
    	  
    	      //find if user already exists, if yes then javax.persistence.EntityNotFoundException is thrown
    	      try {
                  userobj=dataRep.findUserByEmailId(email); 
                 
	       		  logger.info("User with specified email already present, can't register : User ->"+firstName+" "+lastName+" Email ->"+email);
	    		  userobj=new User();
	              userobj.setId(new Long(-1));
	              
	              UserPlain up = new UserPlain();
	              
	              up.setId("-1");
	              
	        	  jsonstr=gson.toJson(userobj);	
	        	  
                 }
              catch(javax.persistence.NoResultException exists) {
          
	              //if user does not exists create one
				  User user=new User();
			      user.setFirstName(firstName);
			      user.setLastName(lastName);
			      user.setEmail(email);
			      user.setPassword(password);
			      user.setRole(getRole(registerAs,dataRep));

			      
/*			      UserProfile up =new UserProfile();
			      dataRep.createUserProfile(up);
			      
			      user.setUserProfile(up);*/
			      
			      
	
			      user.setAddress(getAddress());
			      user.setRecordStatus(getRecordStatus());		
		
			      //Persisting user to database 
			      userobj=dataRep.createUser(user);
			      
			      //Creating plain object
			      UserPlain uplain = new UserPlain();
			      uplain.setId(userobj.getId().toString());
			      
				  jsonstr= gson.toJson(uplain);
				  
				  logger.info("User registration successfull : User ->"+userobj.getFirstName()+" "+userobj.getLastName() +" Email ->"+userobj.getEmail());
              }
        }
      catch(Exception ex)  {
    	  ex.printStackTrace();
    	  logger.error("User registration failed, cause ->"+ ex.getMessage());
    	  
		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
    	  
      }
		  
		  System.out.println(jsonstr);
		  return Response.status(200).entity(jsonstr).build();
		
	}


	@POST
    @Path("/userservice/createUser")
    @Produces(MediaType.TEXT_HTML)	
	public Response createUser(
			     @FormParam("userid") String userId,
			     @FormParam("firstname") String firstName,
			     @FormParam("lastname") String lastName,
			     @FormParam("email") String email,
			     @FormParam("phone") String phone,
			     @FormParam("password") String password,
			     @FormParam("userrole") String userRole )
	{
	  
	  String jsonstr="";
	  
	  GsonExclusionStrat ges = new GsonExclusionStrat();
	  Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

	  User userobj=null;
	  
      try {
    	  
    	      UserDataRepository dataRep= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
    		  
    			
    			
    	  
    	      //find if user already exists, if yes then javax.persistence.EntityNotFoundException is thrown
    	      try {
                  userobj=dataRep.findUserByEmailId(email); 
                 
	       		  logger.info("User with specified email already present, can't register : User ->"+firstName+" "+lastName+" Email ->"+email);
	       		  UserPlainV2 upv= new UserPlainV2();
	    		  userobj=new User();
	              upv.setId(new Long(-1).toString());
	              
	        	  jsonstr=gson.toJson(upv);	
	        	  
                 }
              catch(javax.persistence.NoResultException exists) {
          
	              //if user does not exists create one
				  User user=new User();
			      user.setFirstName(firstName);
			      user.setLastName(lastName);
			      user.setEmail(email);
			      user.setPhone(phone);
			      user.setPassword(password);
			      user.setRecordStatus(getRecordStatus());
			      user.getRecordStatus().setUpdatedBy(new Long(userId));
		
			      //Persisting user to database 
			      userobj=dataRep.createUser(user,userRole);
			      
			      //Creating plain object
			      UserPlainV2 uplain = new UserPlainV2();
			      uplain.setId(userobj.getId().toString());
			      uplain.setFirstName(userobj.getFirstName());
			      uplain.setLastName(userobj.getLastName());
			      uplain.setEmail(userobj.getEmail());
			      uplain.setPhone(userobj.getPhone());
			      uplain.setUpdateDate(getDateString(userobj.getRecordStatus().getUpdatedOn()));
			      uplain.setUpdateTime(getTimeString(userobj.getRecordStatus().getUpdatedOn()));
			      uplain.setUpdateBy(userobj.getRecordStatus().getUpdatedBy().toString());
			      
				  jsonstr= gson.toJson(uplain);
				  
				  logger.info("User creation successfull : User ->"+userobj.getFirstName()+" "+userobj.getLastName() +" Email ->"+userobj.getEmail());
              }
        }
      catch(Exception ex)  {
    	  ex.printStackTrace();
    	  logger.error("User creation failed, cause ->"+ ex.getMessage());
    	  
		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
    	  
      }
		  
		  System.out.println(jsonstr);
		  return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
	
	
	
	
	
	
	@POST
    @Path("/updateUserProfile")
    @Produces(MediaType.TEXT_HTML)	
	public Response updateUserProfile(
			     @FormParam("firstName") String firstName,
			     @FormParam("lastName") String lastName,
			     @FormParam("email") String email,
			     @FormParam("phone") String phone,
			     @FormParam("bloodgroup") String bloodGroup,
			     @FormParam("fathername") String fatherName,
			     @FormParam("fatherphone") String fatherPhone,
			     @FormParam("fatheremail") String fatherEmail,
			     @FormParam("mothername") String motherName,
			     @FormParam("motherphone") String motherPhone,
			     @FormParam("motheremail") String motherEmail,
			     @FormParam("admission-no") String admissionNo,
			     @FormParam("section") String section,
			     @FormParam("level") String level,
			     @FormParam("studiesin") String studiesIn,
			     @FormParam("street") String street,
			     @FormParam("city") String city,
			     @FormParam("state") String state,	
			     @FormParam("zip") String zip,
			     @FormParam("otherinfo") String otherinfo,
	             @FormParam("xyz") String password)
	{
	  
	  String jsonstr="";
	  
	  GsonExclusionStrat ges = new GsonExclusionStrat();
	  Gson gson = new GsonBuilder().setExclusionStrategies(ges).setPrettyPrinting().serializeNulls().create();

	  User userobj=null;
	  
      try {
    	  
    	      UserDataRepository dataRep= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
    	  
    	      //find if user already exists, if yes then javax.persistence.EntityNotFoundException is thrown
    	      try {
                   userobj=dataRep.findUserByEmailId(email); 
                 
	       		  logger.info("User with specified email present, updating information : User ->"+firstName+" "+lastName+" Email ->"+email);
	   
		       		userobj.setFirstName(firstName);
		       		userobj.setLastName(lastName);
		       		userobj.setEmail(email);
		       		userobj.setPhone(phone);
		       		if (!password.equals("noChange"))
		       			userobj.setPassword(password);
		       		userobj.setUserLevel(dataRep.findLevelById(studiesIn));
		       		
		       		userobj.getUserProfile().setBloodGroup(bloodGroup);
		       		userobj.getUserProfile().setFatherName(fatherName);
		       		userobj.getUserProfile().setFatherPhone(fatherPhone);
		       		userobj.getUserProfile().setFatherEmail(fatherEmail);
		       		userobj.getUserProfile().setMotherEmail(motherEmail);
		       		userobj.getUserProfile().setMotherName(motherName);
		       		userobj.getUserProfile().setMotherPhone(motherPhone);
		       		userobj.getUserProfile().setAdmissionNumber(admissionNo);	       		
		       		userobj.getUserProfile().setStudiesInSection(section);
		       		userobj.getUserProfile().setStudiesIn(studiesIn);
		       		userobj.getUserProfile().setLevelName(level);
		       		userobj.getAddress().setStreet(street);
		       		userobj.getAddress().setCity(city);
		       		userobj.getAddress().setState(state);
		       		userobj.getAddress().setZip(zip);
		       	    userobj.setComments(otherinfo);
		       	    //Long updBy = userobj.getRecordStatus().getUpdatedBy();
		       		userobj.getRecordStatus().setUpdatedOn(getRecordStatus().getUpdatedOn());
		       		

				    //Persisting user to database 
				    userobj=dataRep.updateUser(userobj);
				    
				    //Creating plain object
				    UserPlain up =new UserPlain();
					   
				    up.setFirstName(firstName);
				    up.setLastName(lastName);
				    up.setEmail(email);
				    up.setPhone(phone);
				    up.setUpdateDate(getDateString(getRecordStatus().getUpdatedOn()));
				    up.setUpdateTime(getTimeString(getRecordStatus().getUpdatedOn()));
				    UserProfilePlain upp = new UserProfilePlain();
				    
				    upp.setBloodGroup(bloodGroup);
				    upp.setFatherName(fatherName);
				    upp.setFatherPhone(fatherPhone);
				    upp.setFatherEmail(fatherEmail);
				    upp.setMotherEmail(motherEmail);
				    upp.setMotherName(motherName);
				    upp.setMotherPhone(motherPhone);
				    upp.setAdmissionNumber(admissionNo);	       		
				    upp.setStudiesInSection(section);
				    upp.setStudiesIn(studiesIn);
				    upp.setLevelName(level);
				    up.setUserProfile(upp);
				    
				    AddressPlain ap = new AddressPlain();
				    
				    ap.setStreet(street);
				    ap.setCity(city);
				    ap.setState(state);
				    ap.setZip(zip);
				    up.setAddress(ap);
				    
				    up.setComments(otherinfo);
				    //up.setRecordStatus(getRecordStatus());					    

				    
	              
	        	    jsonstr=gson.toJson(up);	
                 }
              catch(javax.persistence.NoResultException exists) {
          
			      userobj = new User();
			      userobj.setId(new Long(-1));
				  jsonstr= gson.toJson(userobj);
				  
				  logger.info("User to be updated was not found : User ->"+userobj.getFirstName()+" "+userobj.getLastName() +" Email ->"+userobj.getEmail());
              }
        }
      catch(Exception ex)  {
    	  ex.printStackTrace();
    	  logger.error("User update failed, cause ->"+ ex.getMessage());
    	  
		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
    	  
      }
		  
		  System.out.println(jsonstr);
		  return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	
	
	//utility methods
	private Role getRole(String roleString,UserDataRepository dataRep) throws Exception 	{
		
		Role role=new Role();
		role.setId(dataRep.getRoleId(roleString));
		role.setRole(roleString);
		
		return role;
	}
	
	  private Address getAddress()
	  {
		  Address add=new Address();
		  add.setStreet("Not available");
		  add.setCity("Not available");
		  add.setState("Not available");
		  add.setCountry("Not available");
		  add.setZip("Not available");
		  
		  return add;
	  }	
	  
	  private RecordStatus getRecordStatus() 
	  {
		  RecordStatus rs=new RecordStatus();

		  long timeinmillis = System.currentTimeMillis();
		  java.sql.Timestamp dt = new java.sql.Timestamp(timeinmillis);
		  
		  rs.setUpdatedOn(dt);
		  rs.setUpdatedBy(new Long(10000));  //while updating user profile updatedBy field should not be updated as it represents owwnership
		  rs.setDeleted(false);
		  
		  return rs;
	  }	  
	  
		private String getTimeString(java.sql.Timestamp ts) {
			SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
			 sdfTime.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			 String timeStr=sdfTime.format(ts);
			return timeStr;
		}

		private String getDateString(java.sql.Timestamp ts) {
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy");
			 sdfDate.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
			 String dateStr=sdfDate.format(ts);
			return dateStr;
		}		  
	
}
