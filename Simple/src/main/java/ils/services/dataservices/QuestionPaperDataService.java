package ils.services.dataservices;

import ils.json.ExamPlain;
import ils.json.QuestionPaperPlain;
import ils.json.QuestionPaperPlainV2;
import ils.json.QuestionPaperPlainV3;
import ils.json.UserPlain;
import ils.persistence.repositories.QuestionPaperDataRepository;
import ils.testspring.context.SpringApplicationContext;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Path("/qb")
//@RolesAllowed({"admin","manager"})
public class QuestionPaperDataService {
	
	final static Logger logger = Logger.getLogger(QuestionPaperDataService.class);
	//@RolesAllowed("admin")
	@POST
    @Path("/questionpaper/searchQuestionPaper")
    @Produces(MediaType.TEXT_HTML)	

   public Response getQuestionsBySeachCriteria(@FormParam("userid") String userId,
			                                    @FormParam("level") String levelId,
												@FormParam("subject") String subjectId,
												@FormParam("papertype") String paperTypeId,
												@FormParam("datefrom") String dateFrom,
												@FormParam("dateto") String dateTo,
												@FormParam("freetext") String freeText)
	{

		List<QuestionPaperPlain> qList=null;
		QuestionPaperDataRepository exRep= (QuestionPaperDataRepository)SpringApplicationContext.getBean("QuestionPaperDataAccessBean");
		try {
			
			if (dateTo == null) {
				dateTo="";
			}
			if (dateFrom == null) {
				dateFrom="";
			}
			if (paperTypeId == null) {
				paperTypeId="";
			}
			if (freeText == null) {
				freeText="";
			}			
			
			
			qList=exRep.getSearchedQuestionPapers(userId,levelId,subjectId,paperTypeId,dateFrom,dateTo,freeText);
		    
		} catch(javax.persistence.NoResultException nr) {
			qList=null;
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionPaperDataService-Error getting question papers, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(qList);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	

	@POST
    @Path("/questionpaper/scheduleExam")
    @Produces(MediaType.TEXT_HTML)	
	@Consumes(MediaType.APPLICATION_JSON)
/*	public Response scheduleExam(@FormParam("userId") String userId,
												@FormParam("examname") String examName,
												@FormParam("examdate") String examDate,
												@FormParam("examduration") String examDuration,
												@FormParam("examtype") String examType,
												@FormParam("cummpercent") String cummPercent,
												@FormParam("paperId") String paperId)*/
	public Response scheduleExam(String scheduleData)
	{

		ExamPlain examPlain=null;
		QuestionPaperDataRepository exRep= (QuestionPaperDataRepository)SpringApplicationContext.getBean("QuestionPaperDataAccessBean");
		try {
			//examPlain=exRep.scheduleExam(userId,examName,examDate,examDuration,examType,cummPercent,paperId);
			examPlain=exRep.scheduleExam(scheduleData);
		} 
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionPaperDataService-Error scheduling exam, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(examPlain);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
	
	@POST
    @Path("/questionpaper/getUsersByLevelRoleOwner")
    @Produces(MediaType.TEXT_HTML)	
	public Response getUsersByLevelRoleOwner(   @FormParam("ownerid") String ownerId,
												@FormParam("roleid") String roleId,
												@FormParam("levelid") String levelId)
	{

		List<UserPlain> users=null;
		QuestionPaperDataRepository exRep= (QuestionPaperDataRepository)SpringApplicationContext.getBean("QuestionPaperDataAccessBean");
		try {
			users=exRep.getUsersByLevelRoleOwner(ownerId,roleId,levelId);
		    
		} 
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionPaperDataService-Error getting list of students for scheduling exam, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(users);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	@GET
    @Path("/questionpaper/getPaper/{id}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getQuestionPaper(@PathParam("id") String paperId)
	{

		QuestionPaperDataRepository exRep= (QuestionPaperDataRepository)SpringApplicationContext.getBean("QuestionPaperDataAccessBean");
		QuestionPaperPlainV2 qpp = new QuestionPaperPlainV2();
		
		try {
			qpp=exRep.getQuestionPaper(new Long(paperId));
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionPaperDataService-Error getting question paper, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(qpp);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}		
	
	
	@GET
    @Path("/questionpaper/getBriefPaper/{id}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getBriefQuestionPaper(@PathParam("id") String paperId)
	{

		QuestionPaperDataRepository exRep= (QuestionPaperDataRepository)SpringApplicationContext.getBean("QuestionPaperDataAccessBean");
		QuestionPaperPlainV3 qpp = new QuestionPaperPlainV3();
		
		try {
			qpp=exRep.getBriefQuestionPaper(new Long(paperId));
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("QuestionPaperDataService-Error getting brief question paper, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(qpp);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}		
}
