package ils.services.dataservices;

import ils.json.ExamPlainV2;
import ils.json.UserPlain;
import ils.persistence.repositories.ScheduledExamsDataRepository;
import ils.testspring.context.SpringApplicationContext;

import java.util.List;

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
public class ScheduledExamsDataService {
	
	final static Logger logger = Logger.getLogger(ScheduledExamsDataService.class);
	//@RolesAllowed("admin")
	@POST
    @Path("/scheduledexam/searchScheduledExams")
    @Produces(MediaType.TEXT_HTML)	

   public Response getScheduledExamsBySeachCriteria(@FormParam("userid") String userId,
			                                    @FormParam("level") String levelId,
												@FormParam("subject") String subjectId,
												@FormParam("examtype") String examTypeId,
												@FormParam("datefrom") String dateFrom,
												@FormParam("dateto") String dateTo,
												@FormParam("freetext") String freeText)
	{
		
		
		if (dateTo == null) {
			dateTo="";
		}
		if (dateFrom == null) {
			dateFrom="";
		}
		if (examTypeId == null) {
			examTypeId="";
		}
		if (freeText == null) {
			freeText="";
		}		

		List<ExamPlainV2> qList=null;
		ScheduledExamsDataRepository exRep= (ScheduledExamsDataRepository)SpringApplicationContext.getBean("ScheduledExamsDataAccessBean");
		try {
			qList=exRep.getSearchedExamsList(userId,levelId,subjectId,examTypeId,dateFrom,dateTo,freeText);
		    
		} catch(javax.persistence.NoResultException nr) {
			qList=null;
		}
		catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("ScheduledExamsDataService-Error getting scheduled exams list, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(qList);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}
	
	
	@GET
    @Path("/scheduledexam/students/{examid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response getStudentsListForExam(@PathParam("examid") String examId)
	{

		List<UserPlain> userList=null;
		ScheduledExamsDataRepository exRep= (ScheduledExamsDataRepository)SpringApplicationContext.getBean("ScheduledExamsDataAccessBean");
		
		try {
			userList=exRep.getScheduledStudentsForExam(new Long(examId));
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("ScheduledExamsDataService-Error getting list of students scheduled for exam, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= gson.toJson(userList);
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	

	
	
	@GET
    @Path("/scheduledexam/unassign/student/{studentid}")
    @Produces(MediaType.TEXT_HTML)	
	public Response unassignStudentsForExam(@PathParam("studentid") String studentId)
	{

		boolean unassigned = false;
		ScheduledExamsDataRepository exRep= (ScheduledExamsDataRepository)SpringApplicationContext.getBean("ScheduledExamsDataAccessBean");
		
		try {
			unassigned=exRep.unassignStudentForExam(new Long(studentId));
		    
		} catch (Exception ex)
		{
	      	  ex.printStackTrace();
	      	  logger.error("ScheduledExamsDataService-Error unassigning student for exam, cause ->"+ ex.getMessage());
	      	  
	  		  return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build(); 			
		}

		//Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		
		String jsonstr= "{\"unassigned\":"+unassigned+"}";
		
		System.out.println(jsonstr);
		
		return Response.status(200).entity(jsonstr).build();
		
	}	
	
	
}
