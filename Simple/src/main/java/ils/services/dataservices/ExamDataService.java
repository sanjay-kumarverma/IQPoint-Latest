package ils.services.dataservices;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.SecurityContext;

import ils.json.AnswerSheetPlain;
import ils.json.ExamExecutionPlain;
import ils.json.ExamPlain;
import ils.json.QuestionPaperPlain;
import ils.json.QuestionPlain;
import ils.json.QuestionPlainForScore;
import ils.json.SubjectPlain;
import ils.persistence.repositories.ExamDataRepository;
import ils.persistence.repositories.UserDataRepository;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ils.persistence.Exception.ErrorMessage;
import ils.persistence.domainclasses.AnswerSheet;
import ils.persistence.domainclasses.Exam;
import ils.persistence.domainclasses.ExamExecution;
import ils.persistence.domainclasses.Role;
import ils.persistence.domainclasses.Subject;
import ils.persistence.domainclasses.User;
import ils.persistence.domainclasses.embeddables.Address;
import ils.persistence.domainclasses.embeddables.RecordStatus;

import ils.testspring.context.SpringApplicationContext;
import ils.utils.GsonExclusionStrat;
import ils.utils.GsonExclusionStratExam;


@Path("/qb")
//@RolesAllowed({"admin","manager"})
public class ExamDataService {
	
	final static Logger logger = Logger.getLogger(ExamDataService.class);
	//@RolesAllowed("admin")
	@GET
    @Path("/exams/subject/{subjectid}/user/{userid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getExamBySubjectAndUser(@Context SecurityContextHolder sec,@PathParam("subjectid") String subjectid,@PathParam("userid") String userId)
	{
		String jsonstr ="";
       try {
		
		ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
		
		List<ExamPlain> examlist=exRep.listAllExamsForSubject(new Long(subjectid), new Long(userId));

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		jsonstr= gson.toJson(examlist);
		
       } catch(Exception ex) {
       	  ex.printStackTrace();
       	  logger.error("ExamDataService-Error trying to get exams for user by subject, cause ->"+ ex.getMessage());
       	  
   		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();     	   
       }
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	@GET
    @Path("/questionpaper/{qpid}/exam/{examid}/user/{userid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getQuestionPaperById(@PathParam("qpid") String qpId,@PathParam("examid") String examId,@PathParam("userid") String userId)
	{
		QuestionPaperPlain qPaper=null;
		String jsonstr="";
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
        try {
				ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
				
				qPaper=exRep.listQuestionsByQPId(new Long(qpId), new Long(examId), new Long(userId));
				
				jsonstr= gson.toJson(qPaper);
				
        } catch (javax.persistence.NoResultException nr)
        {
        	logger.error("ExamDataService-No question paper found, cause ->"+ nr.getMessage());
        	 qPaper= new QuestionPaperPlain();
        	 qPaper.setId(new Long(-1).toString());
        	 jsonstr= gson.toJson(qPaper);
        	 
        } catch(Exception ex) {
        	
      	  ex.printStackTrace();
      	  logger.error("ExamDataService-Error trying to get question paper, cause ->"+ ex.getMessage());
      	  
  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();        	
        }
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	

	
	
	@GET
    @Path("/question/{qid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getQuestionById(@PathParam("qid") String qId)
	{
		QuestionPlain question=null;
		String jsonstr="";
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
        try {
				ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
				
				question=exRep.getQuestionById(new Long(qId));
				
				jsonstr= gson.toJson(question);
				
        } catch (javax.persistence.NoResultException nr)
        {
        	logger.error("No results while trying to get Question, cause ->"+ nr.getMessage());
        	 question= new QuestionPlain();
        	 question.setId(new Long(-1).toString());
        	 jsonstr= gson.toJson(question);
        	 
        } catch(Exception ex) {
        	
      	  ex.printStackTrace();
      	  logger.error("Error while getting the question, cause ->"+ ex.getMessage());
      	  
  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();        	
        }
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
	@GET
    @Path("/exam/previousAttempts/exam/{examid}/user/{userid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getAttemptsByExamId(@PathParam("examid") String examId,@PathParam("userid") String userId)
	{
		List<ExamExecutionPlain> examExecutions=null;
		String jsonstr="";
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
        try {
				ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
				
				examExecutions=exRep.getAttemptsByExamId(new Long(examId),new Long(userId));
				
				jsonstr= gson.toJson(examExecutions);
				
				
        } catch (javax.persistence.NoResultException nr)
        {
        	 logger.error("No results while trying to get exam executions, cause ->"+ nr.getMessage());
				
		     jsonstr= gson.toJson(examExecutions); //return empty string
        	 
        } catch(Exception ex) {
        	
      	  ex.printStackTrace();
      	  logger.error("Error while getting the question, cause ->"+ ex.getMessage());
      	  
  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();        	
        }
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	
		
	@GET
    @Path("/exam/getScore/{examExecutionId}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getTestScore(@PathParam("examExecutionId") String examExecutionId)
	{
		List<QuestionPlainForScore> resultList=null;
		String jsonstr="";
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
        try {
				ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
				
				resultList=exRep.getScoreByExamExecutionId(new Long(examExecutionId));
				
				jsonstr= gson.toJson(resultList);
				
				
        } catch (javax.persistence.NoResultException nr)
        {
        	 logger.error("No results while trying to get exam score, cause ->"+ nr.getMessage());
				
		     jsonstr= gson.toJson(resultList); //return empty string
        	 
        } catch(Exception ex) {
        	
      	  ex.printStackTrace();
      	  logger.error("Error while getting the exam score, cause ->"+ ex.getMessage());
      	  
  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();        	
        }
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	

	
	@GET
    @Path("/exam/answerSheet/{attemptId}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getAnswerSheet(@PathParam("attemptId") String attemptId)
	{
		List<QuestionPlainForScore> resultList=null;
		String jsonstr="";
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
        try {
				ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
				
				resultList=exRep.getAnswerSheetByExamExecutionId(new Long(attemptId));
				
				jsonstr= gson.toJson(resultList);
				
				
        } catch (javax.persistence.NoResultException nr)
        {
        	 logger.error("No results while trying to get answer sheet, cause ->"+ nr.getMessage());
				
		     jsonstr= gson.toJson(resultList); //return empty string
        	 
        } catch(Exception ex) {
        	
      	  ex.printStackTrace();
      	  logger.error("Error while getting answer sheet, cause ->"+ ex.getMessage());
      	  
  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();        	
        }
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	@GET
    @Path("/exam/deleteAttempt/{attemptId}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getDeleteAttempt(@PathParam("attemptId") String attemptId)
	{
		//List<QuestionPlainForScore> resultList=null;
		ExamExecutionPlain ee=null;
		String jsonstr="";
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
        try {
				ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
				
				ee=exRep.deleteExamExecutionById(new Long(attemptId));
				
				jsonstr= gson.toJson(ee);
				
				
        } catch (javax.persistence.NoResultException nr)
        {
        	 logger.error("Exam execution with mentioned ID for deletion was not found, cause ->"+ nr.getMessage());
				
		     jsonstr= gson.toJson("{}"); //return empty string
        	 
        } catch(Exception ex) {
        	
      	  ex.printStackTrace();
      	  logger.error("Error, there was some problem deleting Exam Execution record, cause ->"+ ex.getMessage());
      	  
  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();        	
        }
		
		//System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
	@POST
    @Path("/submitanswer")
    @Produces(MediaType.TEXT_HTML)	
	public Response saveAnswer(
			     @FormParam("userId") String userId,
			     @FormParam("questionPaperId") String qPaperId,
			     @FormParam("questionId") String qId,
			     @FormParam("qoption") String option)

	{
		

		
		String jsonstr="";
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
        try {
				ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
				
				AnswerSheet as=exRep.saveOrUpdateAnswer(new Long(userId), new Long(qPaperId), new Long(qId), option);
				
				AnswerSheetPlain asp=new AnswerSheetPlain();
				asp.setId(as.getId().toString());
				asp.setPaperId(as.getQpId().toString());
				asp.setQuestionId(as.getQuestionId().toString());
				asp.setAnswer(as.getAnswer());
				
				
				jsonstr= gson.toJson(asp);
				
        } catch(Exception ex) {
        	
      	  ex.printStackTrace();
      	  logger.error("Error while saving or updating answer, cause ->"+ ex.getMessage());
      	  
  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();        	
        }		
		
		
		return Response.status(200).entity(jsonstr).build();
	}
	

	
	
	@POST
    @Path("/saveTestSession")
    @Produces(MediaType.TEXT_HTML)	
	public Response saveTestSession(
			     @FormParam("userId") String userId,
			     @FormParam("questionPaperId") String questionPaperId,
			     @FormParam("examExecutionId") String examExecutionId)

	{
		String jsonstr="{'sucessMessage':'Test session saved successfully'}";
		//Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
        try {
				ExamDataRepository exRep= (ExamDataRepository)SpringApplicationContext.getBean("ExamDataAccessBean");
				
				exRep.saveTestSession(new Long(userId), new Long(questionPaperId), new Long(examExecutionId));
				
				
        } catch(Exception ex) {
        	
      	  ex.printStackTrace();
      	  logger.error("Error while saving or updating answer, cause ->"+ ex.getMessage());
      	  
  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();        	
        }		
		
		
		return Response.status(200).entity(jsonstr).build();
	}
	
	
	//@RolesAllowed("manager")
/*	@GET
    @Path("/showuser/{id}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getUserById(@PathParam("id") Long id )
	{
		UserDataRepository srit= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
		
		User user=srit.findUserById(new Long(id));
		
		GsonExclusionStrat ges = new GsonExclusionStrat();
	
		Gson gson = new GsonBuilder().setExclusionStrategies(ges).setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(user);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	

	//@RolesAllowed("manager")
	@GET
    @Path("/subject/subjectlist/{userid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getSubjectListForUser(@PathParam("userid") Long userid )
	{
		UserDataRepository srit= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
		
		List<SubjectPlain> subjects=srit.getSubjects(userid);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(subjects);
		
		return Response.status(200).entity(jsonstr).build();
		
		
	}	
	
	
	
	@POST
    @Path("/registerUser")
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
	  Gson gson = new GsonBuilder().setExclusionStrategies(ges).setPrettyPrinting().serializeNulls().create();

	  User userobj=null;
	  
      try {
    	  
    	      UserDataRepository dataRep= (UserDataRepository)SpringApplicationContext.getBean("UserDataAccessBean");
    		  
    			
    			
    	  
    	      //find if user already exists, if yes then javax.persistence.EntityNotFoundException is thrown
    	      try {
                  userobj=dataRep.findUserByEmailId(email); 
                 
	       		  logger.info("User with specified email already present, can't register : User ->"+firstName+" "+lastName+" Email ->"+email);
	    		  userobj=new User();
	              userobj.setId(new Long(-1));
	              
	              
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
			      user.setAddress(getAddress());
			      user.setRecordStatus(getRecordStatus());		
		
			      //Persisting user to database 
			      userobj=dataRep.createUser(user);
			      
			      
				  jsonstr= gson.toJson(userobj);
				  
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
			     @FormParam("studiesin") String studiesIn,
			     @FormParam("street") String street,
			     @FormParam("city") String city,
			     @FormParam("state") String state,	
			     @FormParam("zip") String zip,
			     @FormParam("otherinfo") String otherinfo)			     
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
		       		userobj.getAddress().setStreet(street);
		       		userobj.getAddress().setCity(city);
		       		userobj.getAddress().setState(state);
		       		userobj.getAddress().setZip(zip);
		       	    userobj.setComments(otherinfo);
		       		userobj.setRecordStatus(getRecordStatus());	    		  

				    //Persisting user to database 
				    userobj=dataRep.updateUser(userobj);
	              
	        	    jsonstr=gson.toJson(userobj);	
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
	private Role getRole(String roleString,UserDataRepository dataRep) 	{
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
		  rs.setUpdatedBy(new Long(10000));
		  rs.setDeleted(false);
		  
		  return rs;
	  }	*/  
	
}
